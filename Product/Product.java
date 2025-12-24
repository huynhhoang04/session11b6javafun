package ra.entity;

import java.util.Scanner;

public class Product {
    private String productId;
    private String productName;
    private double price;
    private ProductStatus status;

    public Product() {
    }

    public Product(String productId, String productName, double price, ProductStatus status) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.status = status;
    }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public ProductStatus getStatus() { return status; }
    public void setStatus(ProductStatus status) { this.status = status; }

    public void inputData(Scanner scanner, Product[] arrProd, int index) {
        while (true) {
            System.out.print("Nhập mã sản phẩm (C/S/A + 3 ký tự, VD: C001): ");
            String inputId = scanner.nextLine();
            if (inputId.matches("^[CSA].{3}$")) {
                boolean isExist = false;
                for (int i = 0; i < index; i++) {
                    if (arrProd[i].getProductId().equals(inputId)) {
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    this.productId = inputId;
                    break;
                } else {
                    System.err.println("Mã sản phẩm đã tồn tại!");
                }
            } else {
                System.err.println("Mã sản phẩm sai định dạng (4 ký tự, bắt đầu bằng C, S hoặc A)!");
            }
        }

        while (true) {
            System.out.print("Nhập tên sản phẩm (10-50 ký tự): ");
            String inputName = scanner.nextLine();
            if (inputName.length() >= 10 && inputName.length() <= 50) {
                boolean isExist = false;
                for (int i = 0; i < index; i++) {
                    if (arrProd[i].getProductName().equals(inputName)) {
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    this.productName = inputName;
                    break;
                } else {
                    System.err.println("Tên sản phẩm đã tồn tại!");
                }
            } else {
                System.err.println("Tên sản phẩm phải từ 10 đến 50 ký tự!");
            }
        }

        while (true) {
            System.out.print("Nhập giá bán (>0): ");
            try {
                double inputPrice = Double.parseDouble(scanner.nextLine());
                if (inputPrice > 0) {
                    this.price = inputPrice;
                    break;
                }
                System.err.println("Giá phải lớn hơn 0!");
            } catch (NumberFormatException e) {
                System.err.println("Giá phải là số!");
            }
        }

        while (true) {
            System.out.println("Chọn trạng thái: 1. AVAILABLE, 2. OUT_OF_STOCK, 3. STOP_SELLING");
            System.out.print("Lựa chọn: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: this.status = ProductStatus.AVAILABLE; break;
                    case 2: this.status = ProductStatus.OUT_OF_STOCK; break;
                    case 3: this.status = ProductStatus.STOP_SELLING; break;
                    default: System.err.println("Không hợp lệ!"); continue;
                }
                break;
            } catch (Exception e) {
                System.err.println("Vui lòng nhập số!");
            }
        }
    }

    public void displayData() {
        System.out.printf("ID: %-5s | Name: %-20s | Price: %,.0f | Status: %s\n",
                productId, productName, price, status);
    }
}
