package com.epam.esm.repository.impl;

import com.epam.esm.model.Order;
import com.epam.esm.repository.IOrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class OrderRepository extends BaseAbstractRepository<Order> implements IOrderRepository {

    public OrderRepository(EntityManager em) {
        super(em, Order.class);
    }
}