package com.sugarmaniac.timeSeries.db.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sugarmaniac.timeSeries.db.dataBase.TextDataBase
import com.sugarmaniac.timeSeries.db.repository.TextRepository
import com.sugarmaniac.timeSeries.db.dataEntity.TextEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TextViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<TextEntity>>

    private val repository : TextRepository

    init {
        val textDao = TextDataBase.getDataBase(application).textDao()
        repository = TextRepository(textDao = textDao)
        readAllData = repository.readAllData
    }

    fun addText(textEntity: TextEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.addText(textEntity = textEntity)
        }
    }

    fun readAllByTitle(title: String):LiveData<List<TextEntity>>{
        return repository.readAllByTitle(title = title)
    }

}