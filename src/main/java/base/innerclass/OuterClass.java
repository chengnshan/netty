package base.innerclass;

/**
 * Created by ASUS on 2018/5/13.
 */
public class OuterClass {
    private String str;

    public void outerDisplay() {
        System.out.println("outerClass...");
    }

    public String getStr() {
        return str;
    }

    public class InnerClass {
        public void innerDisplay() {
            //使用外围内的属性
            str = "chenssy...";
            System.out.println(str);
            //使用外围内的方法
            outerDisplay();
        }
    }

    /*推荐使用getxxx()来获取成员内部类，尤其是该内部类的构造函数无参数时 */
    public InnerClass getInnerClass() {
        return new InnerClass();
    }

    public static void main(String[] args) {
        OuterClass outer = new OuterClass();
        OuterClass.InnerClass inner = outer.getInnerClass();
        inner.innerDisplay();
        System.out.println(outer.getStr());
    }
}
