package com.example.medicinereminder

import androidx.lifecycle.LiveData

class Repositiory (private val dao: DataDao) {

    val allData: LiveData<List<UserEntity>> = dao.getAllData()

    suspend fun insert(data : UserEntity){
        dao.insert(data)
    }
    suspend fun delete(data: UserEntity){
        dao.delete(data)
    }
}