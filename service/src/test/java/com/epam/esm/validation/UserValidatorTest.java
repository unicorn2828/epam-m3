package com.epam.esm.validation;

import com.epam.esm.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest {
    private static final String USER_LOGIN = "test";
    private static final Long USER_ID = 1L;
    private UserDto testUser;
    @Mock
    private CommonValidator commonValidator;
    @InjectMocks
    private UserValidator userValidator;

    @BeforeEach
    public void setUp() {
        testUser = new UserDto();
        testUser.setId(USER_ID);
        testUser.setLogin(USER_LOGIN);
    }

    @Test
    public void isIdTest() {
        when(commonValidator.isId(anyLong())).thenReturn(true);
        boolean actual = userValidator.isId(testUser.getId());
        assertTrue(actual);
    }

    @Test
    public void isLoginTest() {
        when(commonValidator.isName(anyString())).thenReturn(true);
        boolean actual = userValidator.isLogin(testUser.getLogin());
        assertTrue(actual);
    }
}
