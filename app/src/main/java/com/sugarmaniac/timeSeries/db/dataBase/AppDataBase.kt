package com.sugarmaniac.timeSeries.db.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sugarmaniac.timeSeries.db.dao.*
import com.sugarmaniac.timeSeries.db.dataEntity.*

@Database(entities = [TitleEntity::class], version = 1, exportSchema = false)
abstract class TitleDataBase : RoomDatabase() {

    abstract fun titleDao(): TitleDao

    companion object{
        @Volatile
        private var Instance: TitleDataBase? = null

        fun getDataBase(context: Context): TitleDataBase {
            val tempInstance = Instance
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TitleDataBase::class.java,
                    "title_database"
                ).build()
                Instance = instance
                return instance
            }
        }
    }
}

@Database(entities = [TextEntity::class], version = 1, exportSchema = false)
abstract class TextDataBase : RoomDatabase() {
    abstract fun textDao(): TextDao

    companion object{
        @Volatile
        private var Instance: TextDataBase? = null

        fun getDataBase(context: Context): TextDataBase {
            val tempInstance = Instance
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TextDataBase::class.java,
                    "text_database"
                ).build()
                Instance = instance
                return instance
            }
        }
    }
}

@Database(entities = [NumericEntity::class], version = 1, exportSchema = false)
abstract class NumericDataBase : RoomDatabase() {
    abstract fun numericDao(): NumericDao

    companion object{
        @Volatile
        private var Instance: NumericDataBase? = null

        fun getDataBase(context: Context): NumericDataBase {
            val tempInstance = Instance
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NumericDataBase::class.java,
                    "numeric_database"
                ).build()
                Instance = instance
                return instance
            }
        }
    }
}

@Database(entities = [BoolEntity::class], version = 1, exportSchema = false)
abstract class BoolDataBase : RoomDatabase() {
    abstract fun boolDao(): BoolDao

    companion object{
        @Volatile
        private var Instance: BoolDataBase? = null

        fun getDataBase(context: Context): BoolDataBase {
            val tempInstance = Instance
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BoolDataBase::class.java,
                    "bool_database"
                ).build()
                Instance = instance
                return instance
            }
        }
    }
}

@Database(entities = [NotificationEntity::class], version = 1, exportSchema = false)
abstract class NotificationDataBase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao

    companion object{
        @Volatile
        private var Instance: NotificationDataBase? = null

        fun getDataBase(context: Context): NotificationDataBase {
            val tempInstance = Instance
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotificationDataBase::class.java,
                    "notification_database"
                ).build()
                Instance = instance
                return instance
            }
        }
    }
}

