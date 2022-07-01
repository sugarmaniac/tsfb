package com.sugarmaniac.timeSeries.db.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sugarmaniac.timeSeries.db.dataBase.NumericDataBase
import com.sugarmaniac.timeSeries.db.repository.NumericRepository
import com.sugarmaniac.timeSeries.db.dataEntity.NumericEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NumericViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<NumericEntity>>
    private val repository : NumericRepository

    init {
        val numericDao = NumericDataBase.getDataBase(application).numericDao()
        repository = NumericRepository(numericDao = numericDao)
        readAllData = repository.readAllData
    }

    fun addNumeric(numericEntity: NumericEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.addNumeric(numericEntity = numericEntity)
        }
    }

    fun readAllByTitle(title: String):LiveData<List<NumericEntity>>{
        return repository.readAllByTitle(title = title)
    }

}