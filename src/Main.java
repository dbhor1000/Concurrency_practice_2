public class Main {
    public static void main(String[] args) {

        ConcurrentBank bank = new ConcurrentBank();

        // Создание счетов
        BankAccount account1 = bank.createAccount(1000);
        BankAccount account2 = bank.createAccount(500);
        BankAccount account3 = bank.createAccount(500);
        BankAccount account4 = bank.createAccount(500);

        // Перевод между счетами
        Thread transferThread1 = new Thread(() -> {
            bank.transfer(account1, account2, 1100);
            bank.transfer(account2, account4, 300);
        });
        Thread transferThread2 = new Thread(() -> {
            bank.transfer(account1, account3, 700);
            bank.transfer(account2, account1, 300);
        });

        transferThread1.start();
        transferThread2.start();

        try {
            transferThread1.join();
            transferThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Вывод общего баланса
        System.out.println("Total balance: " + bank.getTotalBalance());
        System.out.println(account1.getAccountBalance());
        System.out.println(account2.getAccountBalance());
        System.out.println(account3.getAccountBalance());
        System.out.println(account4.getAccountBalance());
    }
}
