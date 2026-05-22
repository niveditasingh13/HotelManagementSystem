package repository;

import model.Payment;
import model.enums.PaymentStatus;

import java.util.List;

public interface PaymentRepository extends GenericRepository<Payment, Long>{

    List<Payment> findByPaymentStatus(PaymentStatus status);
}
