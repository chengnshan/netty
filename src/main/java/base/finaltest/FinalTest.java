package base.finaltest;

import java.util.Random;

/**
 * Created by ASUS on 2018/5/14.
 */
public class FinalTest {
    //编译期常量，必须要进行初始化，且不可更改
    private final String final_01 = "chenssy";
    //构造器常量，在实例化一个对象时被初始化
    private final String final_02;

    private static Random random = new Random();
    //使用随机数来进行初始化
    private final int final_03 = random.nextInt(50);

    ////final指向引用数据类型
    public final Person final_04 = new Person("chen_ssy");

    FinalTest(String final_02) {
        this.final_02 = final_02;
    }

    public String toString() {
        return "final_01 = " + final_01 + "   final_02 = " + final_02 + "   final_03 = " + final_03 +
                "   final_04 = " + final_04.getName();
    }

    public static void main(String[] args) {
        System.out.println("------------第一次创建对象------------");
        FinalTest final1 = new FinalTest("cm");
        System.out.println(final1);
        System.out.println("------------第二次创建对象------------");
        FinalTest final2 = new FinalTest("zj");
        System.out.println(final2);
        System.out.println("------------修改引用对象--------------");
        final2.final_04.setName("chenssy");
        System.out.println(final2);
    }
}

class Person {
    private String name;

    Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}