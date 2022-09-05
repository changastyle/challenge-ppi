package com.prisma.challengeppi.repository;

import com.prisma.challengeppi.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Integer>
{
    @Query(value = "SELECT p FROM Payment p WHERE p.id = ?1")
    public Payment getByID2(int id);
}
