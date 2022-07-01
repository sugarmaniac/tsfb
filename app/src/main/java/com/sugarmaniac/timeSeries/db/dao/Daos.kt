package com.sugarmaniac.timeSeries.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sugarmaniac.timeSeries.db.dataEntity.*

@Dao
interface TitleDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTitle(title:TitleEntity)

    @Query("Select * from title_table")
    fun readAll(): LiveData<List<TitleEntity>>

    @Delete
    fun deleteTitle(title: TitleEntity)
}

@Dao
interface TextDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addText(title:TextEntity)

    @Query("Select * from text_table")
    fun readAll(): LiveData<List<TextEntity>>

    @Query("Select * from text_table where title = :title")
    fun readAllByTitle(title:String) : LiveData<List<TextEntity>>

    @Delete
    fun deleteText(title: TextEntity)
}

@Dao
interface NumericDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNumeric(title:NumericEntity)

    @Query("Select * from numeric_table")
    fun readAll(): LiveData<List<NumericEntity>>

    @Query("Select * from numeric_table where title = :title")
    fun readAllByTitle(title:String) : LiveData<List<NumericEntity>>

    @Delete
    fun deleteNumeric(title: NumericEntity)
}

@Dao
interface BoolDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBool(title:BoolEntity)

    @Query("Select * from bool_table")
    fun readAll(): LiveData<List<BoolEntity>>

    @Query("Select * from bool_table where title = :title")
    fun readAllByTitle(title:String) : LiveData<List<BoolEntity>>

    @Delete
    fun deleteBool(title: BoolEntity)
}

@Dao
interface NotificationDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNotification(notificationEntity: NotificationEntity)

    @Query("Select * from notification_table")
    fun readAll(): LiveData<List<NotificationEntity>>

    @Query("Select * from notification_table where monday = 1")
    fun readAllMonday() : LiveData<List<NotificationEntity>>

    @Query("Select * from notification_table where tuesday = 1")
    fun readAllTuesday() : LiveData<List<NotificationEntity>>

    @Query("Select * from notification_table where wednesday = 1")
    fun readAllWednesday() : LiveData<List<NotificationEntity>>

    @Query("Select * from notification_table where thursday = 1")
    fun readAllThursday() : LiveData<List<NotificationEntity>>

    @Query("Select * from notification_table where friday = 1")
    fun readAllFriday() : LiveData<List<NotificationEntity>>

    @Query("Select * from notification_table where saturday = 1")
    fun readAllSaturday() : LiveData<List<NotificationEntity>>

    @Query("Select * from notification_table where sunday = 1")
    fun readAllSunday() : LiveData<List<NotificationEntity>>

    @Delete
    fun deleteBool(title: NotificationEntity)
}

