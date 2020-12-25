package aba;

import jdk.nashorn.internal.objects.annotations.Getter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

class User
{
    String userName;
    int age;

    public User() {
    }

    public User(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}

public class AtomicReferenceDemo
{
    public static void main(String[] args)
    {
        //AtomicInteger
        User z3 = new User("z3",22);

        User li4 = new User("li4",25);

        AtomicReference<User> atomicReference = new AtomicReference<User>();
        atomicReference.set(z3);

        System.out.println(atomicReference.compareAndSet(z3,li4) + "\t" + atomicReference.get().toString());

        System.out.println(atomicReference.compareAndSet(z3,li4) + "\t" + atomicReference.get().toString());

        //atomicReference.set(li4);
    }
}
