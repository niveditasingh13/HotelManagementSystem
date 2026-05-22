package service;

import model.Payment;
import model.enums.PaymentStatus;
import repository.PaymentRepository;
import repository.jpa.PaymentRepositoryImpl;

import java.math.BigDecimal;
import java.util.List;

public class PaymentService {

    private final PaymentRepository
            paymentRepository =
            new PaymentRepositoryImpl();

    public Payment processPayment(
            Payment payment) {

        if (payment.getAmount()
                .compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Invalid payment amount");
        }

        payment.setStatus(
                PaymentStatus.COMPLETED);

        return paymentRepository.save(payment);
    }

    public List<Payment> getCompletedPayments() {

        return paymentRepository
                .findByPaymentStatus(
                        PaymentStatus.COMPLETED);
    }
}
