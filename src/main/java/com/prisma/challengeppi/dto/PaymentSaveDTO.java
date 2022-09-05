package com.prisma.challengeppi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PaymentSaveDTO extends BaseDTO
{
    @Size(min = 16, max = 19)
    private String cardNumber;
    private String cardHolder;
    private Date expirationDate;
    private String cvv;
    @DecimalMax(value = "999999.99", inclusive = true)
    private Double amount;
    private String description;
}
