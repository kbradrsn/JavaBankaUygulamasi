public class Customer {
    private String customerId;
    private String name;
    private String password;
    private Account account;

    public Customer(String customerId, String name, String password, Account account) {
        this.customerId = customerId;
        this.name = name;
        this.password = password;
        this.account = account;
    }

    public boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    // Getter Metotları
    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Account getAccount() {
        return account;
    }
}