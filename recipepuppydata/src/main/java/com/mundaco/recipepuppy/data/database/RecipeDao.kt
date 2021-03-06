package com.mundaco.recipepuppy.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.mundaco.recipepuppy.data.model.RecipeRequest

@Dao
interface RecipeDao {
    @Query("SELECT * FROM RecipeRequest WHERE `query` = :query AND page = :page")
    fun search(query: String, page: Int): RecipeRequest?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg queries: RecipeRequest)
}