package ecommerce.model;

import java.math.BigDecimal;

public class DigitalProduct extends Product {
    private String downloadUrl;
    private double fileSizeMB;
    private String fileFormat;
    private int downloadLimit;
    private int validityDays;

    public DigitalProduct() {
        super();
    }

    public DigitalProduct(Long id, String name, String description, BigDecimal price, String category,
                         String downloadUrl, double fileSizeMB, String fileFormat, int downloadLimit, int validityDays) {
        super(id, name, description, price, category);
        this.downloadUrl = downloadUrl;
        this.fileSizeMB = fileSizeMB;
        this.fileFormat = fileFormat;
        this.downloadLimit = downloadLimit;
        this.validityDays = validityDays;
    }

    @Override
    public String getProductType() {
        return "DIGITAL";
    }

    @Override
    public void displayProductDetails() {
        System.out.println("=== Digital Product ===");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Description: " + getDescription());
        System.out.println("Price: R$ " + getPrice());
        System.out.println("Category: " + getCategory());
        System.out.println("File Size: " + fileSizeMB + " MB");
        System.out.println("Format: " + fileFormat);
        System.out.println("Download Limit: " + downloadLimit + " times");
        System.out.println("Validity: " + validityDays + " days");
        System.out.println("Shipping: R$ " + calculateShipping());
        System.out.println("Available: " + (isAvailable() ? "Yes" : "No"));
    }

    @Override
    public BigDecimal calculateShipping() {
        return BigDecimal.ZERO;
    }

    public String generateDownloadLink() {
        return downloadUrl + "?token=" + System.currentTimeMillis();
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public double getFileSizeMB() {
        return fileSizeMB;
    }

    public void setFileSizeMB(double fileSizeMB) {
        this.fileSizeMB = fileSizeMB;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public int getDownloadLimit() {
        return downloadLimit;
    }

    public void setDownloadLimit(int downloadLimit) {
        this.downloadLimit = downloadLimit;
    }

    public int getValidityDays() {
        return validityDays;
    }

    public void setValidityDays(int validityDays) {
        this.validityDays = validityDays;
    }

    @Override
    public String toString() {
        return "DigitalProduct{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", format='" + fileFormat + '\'' +
                ", size=" + fileSizeMB + "MB" +
                '}';
    }
}
