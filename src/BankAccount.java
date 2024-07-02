public class BankAccount {
    //Реализуйте класс BankAccount с методами 1)deposit, 2)withdraw и 3)getBalance, поддерживающими многопоточное взаимодействие.

    private Integer accountBalance;

    public BankAccount(Integer initialBalance) {
        this.accountBalance = initialBalance;
    }

    public synchronized void deposit(Integer depositAmount) {

        accountBalance = accountBalance + depositAmount;

    }

    public synchronized boolean withdraw(Integer withdrawAmount) {
        if(accountBalance < withdrawAmount){
            return false;
        }
        accountBalance = accountBalance - withdrawAmount;
        return true;
    }

    public synchronized Integer getAccountBalance() {
        return accountBalance;
    }
}
