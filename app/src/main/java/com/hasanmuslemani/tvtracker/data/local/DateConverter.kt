package com.hasanmuslemani.tvtracker.data.local

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {

    @TypeConverter
    fun fromDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun toDate(value: Date?): Long? {
        return value?.let { value.time }
    }
}