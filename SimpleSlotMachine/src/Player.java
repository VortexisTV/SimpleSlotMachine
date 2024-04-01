public class Player {
    private int balance;

    public Player(int initialBalance) {
        this.balance = initialBalance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int newBalance) {
        this.balance = newBalance;
    }

    public void deductBalance(int amount) {
        this.balance -= amount;
    }

    public void addBalance(int amount) {
        this.balance += amount;
    }
}