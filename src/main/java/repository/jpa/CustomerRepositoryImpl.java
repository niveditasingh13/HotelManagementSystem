package repository.jpa;

import config.Hibernateutil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Customer;
import repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

public class CustomerRepositoryImpl
        implements CustomerRepository {

    @Override
    public Customer save(Customer customer) {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        EntityTransaction tx =
                em.getTransaction();

        try {

            tx.begin();

            em.persist(customer);

            tx.commit();

            return customer;

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
    public Customer update(Customer customer) {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        EntityTransaction tx =
                em.getTransaction();

        try {

            tx.begin();

            Customer updatedCustomer =
                    em.merge(customer);

            tx.commit();

            return updatedCustomer;

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

            Customer customer =
                    em.find(Customer.class, id);

            if (customer != null) {

                em.remove(customer);
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
    public Optional<Customer> findById(Long id) {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        try {

            Customer customer =
                    em.find(Customer.class, id);

            return Optional.ofNullable(customer);

        } finally {

            em.close();
        }
    }

    @Override
    public List<Customer> findAll() {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        try {

            return em.createQuery(
                            "SELECT c FROM Customer c",
                            Customer.class
                    )
                    .getResultList();

        } finally {

            em.close();
        }
    }

    @Override
    public Optional<Customer> findByEmail(
            String email) {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        try {

            List<Customer> customers =
                    em.createQuery(
                                    """
                                    SELECT c
                                    FROM Customer c
                                    WHERE c.email = :email
                                    """,
                                    Customer.class
                            )
                            .setParameter(
                                    "email",
                                    email
                            )
                            .getResultList();

            if (customers.isEmpty()) {

                return Optional.empty();
            }

            return Optional.of(customers.get(0));

        } finally {

            em.close();
        }
    }
}