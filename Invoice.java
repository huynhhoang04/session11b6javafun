package ra.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Invoice {
    private String invoiceId;
    private String customerName;
    private Date invoiceDate;
    private InvoiceDetail[] invoiceDetails;
    private int detailCount; 
    private double totalAmount;

    public Invoice() {
        this.invoiceDetails = new InvoiceDetail[100]; 
        this.detailCount = 0;
    }

    public String getInvoiceId() { return invoiceId; }
    public void setInvoiceId(String invoiceId) { this.invoiceId = invoiceId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public Date getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(Date invoiceDate) { this.invoiceDate = invoiceDate; }
    public InvoiceDetail[] getInvoiceDetails() { return invoiceDetails; }
    public int getDetailCount() { return detailCount; }
    public double getTotalAmount() { return totalAmount; }

    public void calculateTotalAmount() {
        double total = 0;
        for (int i = 0; i < detailCount; i++) {
            total += invoiceDetails[i].getSubTotal();
        }
        this.totalAmount = total;
    }

    public void inputData(Scanner scanner, Invoice[] arrInvoice, int invIndex, Product[] arrProd, int prodIndex) {

        while (true) {
            System.out.print("Nhập mã hóa đơn (HDxxxx, 6 ký tự): ");
            String inputId = scanner.nextLine();
            if (inputId.matches("^HD.{4}$")) {
                boolean isExist = false;
                for (int i = 0; i < invIndex; i++) {
                    if (arrInvoice[i].getInvoiceId().equals(inputId)) {
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    this.invoiceId = inputId;
                    break;
                } else System.err.println("Mã hóa đơn đã tồn tại!");
            } else System.err.println("Sai định dạng (Bắt đầu bằng HD và có 6 ký tự)!");
        }

        // 2. Tên khách hàng
        System.out.print("Nhập tên khách hàng: ");
        this.customerName = scanner.nextLine();


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        while (true) {
            System.out.print("Nhập ngày lập (dd/MM/yyyy): ");
            try {
                this.invoiceDate = sdf.parse(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.err.println("Ngày tháng không hợp lệ!");
            }
        }

        
        System.out.println("--- Nhập chi tiết sản phẩm mua ---");
        while (true) {
            System.out.print("Bạn muốn nhập bao nhiêu sản phẩm khác nhau? ");
            try {
                int n = Integer.parseInt(scanner.nextLine());
                if (n > 0) {
                    for (int i = 0; i < n; i++) {
                        System.out.println("Sản phẩm thứ " + (i + 1) + ":");
                        InvoiceDetail detail = new InvoiceDetail();
                        detail.inputData(scanner, arrProd, prodIndex);
                        this.invoiceDetails[detailCount++] = detail;
                    }
                    break;
                }
            } catch (Exception e) {
                System.err.println("Nhập số nguyên dương!");
            }
        }
        calculateTotalAmount();
    }

    public void displayData() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.printf("Hóa đơn: %s | KH: %-15s | Ngày: %s | Tổng tiền: %,.0f\n",
                invoiceId, customerName, sdf.format(invoiceDate), totalAmount);
        for (int i = 0; i < detailCount; i++) {
            invoiceDetails[i].displayData();
        }
        System.out.println("----------------------------------------------------");
    }
}
