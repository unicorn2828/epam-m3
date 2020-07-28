package com.epam.esm.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class CommonValidatorTest {
    private static final String NUMBER_STRING = "123";
    private static final String TEST_NAME = "test";
    private static final Long TEST_ID = 1L;

    private CommonValidator commonValidator;

    @BeforeEach
    void setUp() {
        commonValidator = new CommonValidator();
    }

    @Test
    void isIdTest() {
        boolean actual = commonValidator.isId(TEST_ID);
        assertTrue(actual);
    }

    @Test
    void isNameTest() {
        boolean actual = commonValidator.isName(TEST_NAME);
        assertTrue(actual);
    }

    @Test
    void isNumberTest() {
        boolean actual = commonValidator.isNumber(NUMBER_STRING);
        assertTrue(actual);
    }
}
