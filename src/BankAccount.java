import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    //Реализуйте класс BankAccount с методами 1)deposit, 2)withdraw и 3)getBalance, поддерживающими многопоточное взаимодействие.

    private Integer accountBalance;
    final ReentrantLock lock = new ReentrantLock();

    public BankAccount(Integer initialBalance) {
        this.accountBalance = initialBalance;
    }

    public void deposit(Integer depositAmount) {
        lock.lock();
        try {
            accountBalance = accountBalance + depositAmount;
        } finally {
            lock.unlock();
        }
    }

    public boolean withdraw(Integer withdrawAmount) {
        lock.lock();
        try {
            if(accountBalance < withdrawAmount){
                return false;
            }
            accountBalance = accountBalance - withdrawAmount;
            return true;
        } finally {
            lock.unlock();
        }
    }

    public Integer getAccountBalance() {
        lock.lock();
        try {
            return accountBalance;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(accountBalance, that.accountBalance) && Objects.equals(lock, that.lock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountBalance, lock);
    }
}
