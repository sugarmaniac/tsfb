package com.sugarmaniac.timeSeries.db.repository

import androidx.lifecycle.LiveData
import com.sugarmaniac.timeSeries.db.dao.TitleDao
import com.sugarmaniac.timeSeries.db.dataEntity.TitleEntity

class TitleRepository(private val titleDao: TitleDao) {

    val readAllData: LiveData<List<TitleEntity>> = titleDao.readAll()

    suspend fun addTitle(titleEntity: TitleEntity){
        titleDao.addTitle(titleEntity)
    }

}