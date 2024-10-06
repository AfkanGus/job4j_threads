package ru.job4j.synchronizers;

import java.util.concurrent.Phaser;

/**
 * 6. Синхронизаторы [#504906].
 */
public class PhysicalEducation {
    public static void main(String[] args) {
        /*Создаем Phaser для 3 учеников*/
        Phaser phaser = new Phaser(3);

        /*Создаем несколько потоков (учеников)*/
        /*Начинает с первого упражнения*/
        new Thread(new Student(phaser, "Ученик 1", 0)).start();
        /*Начинает со второго упражнения*/
        new Thread(new Student(phaser, "Ученик 2", 1)).start();
        /*Начинает с первого упражнения*/
        new Thread(new Student(phaser, "Ученик 3", 0)).start();
    }
}

class Student implements Runnable {
    private final Phaser phaser;
    private final String name;
    private final int startPhase;

    public Student(Phaser phaser, String name, int startPhase) {
        this.phaser = phaser;
        this.name = name;
        this.startPhase = startPhase;
    }

    @Override
    public void run() {
        for (int phase = startPhase; phase < 3; phase++) { /*Три фазы*/
            doExercise(phase); /*Выполняем упражнение*/
            /*Уведомляем, что упражнение завершено*/
            if (phase < 2) { /*Если это не последняя фаза*/
                phaser.arriveAndAwaitAdvance(); /*Ждем других*/
            } else {
                phaser.arriveAndDeregister(); /*Уведомляем о завершении*/
            }
        }
    }

    private void doExercise(int phase) {
        System.out.println(name + " выполняет упражнение " + (phase + 1));
        try {
            Thread.sleep((long) (Math.random() * 1000)); /*Симуляция времени выполнения упражнения*/
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
/**
 * Здесь мы создаем объект Phaser, указывая, что у нас есть 3 ученика,
 * которые будут участвовать в упражнениях
 * Создаются три потока (ученика). Первый и третий начинаются
 * с первого упражнения, а второй — со второго.
 * <p>
 * Выполнение упражнений: Каждый поток выполняет свое упражнение
 * в методе doExercise(), где выводится сообщение о текущем упражнении, и имитируется время выполнения с помощью Thread.sleep().
 * <p>
 * Уведомление о завершении:
 * <p>
 * Учащиеся, которые не завершили свои упражнения, вызывают phaser.arriveAndAwaitAdvance(),
 * чтобы уведомить, что они завершили текущую фазу и ждут остальных.
 * Ученик, завершивший последнее упражнение, вызывает phaser.arriveAndDeregister(),
 * чтобы уведомить о своем завершении и снять себя с регистрации.
 * Синхронизация: Phaser будет ждать, пока все зарегистрированные участники не завершат
 * свою фазу, после чего продолжит выполнение следующей фазы.
 * <p>
 * Вывод:
 * Этот пример демонстрирует, как Phaser может быть использован для синхронизации потоков,
 * где каждый поток (ученик) может начинать и завершать свои действия в разное время.
 * Это удобно в ситуациях, когда количество участников и их состояние может меняться
 * на протяжении выполнения программы.
 */
