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
}
