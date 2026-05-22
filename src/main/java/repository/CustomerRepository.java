package repository;

import model.Customer;

import java.util.Optional;

public interface CustomerRepository extends GenericRepository<Customer, Long>{

    Optional<Customer> findByEmail(String email);
}
