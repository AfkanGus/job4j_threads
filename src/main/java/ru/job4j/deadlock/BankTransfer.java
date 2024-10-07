package ru.job4j.deadlock;

/**
 * 7. Deadlock [#504910 #516263].
 */
class Account {
    private final int id;
    private int balance;

    public Account(int id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void debit(int amount) {
        balance -= amount;
    }

    public void credit(int amount) {
        balance += amount;
    }
}

class NotEnoughFundsException extends Exception {
    public NotEnoughFundsException(String message) {
        super(message);
    }
}

/**
 * - Deadlock, вызванный динамическим порядком блокировок (блокировка порядка синхронизации).
 */
public class BankTransfer {

    public void moneyTransfer(Account from, Account to, int amount) throws NotEnoughFundsException {
        /* блокировка может произойти, если две нити параллельно вызовут метод moneyTransfer.
         В этом случае метод зависит от расположения передаваемых ему аргументов. */
        Account first = from;
        Account second = to;

        /*Гарантируем фиксированный порядок захвата блокировок
         * Блокировки захватываются в порядке возрастания идентификаторов счетов,
         * что предотвращает взаимную блокировку.*/
        if (from.getId() > to.getId()) {
            first = to;
            second = from;
        }
        synchronized (first) {
            synchronized (second) {
                /*Проверяем, достаточно ли средств для перевода*/
                if (from.getBalance() < amount) {
                    throw new NotEnoughFundsException("Недостаточно средств для перевода");
                } else {
                    from.debit(amount);  /*Списание со счета*/
                    to.credit(amount);   /*Зачисление на счет*/
                    System.out.println("Перевод выполнен успешно: " + amount);
                }
            }
        }
    }

    public static void main(String[] args) {
        /*Создаются два аккаунта с балансами 1000 и 500 единиц.*/
        Account account1 = new Account(1, 1000);
        Account account2 = new Account(2, 500);

        BankTransfer bankTransfer = new BankTransfer();
        /*Два потока пытаются одновременно выполнить переводы средств между этими аккаунтами.*/
        Thread thread1 = new Thread(() -> {
            try {
                bankTransfer.moneyTransfer(account1, account2, 300);
            } catch (NotEnoughFundsException e) {
                System.out.println(e.getMessage());
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                bankTransfer.moneyTransfer(account2, account1, 200);
            } catch (NotEnoughFundsException e) {
                System.out.println(e.getMessage());
            }
        });
        /*Оба потока выполняются параллельно, и порядок захвата блокировок
        предотвращает возможную взаимную блокировку (deadlock).*/
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/**
 * Пример выполнения:
 * Один поток пытается перевести 300 единиц с account1 на account2.
 * Другой поток пытается перевести 200 единиц с account2 на account1.
 * Фиксированный порядок захвата блокировок гарантирует,
 * что deadlock не произойдет, и оба потока успешно завершат выполнение.
 */