package ecommerce.model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private String cpf;
    private String address;
    private String phone;
    private List<Order> orders;

    public Customer() {
        super();
        this.orders = new ArrayList<>();
    }

    public Customer(Long id, String name, String email, String password, String cpf, String address, String phone) {
        super(id, name, email, password);
        this.cpf = cpf;
        this.address = address;
        this.phone = phone;
        this.orders = new ArrayList<>();
    }

    @Override
    public String getUserType() {
        return "CUSTOMER";
    }

    @Override
    public void displayInfo() {
        System.out.println("=== Customer Information ===");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("CPF: " + cpf);
        System.out.println("Phone: " + phone);
        System.out.println("Address: " + address);
        System.out.println("Total Orders: " + orders.size());
        System.out.println("Status: " + (isActive() ? "Active" : "Inactive"));
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", cpf='" + cpf + '\'' +
                ", phone='" + phone + '\'' +
                ", totalOrders=" + orders.size() +
                '}';
    }
}
