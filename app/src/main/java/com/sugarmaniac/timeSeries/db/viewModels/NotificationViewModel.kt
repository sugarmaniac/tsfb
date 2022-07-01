package com.sugarmaniac.timeSeries.db.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sugarmaniac.timeSeries.db.dataBase.NotificationDataBase
import com.sugarmaniac.timeSeries.db.repository.NotificationRepository
import com.sugarmaniac.timeSeries.db.dataEntity.NotificationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<NotificationEntity>>
    private val repository : NotificationRepository

    init {
        val notificationDao = NotificationDataBase.getDataBase(application).notificationDao()
        repository = NotificationRepository(notificationDao = notificationDao)
        readAllData = repository.readAllData
    }

    fun addBool(notificationEntity: NotificationEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.addNotification(notificationEntity = notificationEntity)
        }
    }

    fun readAllMonday() : LiveData<List<NotificationEntity>>{
        return repository.readAllMonday()
    }

    fun readAllTuesday() : LiveData<List<NotificationEntity>>{
        return repository.readAllTuesday()
    }

    fun readAllWednesday() : LiveData<List<NotificationEntity>>{
        return repository.readAllWednesday()
    }

    fun readAllThursday() : LiveData<List<NotificationEntity>>{
        return repository.readAllThursday()
    }

    fun readAllFriday() : LiveData<List<NotificationEntity>>{
        return repository.readAllFriday()
    }

    fun readAllSaturday() : LiveData<List<NotificationEntity>>{
        return repository.readAllSaturday()
    }

    fun readAllSunday() : LiveData<List<NotificationEntity>>{
        return repository.readAllSunday()
    }


}