package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.CertificatePriceDto;
import com.epam.esm.dto.CertificatesDto;

/**
 * The ICertificateService interface; it extends IBaseService
 *
 * @author Vitaly Kononov
 */
public interface ICertificateService extends IBaseService<CertificateDto, CertificatesDto>{

    void delete(final long certificateId);

    CertificateDto create(final CertificateDto dto);

    CertificateDto update(CertificateDto dto, Long certificateId);

    CertificateDto updatePrice(CertificatePriceDto price, Long certificateId);
}
