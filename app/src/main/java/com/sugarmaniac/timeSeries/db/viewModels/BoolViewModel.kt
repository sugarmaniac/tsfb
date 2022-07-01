package com.sugarmaniac.timeSeries.db.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sugarmaniac.timeSeries.db.dataBase.BoolDataBase
import com.sugarmaniac.timeSeries.db.repository.BoolRepository
import com.sugarmaniac.timeSeries.db.dataEntity.BoolEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BoolViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<BoolEntity>>
    private val repository : BoolRepository

    init {
        val boolDao = BoolDataBase.getDataBase(application).boolDao()
        repository = BoolRepository(boolDao = boolDao)
        readAllData = repository.readAllData
    }

    fun addBool(boolEntity: BoolEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.addBool(boolEntity = boolEntity)
        }
    }

    fun readAllByTitle(title: String):LiveData<List<BoolEntity>>{
        return repository.readAllByTitle(title = title)
    }

}