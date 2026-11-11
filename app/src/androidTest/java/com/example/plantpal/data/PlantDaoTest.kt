package com.example.plantpal.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class PlantDaoTest {

    private lateinit var database: PlantDatabase
    private lateinit var dao: PlantDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, PlantDatabase::class.java).build()
        dao = database.plantDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    private fun plant(name: String) = Plant(
        name = name,
        wateringIntervalDays = 7,
        lightNeed = LightNeed.MEDIUM,
        createdDate = LocalDate.of(2026, 11, 5)
    )

    @Test
    fun insertPlant_canBeReadBack() = runBlocking<Unit> {
        val id = dao.insert(plant("Basil"))

        val saved = dao.getById(id)

        assertNotNull(saved)
        assertEquals("Basil", saved?.name)
        assertEquals(7, saved?.wateringIntervalDays)
        assertEquals(LightNeed.MEDIUM, saved?.lightNeed)
        // Checks that the LocalDate TypeConverter works both ways.
        assertEquals(LocalDate.of(2026, 11, 5), saved?.createdDate)
    }

    @Test
    fun observeAll_returnsPlantsSortedByName() = runBlocking<Unit> {
        dao.insert(plant("Mint"))
        dao.insert(plant("basil"))
        dao.insert(plant("Aloe"))

        val names = dao.observeAll().first().map { it.name }

        assertEquals(listOf("Aloe", "basil", "Mint"), names)
    }

    @Test
    fun updatePlant_changesStoredValues() = runBlocking<Unit> {
        val id = dao.insert(plant("Basil"))
        val saved = dao.getById(id)!!

        dao.update(saved.copy(name = "Thai basil", wateringIntervalDays = 3))

        val updated = dao.getById(id)
        assertEquals("Thai basil", updated?.name)
        assertEquals(3, updated?.wateringIntervalDays)
    }

    @Test
    fun deletePlant_removesIt() = runBlocking<Unit> {
        val id = dao.insert(plant("Basil"))
        val saved = dao.getById(id)!!

        dao.delete(saved)

        assertNull(dao.getById(id))
    }
}