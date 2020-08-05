package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.ITagRepository;
import com.epam.esm.service.impl.TagService;
import com.epam.esm.validation.TagValidator;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class TagServiceTest {
    private static final String TAG_NAME = "test";
    private static final Long TAG_ID = 1L;
    private TagDto tagDto;
    private Tag tag;
    @Mock
    private ITagRepository tagRepository;
    @Mock
    private TagValidator validator;
    @Mock
    private ModelMapper mapper;
    @InjectMocks
    private TagService tagService;

    @BeforeEach
    public void setUp() {
        tag = new Tag();
        tag.setId(TAG_ID);
        tag.setTagName(TAG_NAME);
        tagDto = new TagDto();
        tagDto.setId(TAG_ID);
        tagDto.setTagName(TAG_NAME);
    }

    @Test
    public void findTest() {
        when(tagRepository.find(anyLong())).thenReturn(Optional.of(tag));
        when(mapper.map(any(), any())).thenReturn(tagDto);
        TagDto expected = tagDto;
        TagDto actual = tagService.find(TAG_ID);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createTest() {
        when(mapper.map(any(), any())).thenReturn(tag, tagDto);
        when(tagRepository.save(any())).thenReturn(tag);
        TagDto expected = tagDto;
        TagDto actual = tagService.create(tagDto);
        Assert.assertEquals(expected, actual);
    }
}
