package com.example.plantpal.domain

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class PlantValidatorTest {

    @Test
    fun blankName_isRejected() {
        assertNotNull(PlantValidator.validateName("   "))
    }

    @Test
    fun normalName_isAccepted() {
        assertNull(PlantValidator.validateName("Basil"))
    }

    @Test
    fun tooLongName_isRejected() {
        assertNotNull(PlantValidator.validateName("a".repeat(PlantValidator.MAX_NAME_LENGTH + 1)))
    }

    @Test
    fun nonNumericInterval_isRejected() {
        assertNotNull(PlantValidator.validateInterval("abc"))
        assertNotNull(PlantValidator.validateInterval(""))
    }

    @Test
    fun zeroInterval_isRejected() {
        assertNotNull(PlantValidator.validateInterval("0"))
    }

    @Test
    fun intervalAboveLimit_isRejected() {
        assertNotNull(PlantValidator.validateInterval("366"))
    }

    @Test
    fun validInterval_isAccepted() {
        assertNull(PlantValidator.validateInterval("7"))
        assertNull(PlantValidator.validateInterval(" 14 "))
    }
}