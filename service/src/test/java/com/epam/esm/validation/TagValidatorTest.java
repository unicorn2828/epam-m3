package com.epam.esm.validation;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
class TagValidatorTest {
    private static final String TAG_NAME = "test";
    private static final Long TAG_ID = 1L;
    private TagDto test;

    @Mock
    private TagValidator tagValidator;

    @Mock
    private CommonValidator commonValidator;

    @BeforeEach
    void setUp() {
        commonValidator = new CommonValidator();
        tagValidator = new TagValidator(commonValidator);
        test = new TagDto();
        test.setId(TAG_ID);
        test.setTagName(TAG_NAME);
    }

    @Test
    void isTagTest() {
        boolean actual = tagValidator.isTag(test);
        assertTrue(actual);
    }

    @Test
    void isIdTest() {
        boolean actual = tagValidator.isId(test.getId());
        assertTrue(actual);
    }

    @Test
    void isTagNegative() {
        test.setTagName(null);
        assertThrows(ServiceException.class, () -> {
            tagValidator.isTag(test);
        });
    }

    @Test
    void isTagNegativeNull() {
        test = null;
        assertThrows(ServiceException.class, () -> {
            tagValidator.isTag(test);
        });
    }

    @AfterEach
    public void tierDown() {
        test = null;
    }
}
