package repository.jpa;

import config.Hibernateutil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Payment;
import model.enums.PaymentStatus;
import repository.PaymentRepository;

import java.util.List;
import java.util.Optional;

public class PaymentRepositoryImpl
        implements PaymentRepository {

    @Override
    public Payment save(Payment payment) {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        EntityTransaction tx =
                em.getTransaction();

        try {

            tx.begin();

            em.persist(payment);

            tx.commit();

            return payment;

        } catch (Exception e) {

            if (tx.isActive()) {

                tx.rollback();
            }

            throw new RuntimeException(e);

        } finally {

            em.close();
        }
    }

    @Override
    public Payment update(Payment payment) {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        EntityTransaction tx =
                em.getTransaction();

        try {

            tx.begin();

            Payment updatedPayment =
                    em.merge(payment);

            tx.commit();

            return updatedPayment;

        } catch (Exception e) {

            if (tx.isActive()) {

                tx.rollback();
            }

            throw new RuntimeException(e);

        } finally {

            em.close();
        }
    }

    @Override
    public void delete(Long id) {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        EntityTransaction tx =
                em.getTransaction();

        try {

            tx.begin();

            Payment payment =
                    em.find(Payment.class, id);

            if (payment != null) {

                em.remove(payment);
            }

            tx.commit();

        } catch (Exception e) {

            if (tx.isActive()) {

                tx.rollback();
            }

            throw new RuntimeException(e);

        } finally {

            em.close();
        }
    }

    @Override
    public Optional<Payment> findById(Long id) {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        try {

            Payment payment =
                    em.find(Payment.class, id);

            return Optional.ofNullable(payment);

        } finally {

            em.close();
        }
    }

    @Override
    public List<Payment> findAll() {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        try {

            return em.createQuery(
                            "SELECT p FROM Payment p",
                            Payment.class
                    )
                    .getResultList();

        } finally {

            em.close();
        }
    }

    @Override
    public List<Payment> findByPaymentStatus(
            PaymentStatus status) {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        try {

            return em.createQuery(
                            """
                            SELECT p
                            FROM Payment p
                            WHERE p.status = :status
                            """,
                            Payment.class
                    )
                    .setParameter(
                            "status",
                            status
                    )
                    .getResultList();

        } finally {

            em.close();
        }
    }
}