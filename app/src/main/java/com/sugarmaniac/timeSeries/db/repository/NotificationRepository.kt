package com.sugarmaniac.timeSeries.db.repository

import androidx.lifecycle.LiveData
import com.sugarmaniac.timeSeries.db.dao.NotificationDao
import com.sugarmaniac.timeSeries.db.dataEntity.NotificationEntity

class NotificationRepository(private val notificationDao: NotificationDao) {

    val readAllData: LiveData<List<NotificationEntity>> = notificationDao.readAll()

    suspend fun addNotification(notificationEntity: NotificationEntity){
        notificationDao.addNotification(notificationEntity)
    }

    fun readAllMonday() : LiveData<List<NotificationEntity>> {
        return notificationDao.readAllMonday()
    }

    fun readAllTuesday() : LiveData<List<NotificationEntity>> {
        return notificationDao.readAllTuesday()
    }

    fun readAllWednesday() : LiveData<List<NotificationEntity>> {
        return notificationDao.readAllWednesday()
    }

    fun readAllThursday() : LiveData<List<NotificationEntity>> {
        return notificationDao.readAllTuesday()
    }

    fun readAllFriday() : LiveData<List<NotificationEntity>> {
        return notificationDao.readAllFriday()
    }

    fun readAllSaturday() : LiveData<List<NotificationEntity>> {
        return notificationDao.readAllSaturday()
    }

    fun readAllSunday() : LiveData<List<NotificationEntity>> {
        return notificationDao.readAllSunday()
    }

}