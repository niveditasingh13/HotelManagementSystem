package repository.jpa;

import config.Hibernateutil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Room;
import model.enums.RoomStatus;
import repository.RoomRepository;

import java.util.List;
import java.util.Optional;

public class RoomRepositoryImpl
        implements RoomRepository {

    @Override
    public Room save(Room room) {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        EntityTransaction tx =
                em.getTransaction();

        try {

            tx.begin();

            em.persist(room);

            tx.commit();

            return room;

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
    public Room update(Room room) {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        EntityTransaction tx =
                em.getTransaction();

        try {

            tx.begin();

            Room updatedRoom =
                    em.merge(room);

            tx.commit();

            return updatedRoom;

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

            Room room =
                    em.find(Room.class, id);

            if (room != null) {

                em.remove(room);
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
    public Optional<Room> findById(Long id) {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        try {

            Room room =
                    em.find(Room.class, id);

            return Optional.ofNullable(room);

        } finally {

            em.close();
        }
    }

    @Override
    public List<Room> findAll() {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        try {

            return em.createQuery(
                            "SELECT r FROM Room r",
                            Room.class
                    )
                    .getResultList();

        } finally {

            em.close();
        }
    }

    @Override
    public Optional<Room> findByRoomNumber(
            String roomNumber) {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        try {

            List<Room> rooms =
                    em.createQuery(
                                    """
                                    SELECT r
                                    FROM Room r
                                    WHERE r.roomNumber = :roomNumber
                                    """,
                                    Room.class
                            )
                            .setParameter(
                                    "roomNumber",
                                    roomNumber
                            )
                            .getResultList();

            if (rooms.isEmpty()) {

                return Optional.empty();
            }

            return Optional.of(rooms.get(0));

        } finally {

            em.close();
        }
    }

    @Override
    public List<Room> findByStatus(
            RoomStatus status) {

        EntityManager em =
                Hibernateutil
                        .getEntityManagerFactory()
                        .createEntityManager();

        try {

            return em.createQuery(
                            """
                            SELECT r
                            FROM Room r
                            WHERE r.status = :status
                            """,
                            Room.class
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