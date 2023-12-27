package com.rzl.movapp.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rzl.movapp.core.data.source.local.entity.PopularEntity

@Database(entities = [PopularEntity::class], version = 4, exportSchema = false)
abstract class  PopularDatabase : RoomDatabase(){
    abstract fun populerDao(): PopulerDao
}