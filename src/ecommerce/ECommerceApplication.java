package ecommerce;

import ecommerce.model.*;
import ecommerce.repository.impl.*;
import ecommerce.service.*;
import ecommerce.util.FormatUtil;
import java.math.BigDecimal;
import java.util.Scanner;

public class ECommerceApplication {
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final Scanner scanner;

    public ECommerceApplication() {
        this.productService = new ProductService(new ProductRepositoryImpl());
        this.userService = new UserService(new UserRepositoryImpl());
        this.orderService = new OrderService(new OrderRepositoryImpl(), productService);
        this.scanner = new Scanner(System.in);
        initializeSampleData();
    }

    private void initializeSampleData() {
        Customer customer1 = new Customer(null, "João Silva", "joao@email.com", "senha123", 
                "12345678901", "Rua A, 123", "11987654321");
        Customer customer2 = new Customer(null, "Maria Santos", "maria@email.com", "senha456",
                "98765432109", "Rua B, 456", "11912345678");
        Admin admin = new Admin(null, "Admin", "admin@ecommerce.com", "admin123", "IT", "ADM001");

        userService.createUser(customer1);
        userService.createUser(customer2);
        userService.createUser(admin);

        PhysicalProduct notebook = new PhysicalProduct(null, "Notebook Dell", 
                "Notebook Dell Inspiron 15", BigDecimal.valueOf(3500.00), "Electronics",
                2.5, 20.0, 35.0, 25.0, 10);
        PhysicalProduct mouse = new PhysicalProduct(null, "Mouse Logitech", 
                "Mouse sem fio Logitech MX Master", BigDecimal.valueOf(250.00), "Electronics",
                0.3, 5.0, 12.0, 7.0, 50);
        DigitalProduct ebook = new DigitalProduct(null, "Java Programming", 
                "Complete Java Programming Guide", BigDecimal.valueOf(49.90), "Books",
                "https://download.com/java-book", 15.5, "PDF", 5, 365);
        DigitalProduct course = new DigitalProduct(null, "Web Development Course",
                "Complete Web Development Bootcamp", BigDecimal.valueOf(199.90), "Education",
                "https://download.com/web-course", 2500.0, "MP4", 3, 180);

        productService.createProduct(notebook);
        productService.createProduct(mouse);
        productService.createProduct(ebook);
        productService.createProduct(course);

        System.out.println("\n✓ Sample data initialized successfully!\n");
    }

