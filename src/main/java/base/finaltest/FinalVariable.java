package base.finaltest;

/**
 * Created by ASUS on 2018/5/14.
 */
public class FinalVariable {
    public Destionation destionation(String str) {
        class PDestionation implements Destionation {
            private String label;

            private PDestionation(String whereTo) {
                label = whereTo;
            }

            public String readLabel() {
                return label;
            }
        }
        return new PDestionation(str);
    }

    public static void main(String[] args) {
        FinalVariable parcel5 = new FinalVariable();
        Destionation d = parcel5.destionation("chenssy");
        System.out.println(d.readLabel());
    }
}
