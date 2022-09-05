package com.prisma.challengeppi.service;


import com.prisma.challengeppi.model.Payment;
import com.prisma.challengeppi.repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService
{
    @Autowired
    private PaymentRepo paymentRepo;

    public Payment get(int id)
    {
        Payment paymentDB = null;
        Payment paymentAux = paymentRepo.getByID2(id) ;

        if(paymentAux != null)
        {
            paymentDB = paymentAux;
        }
        return paymentDB;
    }

    public List<Payment> findAll()
    {
        return paymentRepo.findAll();
    }
    public Payment save(Payment card)
    {
        return paymentRepo.save(card);
    }
}
