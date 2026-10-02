package com.example

import com.example.data.model.Category
import com.example.util.CryptoUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun cryptoUtils_sha256_hashesCorrectly() {
        val hash = CryptoUtils.sha256("123456")
        assertNotNull(hash)
        assertEquals(64, hash.length)
        // Verify consistent output for same input
        assertEquals(hash, CryptoUtils.sha256("123456"))
    }

    @Test
    fun categories_containDefaultList() {
        val categories = Category.DEFAULT_CATEGORIES
        assertTrue(categories.isNotEmpty())
        assertTrue(categories.any { it.id == "quran" })
        assertTrue(categories.any { it.id == "stories" })
        assertTrue(categories.any { it.id == "education" })
    }
}
