package com.epam.esm.validation;

import com.epam.esm.dto.BookingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

class OrderValidatorTest {
    private BookingDto testBooking;
    private List<String> certificates;
    private static final String NUMBER_STRING = "123";

    private OrderValidator orderValidator;

    @BeforeEach
    void setUp() {
        orderValidator = new OrderValidator();
        testBooking = new BookingDto();
        certificates = new ArrayList<>();
        certificates.add(NUMBER_STRING);
        testBooking.setCertificates(certificates);
    }

    @Test
    void isOrderTest() {
        System.out.println(testBooking);
        boolean actual = orderValidator.isOrder(testBooking);
        assertTrue(actual);
    }
}