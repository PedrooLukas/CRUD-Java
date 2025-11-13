package ecommerce.service;

import ecommerce.model.Customer;
import ecommerce.model.User;
import ecommerce.repository.UserRepository;
import ecommerce.util.ValidationUtil;
import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        ValidationUtil.validateUser(user);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already registered: " + user.getEmail());
        }
        System.out.println("Creating user: " + user.getName());
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        ValidationUtil.validateUser(user);
        if (!userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("User not found with ID: " + user.getId());
        }
        System.out.println("Updating user: " + user.getName());
        return userRepository.update(user);
    }

    public User updateUserEmail(Long id, String newEmail) {
        User user = getUser(id);
        if (userRepository.existsByEmail(newEmail) && !user.getEmail().equals(newEmail)) {
            throw new IllegalArgumentException("Email already in use: " + newEmail);
        }
        user.setEmail(newEmail);
        return userRepository.update(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        System.out.println("Deleting user with ID: " + id);
        userRepository.delete(id);
    }

    public void deactivateUser(Long id) {
        User user = getUser(id);
        user.setActive(false);
        userRepository.update(user);
        System.out.println("User deactivated: " + user.getName());
    }

    public void activateUser(Long id) {
        User user = getUser(id);
        user.setActive(true);
        userRepository.update(user);
        System.out.println("User activated: " + user.getName());
    }

    public boolean authenticateUser(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> user.getPassword().equals(password) && user.isActive())
                .orElse(false);
    }

    public void displayAllUsers() {
        List<User> users = getAllUsers();
        System.out.println("\n=== All Users ===");
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            users.forEach(User::displayInfo);
            System.out.println();
        }
    }

    public void displayCustomers() {
        List<User> users = getAllUsers();
        System.out.println("\n=== All Customers ===");
        users.stream()
                .filter(user -> user instanceof Customer)
                .forEach(User::displayInfo);
        System.out.println();
    }

    public long getTotalUsers() {
        return userRepository.count();
    }
}
