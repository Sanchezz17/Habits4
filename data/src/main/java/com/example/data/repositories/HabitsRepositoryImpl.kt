package com.example.data.repositories

import android.util.Log
import androidx.lifecycle.asLiveData
import com.example.data.mappers.toEntity
import com.example.data.mappers.toModel
import com.example.data.network.HabitApi
import com.example.domain.entities.HabitEntity
import com.example.domain.entities.HabitUID
import com.example.domain.repositories.HabitsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import java.lang.Exception


class HabitsRepositoryImpl(
    private val habitDao: com.example.data.database.HabitDao,
    private val habitApi: HabitApi
) : HabitsRepository {

    override suspend fun initializeHabitsInDB() {
        doRequestUntilSuccess(
            { habitApi.getHabits() },
            { habitsFromServer ->
                for (habitFromServer in habitsFromServer) {
                    val habitFromDB = habitDao.getByUid(habitFromServer.uid).asLiveData().value
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

    override suspend fun addOrUpdateHabit(habitEntity: HabitEntity) {
        val habit = habitEntity.toModel()
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

    override suspend fun deleteHabit(habitEntity: HabitEntity) {
        val habit = habitEntity.toModel()
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

    override fun getAllHabits(): Flow<List<HabitEntity>> = habitDao.getAll()
        .map { it.map { value -> value.toEntity() } }

    override fun getHabitByUid(uid: String?): Flow<HabitEntity?> = habitDao.getByUid(uid)
        .map { it?.toEntity() }

    companion object {

        private const val SERVER_ERROR_TAG = "Server error"
        private const val CLIENT_ERROR_TAG = "Client error"
        private const val COROUTINE_ERROR = "Coroutine error"
        private const val TIMEOUT_AFTER_SERVER_ERROR = 1000L
    }
}
