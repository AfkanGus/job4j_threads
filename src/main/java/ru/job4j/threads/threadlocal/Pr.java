package ru.job4j.threads.threadlocal;

public class Pr {
    public String name;
    public int age;

    public Pr(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /* создаем два объекта этого класса и присваиваем их одной и той же переменной*/
    public static void main(String[] args) {
        Pr person1 = new Pr("John", 30);
        Pr person2 = new Pr("Mary", 25);
        /*теперь sharedPerson ссылается на тот же объект, что и person1*/
        Pr sharedPerson = person1;
        sharedPerson.name = "Jane";
        /*выведет "Jane", потому что person1 и sharedPerson
        ссылаются на один и тот же объект*/
        System.out.println(person1.name);
    }
}
