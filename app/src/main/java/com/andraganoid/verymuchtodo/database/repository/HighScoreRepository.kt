//package com.andraganoid.memoryfields.database.repository
//
//import com.andraganoid.memoryfields.database.dao.HighScoresDao
//import com.andraganoid.memoryfields.model.Score
//
//class HighScoreRepository(private val hiScoreDao: HighScoresDao) {
//
//    suspend fun saveHiScore(score: Score) {
//        hiScoreDao.saveHiScore(score)
//    }
//
//    suspend fun deleteHiScore(score: Score) {
//        hiScoreDao.deleteHiScore(score)
//    }
//
//    suspend fun getTopScoreAction(levelId: String): Score = hiScoreDao.getTopScoreAction(levelId)
//
//    suspend fun getTopScoreStrategy(levelId: String): Score = hiScoreDao.getTopScoreStrategy(levelId)
//
//    suspend fun getAllHighScores(): List<Score> = hiScoreDao.getAllHighScores()
//
//}