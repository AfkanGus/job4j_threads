package ru.job4j.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 10. Многопоточность в Stream API [#504930].
 * распараллеливания Stream - работа,методы.
 */
public class ParallelStreamExample {
    /*все операции выполняются последовательно в главной нити Main*/
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        /*reduce - редукция, перемножает эл.сп.*/
        Optional<Integer> multiplication = list.stream()
                .reduce((left, right) -> left * right);
        /*если список не пусть будет напечатал значение Optional*/
        System.out.println(multiplication);

        Stream<Integer> stream = list.parallelStream();
        System.out.println(stream.isParallel());
        Optional<Integer> multiplication1 = list.stream()
                .reduce((left, right) -> left * right);
        System.out.println(multiplication1.get());
    }
}
