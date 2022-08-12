package com.example.sliideusersbook.utils

import com.example.sliideusersbook.utils.EmailValidator.isEmailValid
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test

class EmailValidatorTest {

    @Test
    fun `given correct email when validation happens then true is returned`() {
        assertTrue(isEmailValid("test@test.com"))
    }

    @Test
    fun `given incorrect email when validation happens then false is returned`() {
        assertFalse(isEmailValid("test.com"))
    }
}