package com.epam.esm.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class CamelStringManagerTest {
    private static final String IN_TAG_NAME_STRING = "tagname";
    private static final String EXPECTED_TAG_NAME_STRING = "tagName";
    private static final String IN_TEST_STRING = "TEST";
    private static final String EXPECTED_TEST_STRING = "test";

    private CamelStringManager manager;

    @BeforeEach
    void setUp() {
        manager = new CamelStringManager();
    }

    @Test
    void toCamelStringTest() {
        String actual = manager.toCamelString(IN_TAG_NAME_STRING);
        assertEquals(actual, EXPECTED_TAG_NAME_STRING);
    }

    @Test
    void toCamelStringSecondTest() {
        String actual = manager.toCamelString(IN_TEST_STRING);
        assertEquals(actual, EXPECTED_TEST_STRING);
    }

    @Test
    void toCamelStringNegativeTest() {
        String actual = manager.toCamelString(IN_TEST_STRING);
        assertNotEquals(actual, IN_TEST_STRING);
    }
}
