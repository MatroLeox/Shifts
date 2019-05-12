package matroleox.shifts;

import java.util.Calendar;

public class Demo {
    private static RegularShiftsHelper rsHelper;
    private static Calendar cal;
    private static ShiftsTable st;
    public static void main(String[] args) {
        st = new SanbandaoTable();
        // st = new LiangbandaoTable();
        rsHelper = new RegularShiftsHelper(st);

        cal = Calendar.getInstance();
        // cal.add(Calendar.DATE, -1);
        cal.set(2019, 4, 11, 23, 29);

        printShift(st.getClassName(0));
        printShift(st.getClassName(1));
        printShift(st.getClassName(2));
        printShift(st.getClassName(3));

        printClassName(st.getShift(0));
        printClassName(st.getShift(1));
        printClassName(st.getShift(2));
        printClassName(st.getShift(3));
        printClassName(st.getShift(4));
        printClassName(st.getShift(5));
        printClassName(st.getShift(6));
        printClassName(st.getShift(7));

        // System.out.println(st.getShift(cal).toFullText());
        System.out.println(rsHelper.getClassName(cal).toCjkIdeographic());
    }

    public static void printShift(ClassName className) {
        System.out.println("今天" + className.toCjkIdeographic() + "的班次：" + rsHelper.getShift(cal, className).toFullText());
    }

    public static void printClassName(Shift shift) {
        System.out.println("今天" + shift.toFullText() + "是：" + rsHelper.getClassName(cal, shift).toCjkIdeographic());
    }
}