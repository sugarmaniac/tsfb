package com.sugarmaniac.timeSeries.db.repository

import androidx.lifecycle.LiveData
import com.sugarmaniac.timeSeries.db.dao.TextDao
import com.sugarmaniac.timeSeries.db.dataEntity.TextEntity

class TextRepository(private val textDao: TextDao) {

    val readAllData: LiveData<List<TextEntity>> = textDao.readAll()

    suspend fun addText(textEntity: TextEntity){
        textDao.addText(textEntity)
    }

    fun readAllByTitle(title : String) : LiveData<List<TextEntity>>{
        return textDao.readAllByTitle(title = title)
    }

}