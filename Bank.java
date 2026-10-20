import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Customer> customers;

    public Bank() {
        customers = new HashMap<>();
    }

    public void addCustomer(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
    }

    public Customer login(String customerId, String password) {
        if (customers.containsKey(customerId)) {
            Customer customer = customers.get(customerId);
            if (customer.authenticate(password)) {
                return customer;
            }
        }
        return null;
    }

    public boolean customerExists(String customerId) {
        return customers.containsKey(customerId);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

    public boolean transferMoney(Customer sender, String targetAccNumber, double amount) {
        Customer receiver = null;
        for (Customer c : customers.values()) {
            if (c.getAccount().getAccountNumber().equals(targetAccNumber)) {
                receiver = c;
                break;
            }
        }

        if (receiver == null) {
            System.out.println("❌ Alici hesap numarasi bulunamadi!");
            return false;
        }

        if (sender.getAccount().getAccountNumber().equals(targetAccNumber)) {
            System.out.println("❌ Kendi hesabiniza havale yapamazsiniz!");
            return false;
        }

        if (sender.getAccount().withdraw(amount)) {
            receiver.getAccount().deposit(amount);
            sender.getAccount().addTransaction(new Transaction("Giden Havale -> " + targetAccNumber, amount));
            receiver.getAccount().addTransaction(new Transaction("Gelen Havale <- " + sender.getAccount().getAccountNumber(), amount));
            System.out.println("✅ " + amount + " TL tutarindaki havale basariyla gerceklesti!");
            return true;
        }
        return false;
    }
}