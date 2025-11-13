package ecommerce.util;

import ecommerce.model.Product;
import ecommerce.model.User;
import java.math.BigDecimal;

public class ValidationUtil {

    public static void validateNotNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void validateNotEmpty(String str, String message) {
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void validatePositive(Number number, String message) {
        if (number == null || number.doubleValue() <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void validateEmail(String email) {
        validateNotEmpty(email, "Email cannot be empty");
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
    }

    public static void validateUser(User user) {
        validateNotNull(user, "User cannot be null");
        validateNotEmpty(user.getName(), "User name cannot be empty");
        validateEmail(user.getEmail());
        validateNotEmpty(user.getPassword(), "Password cannot be empty");
        
        if (user.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
    }

    public static void validateProduct(Product product) {
        validateNotNull(product, "Product cannot be null");
        validateNotEmpty(product.getName(), "Product name cannot be empty");
        validateNotEmpty(product.getCategory(), "Product category cannot be empty");
        validateNotNull(product.getPrice(), "Product price cannot be null");
        
        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }
    }

    public static void validateCPF(String cpf) {
        validateNotEmpty(cpf, "CPF cannot be empty");
        String cleanCPF = cpf.replaceAll("[^0-9]", "");
        
        if (cleanCPF.length() != 11) {
            throw new IllegalArgumentException("CPF must have 11 digits");
        }
    }

    public static void validatePhone(String phone) {
        validateNotEmpty(phone, "Phone cannot be empty");
        String cleanPhone = phone.replaceAll("[^0-9]", "");
        
        if (cleanPhone.length() < 10 || cleanPhone.length() > 11) {
            throw new IllegalArgumentException("Invalid phone number");
        }
    }
}
