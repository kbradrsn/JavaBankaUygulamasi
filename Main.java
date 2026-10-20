import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        FileManager.loadData(bank);

        if (bank.getAllCustomers().isEmpty()) {
            Account acc1 = new Account("TR101", 2500.0);
            Customer cust1 = new Customer("1001", "Ahmet Yilmaz", "1234", acc1);
            bank.addCustomer(cust1);
            FileManager.saveData(bank);
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("====================================");
        System.out.println("     JAVA BANK'A HOS GELDINIZ       ");
        System.out.println("====================================");

        while (true) {
            System.out.println("\n--- ANA GIRIS PANELI ---");
            System.out.println("1. Giris Yap");
            System.out.println("2. Yeni Musteri Kaydi Ol (Kayit Ol)");
            System.out.println("3. Sistemden Cikis");
            System.out.print("Seciminiz (1-3): ");
            int panelChoice = scanner.nextInt();

            if (panelChoice == 1) {
                System.out.print("Musteri Numarasi: ");
                String id = scanner.next();
                System.out.print("Sifre: ");
                String pass = scanner.next();

                Customer currentCustomer = bank.login(id, pass);

                if (currentCustomer != null) {
                    System.out.println("\nGiris Basarili! Hos geldiniz, " + currentCustomer.getName());
                    userMenu(currentCustomer, bank, scanner);
                } else {
                    System.out.println("❌ Hatali Musteri Numarasi veya Sifre!");
                }

            } else if (panelChoice == 2) {
                System.out.println("\n--- YENI MUSTERI KAYIT EKRANI ---");
                System.out.print("Yeni Musteri Numarasi belirleyin (Orn: 1003): ");
                String newId = scanner.next();

                if (bank.customerExists(newId)) {
                    System.out.println("❌ Bu musteri numarasi zaten kullanimda!");
                    continue;
                }

                System.out.print("Adiniz Soyadiniz: ");
                scanner.nextLine();
                String newName = scanner.nextLine();

                System.out.print("Sifreniz: ");
                String newPass = scanner.next();

                System.out.print("Yeni Hesap Numaraniz (Orn: TR103): ");
                String newAccNo = scanner.next();

                System.out.print("Baslangic Yatirim Tutari (TL): ");
                double initialDeposit = scanner.nextDouble();

                Account newAccount = new Account(newAccNo, initialDeposit);
                Customer newCustomer = new Customer(newId, newName, newPass, newAccount);

                bank.addCustomer(newCustomer);
                FileManager.saveData(bank);

                System.out.println("✅ Kaydiniz basariyla olusturuldu!");

            } else if (panelChoice == 3) {
                FileManager.saveData(bank);
                System.out.println("Bizi tercih ettiginiz icin tesekkurler!");
                break;
            } else {
                System.out.println("❌ Gecersiz secim!");
            }
        }
        scanner.close();
    }

    private static void userMenu(Customer customer, Bank bank, Scanner scanner) {
        int choice;
        do {
            System.out.println("\n--- ISLEM MENUSU ---");
            System.out.println("1. Bakiye ve Hesap Ozeti");
            System.out.println("2. Para Yatir");
            System.out.println("3. Para Cek");
            System.out.println("4. Baska Hesaba Havale Yap");
            System.out.println("5. Oturumu Kapat (Ana Menuye Don)");
            System.out.print("Seciminiz: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    customer.getAccount().printStatement();
                    break;
                case 2:
                    System.out.print("Yatirilacak Tutar: ");
                    double dep = scanner.nextDouble();
                    customer.getAccount().deposit(dep);
                    FileManager.saveData(bank);
                    break;
                case 3:
                    System.out.print("Cekilecek Tutar: ");
                    double with = scanner.nextDouble();
                    if(customer.getAccount().withdraw(with)) {
                        FileManager.saveData(bank);
                    }
                    break;
                case 4:
                    System.out.print("Alici Hesap No: ");
                    String targetAcc = scanner.next();
                    System.out.print("Gonderilecek Tutar: ");
                    double amount = scanner.nextDouble();
                    if(bank.transferMoney(customer, targetAcc, amount)) {
                        FileManager.saveData(bank);
                    }
                    break;
                case 5:
                    FileManager.saveData(bank);
                    System.out.println("Oturum kapatildi.");
                    break;
                default:
                    System.out.println("Gecersiz secim!");
            }
        } while (choice != 5);
    }
}