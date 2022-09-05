package com.prisma.challengeppi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;

    private String cardNumber;
    private String cardHolder;
    private Date expirationDate;
    private String cvv;
    private double amount;

    @Temporal(TemporalType.DATE)
    private Date dateOfPayment;
}