    public void start() {
        while (true) {
            displayMainMenu();
            int choice = readInt("Choose an option: ");

            try {
                switch (choice) {
                    case 1:
                        productMenu();
                        break;
                    case 2:
                        userMenu();
                        break;
                    case 3:
                        orderMenu();
                        break;
                    case 4:
                        displayStatistics();
                        break;
                    case 0:
                        System.out.println("\nThank you for using E-Commerce System! Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("\n❌ Error: " + e.getMessage() + "\n");
            }
        }
    }

    private void displayMainMenu() {
        FormatUtil.printHeader("E-COMMERCE MANAGEMENT SYSTEM");
        System.out.println("1. Product Management");
        System.out.println("2. User Management");
        System.out.println("3. Order Management");
        System.out.println("4. Statistics");
        System.out.println("0. Exit");
        FormatUtil.printSeparator();
    }

    private void productMenu() {
        while (true) {
            FormatUtil.printHeader("PRODUCT MANAGEMENT");
            System.out.println("1. Create Product");
            System.out.println("2. List All Products");
            System.out.println("3. Find Product by ID");
            System.out.println("4. Update Product");
            System.out.println("5. Delete Product");
            System.out.println("6. Update Stock");
            System.out.println("7. List by Category");
            System.out.println("0. Back");
            FormatUtil.printSeparator();

            int choice = readInt("Choose an option: ");

            try {
                switch (choice) {
                    case 1:
                        createProduct();
                        break;
                    case 2:
                        productService.displayAllProducts();
                        break;
                    case 3:
                        findProductById();
                        break;
                    case 4:
                        updateProduct();
                        break;
                    case 5:
                        deleteProduct();
                        break;
                    case 6:
                        updateStock();
                        break;
                    case 7:
                        listByCategory();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("\n❌ Error: " + e.getMessage() + "\n");
            }
        }
    }

    private void createProduct() {
        System.out.println("\n--- Create New Product ---");
        System.out.println("1. Physical Product");
        System.out.println("2. Digital Product");
        int type = readInt("Product type: ");

        String name = readString("Name: ");
        String description = readString("Description: ");
        BigDecimal price = readBigDecimal("Price: ");
        String category = readString("Category: ");

        Product product;
        if (type == 1) {
            double weight = readDouble("Weight (kg): ");
            double height = readDouble("Height (cm): ");
            double width = readDouble("Width (cm): ");
            double length = readDouble("Length (cm): ");
            int stock = readInt("Stock quantity: ");
            product = new PhysicalProduct(null, name, description, price, category,
                    weight, height, width, length, stock);
        } else {
            String downloadUrl = readString("Download URL: ");
            double fileSize = readDouble("File size (MB): ");
            String format = readString("File format: ");
            int downloadLimit = readInt("Download limit: ");
            int validity = readInt("Validity (days): ");
            product = new DigitalProduct(null, name, description, price, category,
                    downloadUrl, fileSize, format, downloadLimit, validity);
        }

        productService.createProduct(product);
        System.out.println("\n✓ Product created successfully! ID: " + product.getId() + "\n");
    }

    private void findProductById() {
        Long id = readLong("Product ID: ");
        Product product = productService.getProduct(id);
        System.out.println();
        product.displayProductDetails();
        System.out.println();
    }

    private void updateProduct() {
        Long id = readLong("Product ID to update: ");
        Product product = productService.getProduct(id);
        
        System.out.println("\nCurrent name: " + product.getName());
        String name = readString("New name (press Enter to keep): ");
        if (!name.isEmpty()) product.setName(name);

        System.out.println("Current price: " + product.getPrice());
        String priceStr = readString("New price (press Enter to keep): ");
        if (!priceStr.isEmpty()) product.setPrice(new BigDecimal(priceStr));

        productService.updateProduct(product);
        System.out.println("\n✓ Product updated successfully!\n");
    }

    private void deleteProduct() {
        Long id = readLong("Product ID to delete: ");
        productService.deleteProduct(id);
        System.out.println("\n✓ Product deleted successfully!\n");
    }

    private void updateStock() {
        Long id = readLong("Product ID: ");
        int quantity = readInt("Quantity to add: ");
        productService.updateStock(id, quantity);
        System.out.println();
    }

    private void listByCategory() {
        String category = readString("Category: ");
        var products = productService.getProductsByCategory(category);
        System.out.println("\n=== Products in category: " + category + " ===");
        if (products.isEmpty()) {
            System.out.println("No products found in this category.");
        } else {
            products.forEach(Product::displayProductDetails);
        }
        System.out.println();
    }

    private void userMenu() {
        while (true) {
            FormatUtil.printHeader("USER MANAGEMENT");
            System.out.println("1. Create User");
            System.out.println("2. List All Users");
            System.out.println("3. Find User by ID");
            System.out.println("4. Update User");
            System.out.println("5. Delete User");
            System.out.println("6. Activate/Deactivate User");
            System.out.println("0. Back");
            FormatUtil.printSeparator();

            int choice = readInt("Choose an option: ");

            try {
                switch (choice) {
                    case 1:
                        createUser();
                        break;
                    case 2:
                        userService.displayAllUsers();
                        break;
                    case 3:
                        findUserById();
                        break;
                    case 4:
                        updateUser();
                        break;
                    case 5:
                        deleteUser();
                        break;
                    case 6:
                        toggleUserStatus();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("\n❌ Error: " + e.getMessage() + "\n");
            }
        }
    }

    private void createUser() {
        System.out.println("\n--- Create New User ---");
        System.out.println("1. Customer");
        System.out.println("2. Admin");
        int type = readInt("User type: ");

        String name = readString("Name: ");
        String email = readString("Email: ");
        String password = readString("Password: ");

        User user;
        if (type == 1) {
            String cpf = readString("CPF: ");
            String address = readString("Address: ");
            String phone = readString("Phone: ");
            user = new Customer(null, name, email, password, cpf, address, phone);
        } else {
            String department = readString("Department: ");
            String employeeCode = readString("Employee Code: ");
            user = new Admin(null, name, email, password, department, employeeCode);
        }

        userService.createUser(user);
        System.out.println("\n✓ User created successfully! ID: " + user.getId() + "\n");
    }

    private void findUserById() {
        Long id = readLong("User ID: ");
        User user = userService.getUser(id);
        System.out.println();
        user.displayInfo();
        System.out.println();
    }

    private void updateUser() {
        Long id = readLong("User ID to update: ");
        User user = userService.getUser(id);
        
        System.out.println("\nCurrent name: " + user.getName());
        String name = readString("New name (press Enter to keep): ");
        if (!name.isEmpty()) user.setName(name);

        System.out.println("Current email: " + user.getEmail());
        String email = readString("New email (press Enter to keep): ");
        if (!email.isEmpty()) user.setEmail(email);

        userService.updateUser(user);
        System.out.println("\n✓ User updated successfully!\n");
    }

    private void deleteUser() {
        Long id = readLong("User ID to delete: ");
        userService.deleteUser(id);
        System.out.println("\n✓ User deleted successfully!\n");
    }

    private void toggleUserStatus() {
        Long id = readLong("User ID: ");
        User user = userService.getUser(id);
        
        if (user.isActive()) {
            userService.deactivateUser(id);
        } else {
            userService.activateUser(id);
        }
        System.out.println();
    }

    private void orderMenu() {
        while (true) {
            FormatUtil.printHeader("ORDER MANAGEMENT");
            System.out.println("1. Create Order");
            System.out.println("2. List All Orders");
            System.out.println("3. Find Order by ID");
            System.out.println("4. Add Item to Order");
            System.out.println("5. Update Order Status");
            System.out.println("6. Apply Discount");
            System.out.println("7. Cancel Order");
            System.out.println("0. Back");
            FormatUtil.printSeparator();

            int choice = readInt("Choose an option: ");

            try {
                switch (choice) {
                    case 1:
                        createOrder();
                        break;
                    case 2:
                        orderService.displayAllOrders();
                        break;
                    case 3:
                        findOrderById();
                        break;
                    case 4:
                        addItemToOrder();
                        break;
                    case 5:
                        updateOrderStatus();
                        break;
                    case 6:
                        applyDiscount();
                        break;
                    case 7:
                        cancelOrder();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("\n❌ Error: " + e.getMessage() + "\n");
            }
        }
    }

    private void createOrder() {
        System.out.println("\n--- Create New Order ---");
        Long customerId = readLong("Customer ID: ");
        User user = userService.getUser(customerId);
        
        if (!(user instanceof Customer)) {
            System.out.println("User is not a customer!");
            return;
        }

        Customer customer = (Customer) user;
        String paymentMethod = readString("Payment method: ");
        
        Order order = orderService.createOrder(customer, paymentMethod);
        System.out.println("\n✓ Order created successfully! ID: " + order.getId() + "\n");
    }

    private void findOrderById() {
        Long id = readLong("Order ID: ");
        Order order = orderService.getOrder(id);
        System.out.println();
        order.displayOrderSummary();
        System.out.println();
    }

    private void addItemToOrder() {
        Long orderId = readLong("Order ID: ");
        Long productId = readLong("Product ID: ");
        int quantity = readInt("Quantity: ");
        
        Product product = productService.getProduct(productId);
        orderService.addItemToOrder(orderId, product, quantity);
        System.out.println();
    }

    private void updateOrderStatus() {
        Long id = readLong("Order ID: ");
        System.out.println("\n1. Confirm");
        System.out.println("2. Process");
        System.out.println("3. Ship");
        System.out.println("4. Deliver");
        int action = readInt("Action: ");

        switch (action) {
            case 1:
                orderService.confirmOrder(id);
                break;
            case 2:
                orderService.processOrder(id);
                break;
            case 3:
                orderService.shipOrder(id);
                break;
            case 4:
                orderService.deliverOrder(id);
                break;
            default:
                System.out.println("Invalid action!");
        }
        System.out.println();
    }

    private void applyDiscount() {
        Long id = readLong("Order ID: ");
        System.out.println("1. Percentage");
        System.out.println("2. Fixed Amount");
        int type = readInt("Discount type: ");

        if (type == 1) {
            int percentage = readInt("Discount percentage: ");
            orderService.applyDiscount(id, percentage);
        } else {
            BigDecimal amount = readBigDecimal("Discount amount: ");
            orderService.applyDiscount(id, amount);
        }
        System.out.println();
    }

    private void cancelOrder() {
        Long id = readLong("Order ID to cancel: ");
        orderService.cancelOrder(id);
        System.out.println();
    }

    private void displayStatistics() {
        FormatUtil.printHeader("SYSTEM STATISTICS");
        System.out.println("Total Products: " + productService.getTotalProducts());
        System.out.println("Total Users: " + userService.getTotalUsers());
        System.out.println("Total Orders: " + orderService.getTotalOrders());
        System.out.println("Total Revenue: " + FormatUtil.formatCurrency(orderService.calculateTotalRevenue()));
        FormatUtil.printSeparator();
        System.out.println();
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Invalid input. " + prompt);
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private long readLong(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextLong()) {
            scanner.nextLine();
            System.out.print("Invalid input. " + prompt);
        }
        long value = scanner.nextLong();
        scanner.nextLine();
        return value;
    }

    private double readDouble(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            scanner.nextLine();
            System.out.print("Invalid input. " + prompt);
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }

    private BigDecimal readBigDecimal(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextBigDecimal()) {
            scanner.nextLine();
            System.out.print("Invalid input. " + prompt);
        }
        BigDecimal value = scanner.nextBigDecimal();
        scanner.nextLine();
        return value;
    }

    public static void main(String[] args) {
        ECommerceApplication app = new ECommerceApplication();
        app.start();
    }
}
