package com.sugarmaniac.timeSeries.db.repository

import androidx.lifecycle.LiveData
import com.sugarmaniac.timeSeries.db.dao.BoolDao
import com.sugarmaniac.timeSeries.db.dataEntity.BoolEntity

class BoolRepository(private val boolDao: BoolDao) {

    val readAllData: LiveData<List<BoolEntity>> = boolDao.readAll()

    suspend fun addBool(boolEntity: BoolEntity){
        boolDao.addBool(boolEntity)
    }

    fun readAllByTitle(title : String) : LiveData<List<BoolEntity>>{
        return boolDao.readAllByTitle(title = title)
    }

}