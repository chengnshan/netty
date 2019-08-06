package base.innerclass;

/**
 * 通过数组定义来引用类，不会触发此类的初始化
 * Created by ASUS on 2018/5/14.
 */
public class MethodInnerClass {
    public static void main(String[] args) {
        SuperClass[] sc=new SuperClass[5];
    }
}

class SuperClass {
    static {
        System.out.println("SuperClass init!");
    }

    public static int value = 66;
}

class SubClass extends SuperClass {
    static {
        System.out.println("SubClass init!");
    }
}

