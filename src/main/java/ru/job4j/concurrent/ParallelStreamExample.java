package ru.job4j.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 10. Многопоточность в Stream API [#504930].
 * распараллеливания Stream - работа,методы.
 */
public class ParallelStreamExample {
    /*все операции выполняются  sequention потоке в главной нити Main*/
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        /*reduce - редукция, перемножает эл.сп.*/
        Optional<Integer> multiplication = list.stream()
                .reduce((left, right) -> left * right);
        /*если список не пусть будет напечатал значение Optional*/
        System.out.println(multiplication);

        /*Операции выполняются в parallel потоке*/
        Stream<Integer> stream = list.parallelStream();
        System.out.println(stream.isParallel());
        Optional<Integer> multiplication1 = list.stream()
                .reduce((left, right) -> left * right);
        System.out.println(multiplication1.get());

        /*применям метод к паралельному потоку, вернется сам поток*/
        IntStream parallel = IntStream.range(1, 100).parallel();
        System.out.println(parallel.isParallel());
        /*вернется новый паралельный поток*/
        IntStream sequential = parallel.sequential();
        System.out.println(sequential.isParallel());
        /*в многопоточной среде нет гарантии сохранения
         порядка следования элементов
         * это неустранимыый побочный эффект метода peek()*/
        list.stream().parallel().peek(System.out::println).toList();
        /*forEach. При параллельной обработке потока он так же
         будет выдавать искаженный порядок элементов*/
        list.stream().parallel().forEach(System.out::println);
        /*сохранения порядка следования элементов можно
         воспользоваться методов forEachOrdered():*/
        list.stream().parallel().forEachOrdered(System.out::println);

    }

}
