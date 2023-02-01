public class TestProgram extends SunnyMilkFuzzer {
    int called = 0;
    public void FuzzOne(String s) {
        if (called % 2 == 0) {
            System.out.println("It's even.");
        } else {
            System.out.println("It's odd.");
        }
        ++called;
    }

    public static void main(String[] args) {
        new TestProgram().Loop();
    }
}
