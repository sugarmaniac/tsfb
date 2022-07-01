package com.sugarmaniac.timeSeries.db.dataEntity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "text_table")
data class TextEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "Title") val title: String?,
    @ColumnInfo(name = "Date") val date: String?,
    @ColumnInfo(name = "Value") val value: String?
)

@Entity(tableName = "numeric_table")
data class NumericEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "Title") val title: String?,
    @ColumnInfo(name = "Date") val date: String?,
    @ColumnInfo(name = "Value") val value: Float?
)

@Entity(tableName = "bool_table")
data class BoolEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "Title") val title: String?,
    @ColumnInfo(name = "Date") val date: String?,
    @ColumnInfo(name = "Value") val value: Boolean?
)

@Entity(tableName = "title_table")
data class TitleEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "Title") val title: String?,
    @ColumnInfo(name = "Type") val type: Int?
)

@Entity(tableName = "notification_table")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "Title") val title: String?,
    @ColumnInfo(name = "Monday") val monday: Boolean = false,
    @ColumnInfo(name = "Tuesday") val tuesday: Boolean = false,
    @ColumnInfo(name = "Wednesday") val wednesday: Boolean = false,
    @ColumnInfo(name = "Thursday") val thursday: Boolean = false,
    @ColumnInfo(name = "Friday") val friday: Boolean = false,
    @ColumnInfo(name = "Saturday") val saturday: Boolean = false,
    @ColumnInfo(name = "Sunday") val sunday: Boolean = false
)

