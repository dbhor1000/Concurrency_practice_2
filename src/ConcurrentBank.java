import java.util.ArrayList;
import java.util.List;

public class ConcurrentBank {
    //Реализуйте класс ConcurrentBank для управления счетами и выполнения переводов между ними.
    // Класс должен предоставлять методы 1)createAccount для создания нового счета и transfer для выполнения переводов между счетами.

    //Реализуйте метод 2)getTotalBalance, который возвращает общий баланс всех счетов в банке.

    List<BankAccount> bankAccounts = new ArrayList<>();

    public BankAccount createAccount(Integer initialDeposit) {
        BankAccount newAccount = new BankAccount(initialDeposit);
        bankAccounts.add(newAccount);

        return newAccount;
    }

    public boolean transfer(BankAccount transferFrom, BankAccount transferTo, Integer transferAmount) {
        if (transferFrom.getAccountBalance() < transferAmount) {
            return false;
        }

        boolean lockAcquired = false;
        try {
            if (transferFrom.lock.tryLock()) {
                if (transferTo.lock.tryLock()) {
                    lockAcquired = true;
                    transferFrom.withdraw(transferAmount);
                    transferTo.deposit(transferAmount);
                    return true;
                }
            }
        } finally {
            if (lockAcquired) {
                transferTo.lock.unlock();
                transferFrom.lock.unlock();
            } else {
                if (transferFrom.lock.isHeldByCurrentThread()) {
                    transferFrom.lock.unlock();
                }
                if (transferTo.lock.isHeldByCurrentThread()) {
                    transferTo.lock.unlock();
                }
            }
        }
        return false;
    }

    public Integer getTotalBalance() {

        return bankAccounts.stream().mapToInt(BankAccount::getAccountBalance).sum();

    }
}
