import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

        int attempts = 0;
        boolean lockAcquired = false;

        while (attempts < 5) {
        try {
            if (transferFrom.lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                if (transferTo.lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                    lockAcquired = true;
                    if (!transferFrom.withdraw(transferAmount)) {
                        return false;
                    }
                    transferTo.deposit(transferAmount);
                    return true;
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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

        attempts++;
        if (attempts == 5) {
            System.out.println("Перевод с " + transferFrom.hashCode() + " на " + transferTo.hashCode() + " размером " + transferAmount + " не удался после 5 попыток.");
        }

        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
        return false;
    }

    public Integer getTotalBalance() {

        return bankAccounts.stream().mapToInt(BankAccount::getAccountBalance).sum();

    }
}
