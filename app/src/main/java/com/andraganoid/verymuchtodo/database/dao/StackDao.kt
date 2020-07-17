package com.andraganoid.verymuchtodo.database.dao

import androidx.room.*
import com.andraganoid.verymuchtodo.kmodel.Stack
import kotlinx.coroutines.flow.Flow

@Dao
interface StackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveStack(stack: List<Stack>)

    @Delete
    suspend fun deleteStack(stack: List<Stack>)

    @Update
    suspend fun updateStack(stack: List<Stack>)

    @Query("SELECT * FROM stack_table")
    fun allStacks(): Flow<List<Stack>>

}