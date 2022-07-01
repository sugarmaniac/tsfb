package com.sugarmaniac.timeSeries.db.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sugarmaniac.timeSeries.db.dataBase.TitleDataBase
import com.sugarmaniac.timeSeries.db.repository.TitleRepository
import com.sugarmaniac.timeSeries.db.dataEntity.TitleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TitleViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<TitleEntity>>
    private val repository : TitleRepository

    init {
        val titleDao = TitleDataBase.getDataBase(application).titleDao()
        repository = TitleRepository(titleDao = titleDao)
        readAllData = repository.readAllData
    }

    fun addTitle(titleEntity: TitleEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.addTitle(titleEntity = titleEntity)
        }
    }

}