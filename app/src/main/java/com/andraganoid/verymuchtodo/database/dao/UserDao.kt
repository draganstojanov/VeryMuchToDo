package com.andraganoid.verymuchtodo.database.dao


import androidx.room.*
import com.andraganoid.verymuchtodo.kmodel.User
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

//    @Insert
//    suspend fun saveUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: List<User>)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user_table")
   suspend fun allUsersClean(): List<User>

    @Query("SELECT * FROM user_table")
    fun allUsers(): Flow<List<User>>


//
//    @Query("SELECT * FROM hi_score_table WHERE level_id = :levelId AND game_style=${Score.ACTION}")
//    suspend fun getTopScoreAction(levelId: String): Score
//
//    @Query("SELECT * FROM hi_score_table WHERE level_id = :levelId AND game_style=${Score.STRATEGY}")
//    suspend fun getTopScoreStrategy(levelId: String): Score
//


//    @Query("SELECT * FROM saved_games_fragment ORDER BY saved_timestamp DESC")
//    suspend fun getAllSavedGames(): List<Game?>
//
//    @Insert
//    suspend fun saveGame(game: Game?)
//
//    @Delete
//    suspend fun deleteSavedGame(game: Game)
//
//    @Query("DELETE FROM saved_games_fragment")
//    suspend fun deleteAllSavedGames()


}