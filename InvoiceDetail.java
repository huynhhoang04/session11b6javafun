package ra.entity;

import java.util.Scanner;

public class InvoiceDetail {
    private Product product;
    private int quantity;
    private double subTotal;

    public InvoiceDetail() {
    }

    public InvoiceDetail(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.subTotal = product.getPrice() * quantity;
    }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        if(this.product != null) {
            this.subTotal = this.product.getPrice() * quantity;
        }
    }
    public double getSubTotal() { return subTotal; }

    public void inputData(Scanner scanner, Product[] arrProd, int prodIndex) {
        // Hiển thị danh sách sản phẩm để chọn
        System.out.println("--- Danh sách sản phẩm khả dụng ---");
        for (int i = 0; i < prodIndex; i++) {
            if (arrProd[i].getStatus() == ProductStatus.AVAILABLE) {
                System.out.printf("%d. %s - %s (Giá: %,.0f)\n", 
                    (i+1), arrProd[i].getProductId(), arrProd[i].getProductName(), arrProd[i].getPrice());
            }
        }
        
        // Chọn sản phẩm
        while (true) {
            System.out.print("Nhập mã sản phẩm muốn mua: ");
            String pId = scanner.nextLine();
            boolean found = false;
            for (int i = 0; i < prodIndex; i++) {
                if (arrProd[i].getProductId().equals(pId)) {
                    if (arrProd[i].getStatus() == ProductStatus.AVAILABLE) {
                        this.product = arrProd[i];
                        found = true;
                    } else {
                        System.err.println("Sản phẩm này không khả dụng!");
                        found = true; // Tìm thấy nhưng không bán
                    }
                    break;
                }
            }
            if (found && this.product != null) break;
            if (!found) System.err.println("Không tìm thấy mã sản phẩm!");
        }

        // Nhập số lượng
        while (true) {
            System.out.print("Nhập số lượng mua (>0): ");
            try {
                int qty = Integer.parseInt(scanner.nextLine());
                if (qty > 0) {
                    this.quantity = qty;
                    this.subTotal = this.product.getPrice() * this.quantity;
                    break;
                }
                System.err.println("Số lượng phải > 0");
            } catch (Exception e) {
                System.err.println("Nhập số nguyên!");
            }
        }
    }

    public void displayData() {
        System.out.printf("\tSP: %-15s | SL: %-3d | Thành tiền: %,.0f\n", 
            product.getProductName(), quantity, subTotal);
    }
}
