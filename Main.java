package set;

import collection.model.Student;
import set.collections.MyTreeSet;

/**
 * The Main program tests the functionality of MyTreeSet.
 *
 * @author Martin Mirzoyan
 * @version 1.0
 * @since 2020-02-06
 */

public class Main {
    public static void initializeStudents(MyTreeSet<Student> set) {
        Student st1 = new Student("Martin", "Mirzoyan", 19);
        Student st2 = new Student("Jahan","Semo",22);
        Student st3 = new Student("Aram", "Barseghian", 18);
        Student st4 = new Student("Margar", "Matevosyan", 29);
        Student st5 = new Student("Zaven", "Sahakian", 81);
        Student st6 = new Student("Anna", "Tovmasyan", 27);
        Student st7 = new Student("Louise", "Gulbenkian", 22);
        Student st8 = new Student("Aram","Javadian", 25);

        set.add(st1);
        set.add(st2);
        set.add(st3);
        set.add(st4);
        set.add(st5);
        set.add(st6);
        set.add(st7);
        set.add(st8);
    }

    public static void main(String[] args) {
        MyTreeSet<Student> set = new MyTreeSet<>();

        //initializing
        initializeStudents(set);

        /*
        Students are compared by their surnames as defined in previous task.
         */
        System.out.printf("\nInitial set:\n%s\n", set);

        //adding object
        Student st1 = new Student("Zareh","Karayan", 25);
        set.add(st1);
        System.out.printf("\nSet after adding new student:\n%s\n", set);

        //adding already added object
        set.add(st1);
        System.out.printf("\nSet after adding existing student:\n%s\n", set);

        //removing object
        set.remove(st1);
        System.out.printf("\nSet after removing student:\n%s\n", set);

        //removing object which is not in set
        Student st2 = new Student("Sinan","Voskanian", 25);
        //set.remove(st2); //throws exception

        //check if set contains object
        System.out.printf("\nIs student %s in set: %b", st2.getFirstName(), set.contains(st2));

        //get size of set
        System.out.printf("\nSize of set: %d", set.getSize());
    }
}
