package com.example.plantpal.data

import kotlinx.coroutines.flow.Flow

/** The only place the rest of the app talks to when it needs plant data. */
class PlantRepository(private val plantDao: PlantDao) {

    val plants: Flow<List<Plant>> = plantDao.observeAll()

    suspend fun getPlant(id: Long): Plant? = plantDao.getById(id)

    suspend fun addPlant(plant: Plant): Long = plantDao.insert(plant)

    suspend fun updatePlant(plant: Plant) = plantDao.update(plant)

    suspend fun deletePlant(plant: Plant) = plantDao.delete(plant)
}