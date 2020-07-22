package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrdersDto;
import com.epam.esm.dto.TagDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class SuperTagService {

    static List<TagDto> findUserTags(OrdersDto orders) {
        List<TagDto> userTagList = new ArrayList<>();
        orders.getOrders()
              .forEach(o -> o.getCertificates()
                             .forEach(c -> c.getTags()
                                            .stream()
                                            .filter(t -> !userTagList.contains(t))
                                            .forEach(t -> userTagList.add(t))));
        return userTagList;
    }

    static List<TagDto> findSuperTagList(List<TagDto> userTagList, OrdersDto orders) {
        List<TagDto> superTagList = new ArrayList<>();
        BigDecimal maxTagCost = new BigDecimal(0);
        for (TagDto superTag : userTagList) {
            BigDecimal superTagCost = new BigDecimal(0);
            for (OrderDto order : orders.getOrders()) {
                List<CertificateDto> certificatesDto = order.getCertificates();
                for (CertificateDto certificate : certificatesDto) {
                    List<TagDto> tagsDto = certificate.getTags();
                    for (TagDto tag : tagsDto) {
                        if (tag.equals(superTag)) {
                            superTagCost = superTagCost.add(order.getOrderPrice());
                        }
                    }
                }
            }
            if (superTagCost.compareTo(maxTagCost) == 0) {
                maxTagCost = superTagCost;
                if (!superTagList.contains(superTag)) {
                    superTagList.add(superTag);
                }
            }
            if (superTagCost.compareTo(maxTagCost) > 0) {
                maxTagCost = superTagCost;
                superTagList.clear();
                superTagList.add(superTag);
            }
        }
        return superTagList;
    }
}
