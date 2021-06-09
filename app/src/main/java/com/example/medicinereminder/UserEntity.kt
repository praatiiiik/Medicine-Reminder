package com.example.medicinereminder

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "data_table")
class UserEntity(@ColumnInfo(name = "MedicineName") val name:String, @ColumnInfo(name = "dose") val dose: Int , ) {
    @PrimaryKey(autoGenerate = true) var id = 0
}

@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note : UserEntity)

    @Delete
    suspend fun delete(note:UserEntity)

    @Query("Select * from data_table order by id ASC")
    fun getAllData() : LiveData<List<UserEntity>>
}