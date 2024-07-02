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
        if(transferFrom.getAccountBalance() < transferAmount){
            return false;
        }
        synchronized (transferFrom) {
            synchronized (transferTo){
                transferFrom.withdraw(transferAmount);
                transferTo.deposit(transferAmount);
                return true;
            }
        }
    }

    public Integer getTotalBalance() {

        return bankAccounts.stream().mapToInt(BankAccount::getAccountBalance).sum();

    }
}
