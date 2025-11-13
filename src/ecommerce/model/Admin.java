package ecommerce.model;

import java.util.Arrays;
import java.util.List;

public class Admin extends User {
    private String department;
    private List<String> permissions;
    private String employeeCode;

    public Admin() {
        super();
    }

    public Admin(Long id, String name, String email, String password, String department, String employeeCode) {
        super(id, name, email, password);
        this.department = department;
        this.employeeCode = employeeCode;
        this.permissions = Arrays.asList("CREATE", "READ", "UPDATE", "DELETE");
    }

    @Override
    public String getUserType() {
        return "ADMIN";
    }

    @Override
    public void displayInfo() {
        System.out.println("=== Admin Information ===");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Department: " + department);
        System.out.println("Employee Code: " + employeeCode);
        System.out.println("Permissions: " + String.join(", ", permissions));
        System.out.println("Status: " + (isActive() ? "Active" : "Inactive"));
    }

    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", department='" + department + '\'' +
                ", employeeCode='" + employeeCode + '\'' +
                '}';
    }
}
