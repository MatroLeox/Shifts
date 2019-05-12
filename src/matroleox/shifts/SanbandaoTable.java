package matroleox.shifts;

import java.util.Calendar;

public class SanbandaoTable implements ShiftsTable {
    
    private Shift[] shiftGroup = {
        new Shift(0, "晚班1", "晚", "0-8"),
        new Shift(1, "晚班2", "晚", "0-8"),
        new Shift(2, "早班1", "早", "8-16"),
        new Shift(3, "早班2", "早", "8-16"),
        new Shift(4, "中班1", "中", "16-24"),
        new Shift(5, "中班2", "中", "16-24"),
        new Shift(6, "休息1", "休", ""),
        new Shift(7, "休息2", "休", "")
    };

    private ClassName[] classNames = {
        new ClassName(0, "一班", "甲班"),
        new ClassName(1, "二班", "乙班"),
        new ClassName(2, "三班", "丙班"),
        new ClassName(3, "四班", "丁班")
    };

    private final int WANBAN_BEGIN_IN_MINUTE = 23 * 60 + 30;
    private final int ZAOBAN_BEGIN_IN_MINUTE = 7 * 60 + 30;
    private final int ZHONGBAN_BEGIN_IN_MINUTE = 15 * 60 + 30;

    @Override
    public int getRepetition() {
        return 1;
    }

    @Override
    public int getDelta() {
        return 0;
    }

    @Override
    public int getCycle() {
        return shiftGroup.length;
    }

    @Override
    public int getAntedateInMillis() {
        return 30 * 60 * 1000;
    }

    @Override
    public Shift getShift(Calendar date) {
        int timeInMinute = date.get(Calendar.HOUR_OF_DAY) * 60 + date.get(Calendar.MINUTE);
        int index;
        if (timeInMinute >= ZAOBAN_BEGIN_IN_MINUTE && timeInMinute < ZHONGBAN_BEGIN_IN_MINUTE) {
            index = 2;
        } else if (timeInMinute >= ZHONGBAN_BEGIN_IN_MINUTE && timeInMinute < WANBAN_BEGIN_IN_MINUTE) {
            index = 4;
        } else {
            index = 0;
        }
        return shiftGroup[index + getOffset(date)];
    }

    private int getOffset(Calendar date) {
        long timeInMillis = date.getTimeInMillis() + date.get(Calendar.ZONE_OFFSET) + getAntedateInMillis();
        final int DAY_IN_MILLIS = 24 * 60 * 60 * 1000;
        return (int) (timeInMillis / DAY_IN_MILLIS % (getRepetition() + 1));
    }

    @Override
    public Shift getShift(int index) {
        if (index < 0 || index >= shiftGroup.length) {
            return ShiftsTable.NONE_SHIFT;
        }
        return shiftGroup[index];
    }

    @Override
    public int getAmountOfClasses() {
        return classNames.length;
    }

    @Override
    public ClassName getClassName(int index) {
        if (index < 0 || index >= classNames.length) {
            return ShiftsTable.NONE_CLASS_NAME;
        }
        return classNames[index];
    }
}