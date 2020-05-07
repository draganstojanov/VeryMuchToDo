//package com.andraganoid.memoryfields.database.repository
//
//import com.andraganoid.memoryfields.model.Game
//
//class SavedRepository(private val savedGamesDao: SavedGamesDao) {
//
//    suspend fun saveGame(game: Game?) {
//        game?.savedTimestamp = System.currentTimeMillis()
//        savedGamesDao.saveGame(game)
//    }
//
//    suspend fun getAllSavedGames(): List<Game?>? {
//        var savedGames: List<Game?>? = listOf()
//        try {
//            savedGames = savedGamesDao.getAllSavedGames()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return savedGames
//    }
//
//
//    suspend fun deleteSavedGame(game: Game?) {
//        savedGamesDao.deleteSavedGame(game!!)
//    }
//
//    suspend fun deleteAllSavedGames() {
//        savedGamesDao.deleteAllSavedGames()
//    }
//}