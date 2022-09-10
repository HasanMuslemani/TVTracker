package com.hasanmuslemani.tvtracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hasanmuslemani.tvtracker.data.local.dao.TVShowDao

@Database(
    entities = [],
    version = 1,
    exportSchema = false
)
abstract class TVShowDatabase: RoomDatabase() {
    abstract val tvShowDao: TVShowDao
}