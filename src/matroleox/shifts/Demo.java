package matroleox.shifts;

import java.util.Calendar;

public class Demo {
    private static RegularShiftsHelper rsHelper;
    private static Calendar cal;
    private static ShiftsTable st;
    public static void main(String[] args) {
        // st = new SanbandaoTable();
        st = new LiangbandaoTable();
        rsHelper = new RegularShiftsHelper(st);

        cal = Calendar.getInstance();
        // cal.add(Calendar.DATE, -1);
        

        // printShift(st.getClassName(0));
        // printShift(st.getClassName(1));
        // printShift(st.getClassName(2));
        // printShift(st.getClassName(3));

        // printClassName(st.getShift(0));
        // printClassName(st.getShift(1));
        // printClassName(st.getShift(2));
        // printClassName(st.getShift(3));
        // printClassName(st.getShift(4));
        // printClassName(st.getShift(5));
        // printClassName(st.getShift(6));
        // printClassName(st.getShift(7));
        
        cal.set(2019, 4, 21, 1, 30); //晚上1点30分 也就是昨天的夜班
        System.out.println("这个时间节点是：" + st.getShift(cal).toFullText());
        System.out.println("当前上班的是：" + rsHelper.getClassName(cal).toCjkIdeographic()); //以小时为节点计算方法
    }

    public static void printShift(ClassName className) {
        System.out.println("今天" + className.toCjkIdeographic() + "的班次：" + rsHelper.getShift(cal, className).toFullText());
    }

    public static void printClassName(Shift shift) {
        System.out.println("今天" + shift.toFullText() + "是：" + rsHelper.getClassName(cal, shift).toCjkIdeographic());
    }
}