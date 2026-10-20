import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {
    private static final String DOSYA_ADI = "bank_data.txt";

    public static void saveData(Bank bank) {
        try (FileWriter writer = new FileWriter(DOSYA_ADI)) {
            for (Customer customer : bank.getAllCustomers()) {
                String satir = customer.getCustomerId() + "," +
                               customer.getName() + "," +
                               customer.getPassword() + "," +
                               customer.getAccount().getAccountNumber() + "," +
                               customer.getAccount().getBalance() + "\n";
                writer.write(satir);
            }
        } catch (IOException e) {
            System.out.println("❌ Dosya kaydedilirken hata olustu: " + e.getMessage());
        }
    }

    public static void loadData(Bank bank) {
        File file = new File(DOSYA_ADI);
        if (!file.exists()) {
            return;
        }

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String customerId = parts[0];
                    String name = parts[1];
                    String password = parts[2];
                    String accNumber = parts[3];
                    double balance = Double.parseDouble(parts[4]);

                    Account account = new Account(accNumber, balance);
                    Customer customer = new Customer(customerId, name, password, account);
                    bank.addCustomer(customer);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Dosya okunurken hata olustu: " + e.getMessage());
        }
    }
}