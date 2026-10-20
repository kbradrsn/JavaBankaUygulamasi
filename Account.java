import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountNumber;
    private double balance;
    private List<Transaction> history;

    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.history = new ArrayList<>();
        if (initialBalance > 0) {
            history.add(new Transaction("Ilk Hesap Bakiye Acilisi", initialBalance));
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            history.add(new Transaction("Para Yatirma", amount));
            System.out.println("✅ " + amount + " TL yatirildi. Guncel Bakiye: " + balance + " TL");
        } else {
            System.out.println("❌ Gecersiz miktar!");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            history.add(new Transaction("Para Cekme", amount));
            System.out.println("✅ " + amount + " TL cekildi. Kalan Bakiye: " + balance + " TL");
            return true;
        } else if (amount > balance) {
            System.out.println("❌ Yetersiz bakiye! Bakiyeniz: " + balance + " TL");
            return false;
        } else {
            System.out.println("❌ Gecersiz miktar!");
            return false;
        }
    }

    public void printStatement() {
        System.out.println("\n--- HESAP OZETI (" + accountNumber + ") ---");
        if (history.isEmpty()) {
            System.out.println("Henuz islem gecmisi yok.");
        } else {
            for (Transaction t : history) {
                System.out.println(t);
            }
        }
        System.out.println("GUNCEL BAKIYE: " + balance + " TL");
    }

    public void addTransaction(Transaction transaction) {
        this.history.add(transaction);
    }

    public String getAccountNumber() { 
        return accountNumber; 
    }
    
    public double getBalance() { 
        return balance; 
    }
}