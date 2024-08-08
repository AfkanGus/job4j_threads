package ru.job4j;

/**
 * 1. Синхронизация общих ресурсов. [#1096].
 * ОДновременно с объектом может работать тольлка одна нить.
 * Если две нити пробуют выполнить один и тот же синхронизировааный метод.
 * то одна из нитей блокируется, до тех пор пока первая нить не закончит
 * работу с эти методом
 */
public class CounSynch { /*не явный манитор, когда манитором будет сам объект класса - CountSynch*/
    private int valueS;

    /* Монитор будет сам класс CounSynch */
    public static synchronized void lockOfClass() {
        synchronized (CounSynch.class) {

        }
    }

    /* Монитор - это объект CounSynch */
    public void lockOfInstance() {
        synchronized (this) {
        }
    }

    /*Пример не явного указания манитора
     *При заходе нити в syncronized метод класса, другая нить зайдет в метод после того как первая нить его освободит*/
    public synchronized void increment() {
        valueS++; /*коод внитру метода - критическая секция - обл.памят. где может работать 1 нить*/
        /* Монитор - это объект CounSynch */
    }


    /* Монитор будет сам класс CounSynch */
    public static synchronized int getValueS() {
        /*JVM исп.механиз маниторов для регулирования эксклюзивного доступа*/
        return getValueS();
    }
}
