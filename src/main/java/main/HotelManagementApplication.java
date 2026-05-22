package main;

import controller.AuthController;
import controller.BookingController;
import controller.PaymentController;
import controller.RoomController;
import model.Booking;
import model.Customer;
import model.Payment;
import model.Room;
import model.enums.PaymentStatus;
import model.enums.RoomStatus;
import model.enums.RoomType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class HotelManagementApplication {

    private static final Scanner scanner =
            new Scanner(System.in);

    private static final AuthController
            authController =
            new AuthController();

    private static final RoomController
            roomController =
            new RoomController();

    private static final BookingController
            bookingController =
            new BookingController();

    private static final PaymentController
            paymentController =
            new PaymentController();

    public static void main(String[] args) {

        boolean running = true;

        while (running) {

            showMainMenu();

            int choice =
                    Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1 -> registerCustomer();

                case 2 -> loginCustomer();

                case 3 -> addRoom();

                case 4 -> viewAllRooms();

                case 5 -> createBooking();

                case 6 -> viewBookings();

                case 7 -> processPayment();

                case 8 -> running = false;

                default ->
                        System.out.println(
                                "Invalid Choice");
            }
        }

        System.out.println(
                "Application Closed");
    }
    private static void showMainMenu() {

        System.out.println("""
                
                ===================================
                     HOTEL MANAGEMENT SYSTEM
                ===================================
                
                1. Register Customer
                2. Login
                3. Add Room
                4. View Rooms
                5. Create Booking
                6. View Bookings
                7. Process Payment
                8. Exit
                
                Enter Choice :
                """);
    }
    private static void registerCustomer() {

        try {

            Customer customer = new Customer();

            System.out.print("Enter Name : ");
            customer.setName(scanner.nextLine());

            System.out.print("Enter Email : ");
            customer.setEmail(scanner.nextLine());

            System.out.print("Enter Phone : ");
            customer.setPhone(scanner.nextLine());

            System.out.print("Enter Password : ");
            customer.setPasswordHash(scanner.nextLine());

            Customer savedCustomer =
                    authController
                            .registerCustomer(customer);
            if (savedCustomer != null) {

                System.out.println(
                        "Customer Registered Successfully");
            }

        } catch (Exception e) {

            System.out.println(
                    "Registration Error : "
                            + e.getMessage());
        }
    }

    private static void loginCustomer() {

        try {

            System.out.print("Enter Email : ");
            String email = scanner.nextLine();

            System.out.print("Enter Password : ");
            String password = scanner.nextLine();

            Customer customer =
                    authController.login(
                            email,
                            password
                    );

            if (customer != null) {

                System.out.println(
                        "Login Successful");

                System.out.println(
                        "Welcome "
                                + customer.getName());
            }
        } catch (Exception e) {

            System.out.println(
                    "Login Error : "
                            + e.getMessage());
        }
    }
    private static void addRoom() {

        try {

            Room room = new Room();

            System.out.print("Room Number : ");
            room.setRoomNumber(scanner.nextLine());

            System.out.println("""
                    
                    Select Room Type
                    1. STANDARD
                    2. DELUXE
                    3. SUITE
                    """);

            int typeChoice =
                    Integer.parseInt(scanner.nextLine());

            switch (typeChoice) {

                case 1 ->
                        room.setRoomType(
                                RoomType.STANDARD);

                case 2 ->
                        room.setRoomType(
                                RoomType.DELUXE);

                case 3 ->
                        room.setRoomType(
                                RoomType.SUITE);

                default ->
                        throw new RuntimeException(
                                "Invalid Room Type");
            }

            System.out.print(
                    "Price Per Night : ");

            room.setPricePerNight(
                    new BigDecimal(
                            scanner.nextLine()));
            room.setStatus(
                    RoomStatus.AVAILABLE);

            Room savedRoom =
                    roomController.addRoom(room);

            if (savedRoom != null) {

                System.out.println(
                        "Room Added Successfully");
            }

        } catch (Exception e) {

            System.out.println(
                    "Room Creation Error : "
                            + e.getMessage());
            }

    }
    private static void viewAllRooms() {

        List<Room> rooms =
                roomController.getAllRooms();

        System.out.println("""
                
                ========= ROOM LIST =========
                """);

        for (Room room : rooms) {

            System.out.println(
                    "Room ID : "
                            + room.getRoomId());

            System.out.println(
                    "Room Number : "
                            + room.getRoomNumber());

            System.out.println(
                    "Room Type : "
                            + room.getRoomType());
            System.out.println(
                    "Price : "
                            + room.getPricePerNight());

            System.out.println(
                    "Status : "
                            + room.getStatus());

            System.out.println(
                    "-------------------------");
        }
    }
    private static void createBooking() {

        try {

            Booking booking = new Booking();

            Customer customer =
                    new Customer();

            Room room =
                    new Room();

            System.out.print(
                    "Enter Customer ID : ");
            //long id= scanner.nextLong();

            customer.setCustomerId(
                    Long.parseLong(
                            scanner.nextLine()));


            System.out.print(
                    "Enter Room ID : ");

            room.setRoomId(
                    Long.parseLong(
                            scanner.nextLine()));

            booking.setCustomer(customer);

            booking.setRoom(room);

            System.out.print(
                    "Enter Check In Date (yyyy-mm-dd): ");

            booking.setCheckInDate(
                    LocalDate.parse(
                            scanner.nextLine()));
            System.out.print(
                    "Enter Check Out Date (yyyy-mm-dd): ");

            booking.setCheckOutDate(
                    LocalDate.parse(
                            scanner.nextLine()));

            Booking savedBooking =
                    bookingController
                            .createBooking(booking);
            if (savedBooking != null) {

                System.out.println(
                        "Booking Confirmed");
            }

        } catch (Exception e) {

            System.out.println(
                    "Booking Error : "
                            + e.getMessage());
        }
    }
    private static void viewBookings() {

        List<Booking> bookings =
                bookingController
                        .getAllBookings();

        System.out.println("""
                
                ======== BOOKINGS ========
                """);

        for (Booking booking : bookings) {

            System.out.println(
                    "Booking ID : "
                            + booking.getBookingId());

            System.out.println(
                    "Customer : "
                            + booking.getCustomer()
                            .getName());

            System.out.println(
                    "Room Number : "
                            + booking.getRoom()
                            .getRoomNumber());
            System.out.println(
                    "Check In : "
                            + booking.getCheckInDate());

            System.out.println(
                    "Check Out : "
                            + booking.getCheckOutDate());

            System.out.println(
                    "Status : "
                            + booking.getStatus());

            System.out.println(
                    "------------------------");
        }
    }
    private static void processPayment() {

        try {

            Payment payment =
                    new Payment();

            Booking booking =
                    new Booking();

            System.out.print(
                    "Enter Booking ID : ");

            booking.setBookingId(
                    Long.parseLong(
                            scanner.nextLine()));

            payment.setBooking(booking);

            System.out.print(
                    "Enter Amount : ");
            payment.setAmount(
                    new BigDecimal(
                            scanner.nextLine()));

            payment.setStatus(
                    PaymentStatus.COMPLETED);

            Payment savedPayment =
                    paymentController
                            .processPayment(payment);

            if (savedPayment != null) {

                System.out.println(
                        "Payment Successful");
            }

        } catch (Exception e) {

            System.out.println(
                    "Payment Error : "
                            + e.getMessage());
        }
    }
}