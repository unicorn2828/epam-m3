package com.epam.test.repository;

import com.epam.esm.model.Tag;
import com.epam.esm.repository.ITagRepository;
import com.epam.test.config.RepositoryTestConfig;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = RepositoryTestConfig.class)
public class TagRepositoryTest {
    private static final Long TEST_ID = 1L;
    private static final int TEST_NUMBER = 1;
    private static final String TEST_NAME = "test";
    private static final String TEST_SQL = "SELECT x FROM Tag x WHERE x.tagName LIKE CONCAT('test', '%')";
    private Tag expected;

    @Autowired
    private ITagRepository tagRepository;

    @BeforeEach
    public void setup() {
        expected = new Tag();
        expected.setId(TEST_ID);
        expected.setTagName(TEST_NAME);
        expected.setCertificates(new ArrayList<>());
    }

    @Test
    public void findByIdTest() {
        Tag actual = tagRepository.find(TEST_ID).get();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByNameTest() {
        Optional<Tag> tagList = tagRepository.findByName(TEST_NAME);
        Tag actual = tagList.get();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findAllTest() {
        List<Tag> actual = tagRepository.findAll(TEST_NUMBER, TEST_NUMBER, TEST_SQL);
        Assert.assertFalse(actual.isEmpty());
    }
}
