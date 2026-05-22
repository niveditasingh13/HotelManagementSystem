package repository.jpa;

import config.Hibernateutil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Booking;
import model.Customer;
import repository.BookingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BookingRepositoryImpl
        implements BookingRepository {

    @Override
    public Booking save(Booking booking) {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        EntityTransaction tx =
                em.getTransaction();

        try {

            tx.begin();

            em.persist(booking);

            tx.commit();

            return booking;

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
    public Booking update(Booking booking) {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        EntityTransaction tx =
                em.getTransaction();

        try {

            tx.begin();

            Booking updatedBooking =
                    em.merge(booking);

            tx.commit();

            return updatedBooking;

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

            Booking booking =
                    em.find(Booking.class, id);

            if (booking != null) {

                em.remove(booking);
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
    public Optional<Booking> findById(Long id) {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        try {

            Booking booking =
                    em.find(Booking.class, id);

            return Optional.ofNullable(booking);

        } finally {

            em.close();
        }
    }

    @Override
    public List<Booking> findAll() {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        try {

            return em.createQuery(
                            "SELECT b FROM Booking b",
                            Booking.class
                    )
                    .getResultList();

        } finally {

            em.close();
        }
    }

    @Override
    public List<Booking> findBookingsByCustomer(
            Customer customer) {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        try {

            return em.createQuery(
                            """
                            SELECT b
                            FROM Booking b
                            WHERE b.customer = :customer
                            """,
                            Booking.class
                    )
                    .setParameter(
                            "customer",
                            customer
                    )
                    .getResultList();

        } finally {

            em.close();
        }
    }

    @Override
    public List<Booking> findActiveBookings() {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        try {

            return em.createQuery(
                            """
                            SELECT b
                            FROM Booking b
                            WHERE b.status = 'CONFIRMED'
                            """,
                            Booking.class
                    )
                    .getResultList();

        } finally {

            em.close();
        }
    }

    @Override
    public boolean existsOverlappingBooking(
            Long roomId,
            LocalDate checkIn,
            LocalDate checkOut) {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        try {

            Long count =
                    em.createQuery(
                                    """
                                    SELECT COUNT(b)
                                    FROM Booking b
                                    WHERE b.room.roomId = :roomId
                                    AND b.checkInDate < :checkOut
                                    AND b.checkOutDate > :checkIn
                                    """,
                                    Long.class
                            )
                            .setParameter(
                                    "roomId",
                                    roomId
                            )
                            .setParameter(
                                    "checkIn",
                                    checkIn
                            )
                            .setParameter(
                                    "checkOut",
                                    checkOut
                            )
                            .getSingleResult();

            return count != null &&
                    count > 0;

        } finally {

            em.close();
        }
    }
}