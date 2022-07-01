package com.sugarmaniac.timeSeries.db.repository

import androidx.lifecycle.LiveData
import com.sugarmaniac.timeSeries.db.dao.NumericDao
import com.sugarmaniac.timeSeries.db.dataEntity.NumericEntity

class NumericRepository(private val numericDao: NumericDao) {

    val readAllData: LiveData<List<NumericEntity>> = numericDao.readAll()

    suspend fun addNumeric(numericEntity: NumericEntity){
        numericDao.addNumeric(numericEntity)
    }

    fun readAllByTitle(title : String) : LiveData<List<NumericEntity>>{
        return numericDao.readAllByTitle(title = title)
    }

}