package service;

import model.Customer;
import model.enums.UserRole;
import repository.CustomerRepository;
import repository.jpa.CustomerRepositoryImpl;

import java.util.Optional;

public class AuthService {

    private final CustomerRepository
            customerRepository =
            new CustomerRepositoryImpl();

    public Customer registerCustomer(
            Customer customer) {

        Optional<Customer> existingCustomer =
                customerRepository
                        .findByEmail(customer.getEmail());

        if (existingCustomer.isPresent()) {

            throw new RuntimeException(
                    "Email already registered");
        }

        //customer.setRole(UserRole.CUSTOMER);

        return customerRepository.save(customer);
    }

    public Customer login(
            String email,
            String passwordHash) {

        Customer customer =
                customerRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid email"));

        if (!customer.getPasswordHash()
                .equals(passwordHash)) {

            throw new RuntimeException(
                    "Invalid password");
        }

        return customer;
    }
}

