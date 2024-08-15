package ru.job4j.ref;

public class ShareNotSafe {
    public static void main(String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user = User.of("main");
        cache.add(user);
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        user.setName("first");
                    }
                    System.out.println(cache.findById(1).getName());
                }
        );

    }
}
