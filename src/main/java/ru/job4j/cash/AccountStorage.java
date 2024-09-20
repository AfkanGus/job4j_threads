package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

/**
 * 3. Денежные переводы AccountStorage [#1104].
 */
@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accountHashMap = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accountHashMap.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accountHashMap.replace(account.id(), account) != null;
    }

    public synchronized void delete(int id) {
        accountHashMap.remove(id);

    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accountHashMap.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Account fromIdAccount = accountHashMap.get(fromId);
        Account toIdAccount = accountHashMap.get(toId);
        if (fromIdAccount != null && toIdAccount != null && fromIdAccount.amount() >= amount) {
            Account updatedFromAccount = new Account(fromIdAccount.id(),
                    fromIdAccount.amount() - amount);
            Account updatedToAccount = new Account(toIdAccount.id(), toIdAccount.amount() + amount);
            accountHashMap.put(fromId, updatedFromAccount);
            accountHashMap.put(toId, updatedToAccount);
            return true;
        }
        return false;
    }
}
