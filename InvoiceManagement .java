package ra.presentation;

import ra.entity.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class InvoiceManagement {
    private static Product[] arrProd = new Product[100];
    private static int prodCount = 0;
    private static Invoice[] arrInvoice = new Invoice[100];
    private static int invCount = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n============== QUẢN LÝ HÓA ĐƠN ==============");
            System.out.println("1. Quản lý sản phẩm");
            System.out.println("2. Quản lý hóa đơn");
            System.out.println("3. Báo cáo doanh thu");
            System.out.println("4. Thoát");
            System.out.println("=============================================");
            System.out.print("Lựa chọn của bạn: ");
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) { choice = 0; }

            switch (choice) {
                case 1: menuProduct(); break;
                case 2: menuInvoice(); break;
                case 3: menuRevenue(); break;
                case 4:
                    System.out.println("Thoát chương trình.");
                    System.exit(0);
                default: System.err.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    public static void menuProduct() {
        boolean isExit = false;
        while (!isExit) {
            System.out.println("\n----------- QUẢN LÝ SẢN PHẨM -----------");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Hiển thị danh sách sản phẩm");
            System.out.println("3. Cập nhật thông tin sản phẩm");
            System.out.println("4. Xóa sản phẩm (nếu chưa có trong hóa đơn)");
            System.out.println("5. Tìm kiếm sản phẩm theo tên");
            System.out.println("6. Quay lại");
            System.out.print("Lựa chọn: ");
            int choice = 0;
            try { choice = Integer.parseInt(scanner.nextLine()); } catch (Exception e) {}

            switch (choice) {
                case 1:
                    if (prodCount < 100) {
                        Product p = new Product();
                        p.inputData(scanner, arrProd, prodCount);
                        arrProd[prodCount++] = p;
                        System.out.println("Thêm thành công!");
                    } else System.err.println("Danh sách đầy!");
                    break;
                case 2:
                    if(prodCount==0) System.out.println("Danh sách trống");
                    for(int i=0; i<prodCount; i++) arrProd[i].displayData();
                    break;
                case 3:
                    System.out.print("Nhập mã sản phẩm cần sửa: ");
                    String editId = scanner.nextLine();
                    int editIdx = -1;
                    for(int i=0; i<prodCount; i++) {
                        if(arrProd[i].getProductId().equals(editId)) { editIdx=i; break; }
                    }
                    if(editIdx != -1) {
                        System.out.println("Nhập tên mới: "); arrProd[editIdx].setProductName(scanner.nextLine());
                        System.out.println("Nhập giá mới: "); arrProd[editIdx].setPrice(Double.parseDouble(scanner.nextLine()));
                        System.out.println("Cập nhật thành công!");
                    } else System.err.println("Không tìm thấy!");
                    break;
                case 4:
                    System.out.print("Nhập mã sản phẩm cần xóa: ");
                    String delId = scanner.nextLine();
                    boolean inUse = false;
                    for(int i=0; i<invCount; i++) {
                        Invoice inv = arrInvoice[i];
                        for(int j=0; j<inv.getDetailCount(); j++) {
                            if(inv.getInvoiceDetails()[j].getProduct().getProductId().equals(delId)) {
                                inUse = true; break;
                            }
                        }
                        if(inUse) break;
                    }
                    if(inUse) {
                        System.err.println("Sản phẩm đã có trong hóa đơn, không thể xóa!");
                    } else {
                        int delIdx = -1;
                        for(int i=0; i<prodCount; i++) {
                            if(arrProd[i].getProductId().equals(delId)) { delIdx = i; break; }
                        }
                        if(delIdx != -1) {
                            for(int i=delIdx; i<prodCount-1; i++) arrProd[i] = arrProd[i+1];
                            arrProd[prodCount-1] = null;
                            prodCount--;
                            System.out.println("Xóa thành công!");
                        } else System.err.println("Không tìm thấy!");
                    }
                    break;
                case 5:
                    System.out.print("Nhập tên tìm kiếm: ");
                    String sName = scanner.nextLine();
                    for(int i=0; i<prodCount; i++) {
                        if(arrProd[i].getProductName().toLowerCase().contains(sName.toLowerCase()))
                            arrProd[i].displayData();
                    }
                    break;
                case 6: isExit = true; break;
                default: System.err.println("Sai lựa chọn!");
            }
        }
    }

    public static void menuInvoice() {
        boolean isExit = false;
        while (!isExit) {
            System.out.println("\n----------- QUẢN LÝ HÓA ĐƠN -----------");
            System.out.println("1. Thêm hóa đơn");
            System.out.println("2. Hiển thị danh sách hóa đơn");
            System.out.println("3. Cập nhật thông tin hóa đơn (KH, Ngày)");
            System.out.println("4. Xóa hóa đơn");
            System.out.println("5. Tìm hóa đơn theo mã");
            System.out.println("6. Tìm hóa đơn theo tên khách hàng");
            System.out.println("7. Quay lại");
            System.out.print("Lựa chọn: ");
            int choice = 0;
            try { choice = Integer.parseInt(scanner.nextLine()); } catch (Exception e) {}

            switch (choice) {
                case 1:
                    if(prodCount == 0) {
                        System.err.println("Chưa có sản phẩm nào để bán!");
                        break;
                    }
                    if (invCount < 100) {
                        Invoice inv = new Invoice();
                        inv.inputData(scanner, arrInvoice, invCount, arrProd, prodCount);
                        arrInvoice[invCount++] = inv;
                        System.out.println("Tạo hóa đơn thành công!");
                    }
                    break;
                case 2:
                    if(invCount==0) System.out.println("Chưa có hóa đơn nào.");
                    for(int i=0; i<invCount; i++) arrInvoice[i].displayData();
                    break;
                case 3:
                    System.out.print("Nhập mã hóa đơn cần sửa: ");
                    String editId = scanner.nextLine();
                    Invoice editInv = null;
                    for(int i=0; i<invCount; i++) {
                        if(arrInvoice[i].getInvoiceId().equals(editId)) { editInv = arrInvoice[i]; break; }
                    }
                    if(editInv != null) {
                        System.out.print("Tên khách hàng mới: "); editInv.setCustomerName(scanner.nextLine());
  
                        System.out.println("Cập nhật thành công!");
                    } else System.err.println("Không tìm thấy!");
                    break;
                case 4:
                    System.out.print("Nhập mã hóa đơn xóa: ");
                    String delId = scanner.nextLine();
                    int delIdx = -1;
                    for(int i=0; i<invCount; i++) {
                        if(arrInvoice[i].getInvoiceId().equals(delId)) { delIdx = i; break; }
                    }
                    if(delIdx != -1) {
                        for(int i=delIdx; i<invCount-1; i++) arrInvoice[i] = arrInvoice[i+1];
                        arrInvoice[invCount-1] = null;
                        invCount--;
                        System.out.println("Xóa thành công!");
                    } else System.err.println("Không tìm thấy!");
                    break;
                case 5:
                    System.out.print("Nhập mã hóa đơn: ");
                    String sId = scanner.nextLine();
                    for(int i=0; i<invCount; i++) {
                        if(arrInvoice[i].getInvoiceId().equals(sId)) arrInvoice[i].displayData();
                    }
                    break;
                case 6:
                    System.out.print("Nhập tên khách hàng: ");
                    String sCust = scanner.nextLine();
                    for(int i=0; i<invCount; i++) {
                        if(arrInvoice[i].getCustomerName().toLowerCase().contains(sCust.toLowerCase()))
                            arrInvoice[i].displayData();
                    }
                    break;
                case 7: isExit = true; break;
                default: System.err.println("Sai lựa chọn!");
            }
        }
    }


    public static void menuRevenue() {
        boolean isExit = false;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        while (!isExit) {
            System.out.println("\n----------- QUẢN LÝ DOANH THU -----------");
            System.out.println("1. Tính tổng doanh thu tất cả hóa đơn");
            System.out.println("2. Tìm hóa đơn có giá trị lớn nhất");
            System.out.println("3. Thống kê số hóa đơn theo khoảng ngày");
            System.out.println("4. Thống kê tổng doanh thu theo khoảng ngày");
            System.out.println("5. Quay lại");
            System.out.print("Lựa chọn: ");
            int choice = 0;
            try { choice = Integer.parseInt(scanner.nextLine()); } catch (Exception e) {}

            switch (choice) {
                case 1:
                    double total = 0;
                    for(int i=0; i<invCount; i++) total += arrInvoice[i].getTotalAmount();
                    System.out.printf("Tổng doanh thu toàn bộ: %,.0f\n", total);
                    break;
                case 2:
                    if(invCount == 0) { System.out.println("Chưa có hóa đơn."); break; }
                    Invoice maxInv = arrInvoice[0];
                    for(int i=1; i<invCount; i++) {
                        if(arrInvoice[i].getTotalAmount() > maxInv.getTotalAmount()) maxInv = arrInvoice[i];
                    }
                    System.out.println("Hóa đơn giá trị lớn nhất:");
                    maxInv.displayData();
                    break;
                case 3:
                case 4:
                    try {
                        System.out.print("Từ ngày (dd/MM/yyyy): ");
                        Date from = sdf.parse(scanner.nextLine());
                        System.out.print("Đến ngày (dd/MM/yyyy): ");
                        Date to = sdf.parse(scanner.nextLine());
                        
                        int count = 0;
                        double sum = 0;
                        for(int i=0; i<invCount; i++) {
                            Date iDate = arrInvoice[i].getInvoiceDate();
                            if((iDate.after(from) || iDate.equals(from)) && (iDate.before(to) || iDate.equals(to))) {
                                count++;
                                sum += arrInvoice[i].getTotalAmount();
                            }
                        }
                        if(choice == 3) System.out.println("Số lượng hóa đơn trong khoảng: " + count);
                        else System.out.printf("Tổng doanh thu trong khoảng: %,.0f\n", sum);

                    } catch (Exception e) {
                        System.err.println("Ngày tháng không hợp lệ!");
                    }
                    break;
                case 5: isExit = true; break;
                default: System.err.println("Sai lựa chọn!");
            }
        }
    }
}
