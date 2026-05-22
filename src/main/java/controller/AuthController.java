package controller;

import model.Customer;
import service.AuthService;

public class AuthController {

    private final AuthService
            authService =
            new AuthService();

    public Customer registerCustomer(
            Customer customer) {

        try {

            return authService
                    .registerCustomer(customer);

        } catch (Exception e) {

            System.out.println(
                    "Registration Failed : "
                            + e.getMessage());

            return null;
        }
    }

    public Customer login(
            String email,
            String password) {

        try {

            return authService
                    .login(email, password);

        } catch (Exception e) {

            System.out.println(
                    "Login Failed : "
                            + e.getMessage());

            return null;
        }
    }
}
