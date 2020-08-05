package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.model.Certificate;
import com.epam.esm.repository.ICertificateRepository;
import com.epam.esm.service.impl.CertificateService;
import com.epam.esm.validation.CertificateValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class CertificateServiceTest {
    private static final Long CERTIFICATE_ID = 1L;
    @Mock
    private ICertificateRepository certificateRepository;
    @Mock
    private CertificateValidator validator;
    @Mock
    private ModelMapper mapper;
    @InjectMocks
    private CertificateService certificateService;

    @Test
    public void findTest() {
        when(certificateRepository.find(anyLong())).thenReturn(Optional.of(new Certificate()));
        when(mapper.map(any(), any())).thenReturn(new CertificateDto());
        CertificateDto expected = new CertificateDto();
        CertificateDto actual = certificateService.find(CERTIFICATE_ID);
        System.out.println(expected + " " + actual);
        Assert.assertEquals(expected, actual);
    }
}
