package com.example.habits4.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.habits4.database.HabitDao
import com.example.habits4.model.Habit
import com.example.habits4.model.HabitUID
import com.example.habits4.network.HabitApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import java.lang.Exception


class HabitsRepository(private val habitDao: HabitDao, private val habitApi: HabitApi) {
    suspend fun initializeHabitsInDB() {
        doRequestUntilSuccess(
            { habitApi.getHabits() },
            { habitsFromServer ->
                for (habitFromServer in habitsFromServer) {
                    val habitFromDB = habitDao.getByUid(habitFromServer.uid).value
                    if (habitFromDB == null) {
                        withContext(Dispatchers.IO) { habitDao.insert(habitFromServer) }
                    }
                    else if (habitFromDB.date < habitFromServer.date) {
                        withContext(Dispatchers.IO) { habitDao.update(habitFromServer) }
                    }
                }
            }
        )
    }

    suspend fun addOrUpdateHabit(habit: Habit) {
        doRequestUntilSuccess(
            { habitApi.addOrUpdateHabit(habit) },
            {
                val previousUid = habit.uid
                habit.uid = it.uid
                if (previousUid == null) {
                    withContext(Dispatchers.IO) { habitDao.insert(habit) }
                } else {
                    withContext(Dispatchers.IO) { habitDao.update(habit) }
                }
            }
        )
    }

    suspend fun deleteHabit(habit: Habit) {
        doRequestUntilSuccess(
            { habitApi.deleteHabit(HabitUID(habit.uid)) },
            { _ -> withContext(Dispatchers.IO) { habitDao.delete(habit) } }
        )
    }

    private suspend fun <T> doRequestUntilSuccess(
        responseGetter: suspend () -> Response<T>,
        callback: suspend (T) -> Unit
    ) {
        while (true) {
            try {
                val response = responseGetter()
                if (response.isSuccessful) {
                    response.body()?.let { callback(it) }
                    break
                } else {
                    val errorBodyString = response.errorBody()?.string() ?: ""
                    if (needRepeatUnsuccessfulRequest(errorBodyString, response.code())) {
                        Thread.sleep(TIMEOUT_AFTER_SERVER_ERROR)
                    } else {
                        break
                    }
                }
            } catch (e: Exception) {
                Log.e(COROUTINE_ERROR, e.toString())
                break
            }
        }
    }

    private fun needRepeatUnsuccessfulRequest(errorBodyString: String, statusCode: Int): Boolean {
        val errorJSONString = JSONObject(errorBodyString).toString()
        return if (statusCode >= 500) {
            Log.e(SERVER_ERROR_TAG, errorJSONString)
            true
        } else {
            Log.e(CLIENT_ERROR_TAG, errorJSONString)
            false
        }
    }

    fun getAllHabits(): LiveData<List<Habit>> = habitDao.getAll()

    fun getHabitByUid(uid: String?): LiveData<Habit?> = habitDao.getByUid(uid)

    companion object {

        private const val SERVER_ERROR_TAG = "Server error"
        private const val CLIENT_ERROR_TAG = "Client error"
        private const val COROUTINE_ERROR = "Coroutine error"
        private const val TIMEOUT_AFTER_SERVER_ERROR = 1000L
    }
}
