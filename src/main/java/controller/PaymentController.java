package controller;

import model.Payment;
import service.PaymentService;

import java.util.List;

public class PaymentController {

    private final PaymentService
            paymentService =
            new PaymentService();

    public Payment processPayment(
            Payment payment) {

        try {

            return paymentService
                    .processPayment(payment);

        } catch (Exception e) {

            System.out.println(
                    "Payment Failed : "
                            + e.getMessage());

            return null;
        }
    }

    public List<Payment> getCompletedPayments() {

        return paymentService
                .getCompletedPayments();
    }
}
