package com.prisma.challengeppi.controller;
import java.util.*;

import com.prisma.challengeppi.dto.PaymentSaveDTO;
import com.prisma.challengeppi.model.Payment;
import com.prisma.challengeppi.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/payments")
public class PaymentController
{
    @Autowired
    PaymentService paymentService;

    @GetMapping(value = "/")
    public List<Payment> findAll()
    {
        return paymentService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity get(@PathVariable int id)
    {
        ResponseEntity response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("PAYMENT WITH ID " + id + " NOT FOUND IN DATABASE");

        Payment paymentDB = paymentService.get(id);

        if(paymentDB != null)
        {
            response = ResponseEntity.status(HttpStatus.OK).body(paymentDB);
        }

        return response;
    }



    @PostMapping(value = "/")
    public ResponseEntity save(@Valid @RequestBody PaymentSaveDTO paymentSaveDTO)
    {
        ResponseEntity response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR");

        Payment payment = (Payment) paymentSaveDTO.toEntity(Payment.class);
        boolean validated = true;

        if(payment != null)
        {
            if(payment.getAmount() > 999999.99 )
            {
                validated = false;
                response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("AMOUNT SHOULD BE LOWER THAN 999999.99");
            }
            if(payment.getCardNumber() != null)
            {
                if(!payment.getCardNumber().startsWith("4"))
                {
                    response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PAYMENT METHOD SHOULD START WITH 4");
                    validated = false;
                }
                if(payment.getCardNumber().length() < 16 || payment.getCardNumber().length() > 19)
                {
                    response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PAYMENT METHOD LENGTH SHOULD BE BETWEEN 16 AN 19 CHARS");
                    validated = false;
                }
            }
            else
            {
                validated = false;
                response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PAYMENT WITHOUT CARD NUMBER");
            }

            if(validated)
            {
                // TODO: SEND EMAIL + DISCOUNT AMOUNT OF CARD + WHATSAPP SEND TEXT:
                payment.setDateOfPayment(new Date());
                payment = paymentService.save(payment);
                response = ResponseEntity.ok().body(payment);
            }
        }



        return response;
    }
}