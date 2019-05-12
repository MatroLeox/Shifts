package matroleox.shifts;

import java.util.Calendar;

public class LiangbandaoTable implements ShiftsTable {
    
    private Shift[] shiftGroup = {
        new Shift(0, "白班", "白", "8-20"),
        new Shift(1, "夜班", "夜", "20-8"),
        new Shift(2, "休息1", "休", ""),
        new Shift(3, "休息2", "休", "")
    };

    private ClassName[] classNames = {
        new ClassName(0, "一班", "甲班"),
        new ClassName(1, "二班", "乙班"),
        new ClassName(2, "三班", "丙班"),
        new ClassName(3, "四班", "丁班")
    };

    private final int BAIBAN_BEGIN_IN_MINUTE = 7 * 60 + 30;
    private final int YIEBAN_BEGIN_IN_MINUTE = 19 * 60 + 30;

    @Override
    public int getRepetition() {
        return 0;
    }

    @Override
    public int getDelta() {
        return 2;
    }

    @Override
    public int getCycle() {
        return shiftGroup.length;
    }

    @Override
    public int getAntedateInMillis() {
        return -((7 * 60 + 30) * 60 * 1000);
    }

    @Override
    public Shift getShift(Calendar date) {
        int timeInMinute = date.get(Calendar.HOUR_OF_DAY) * 60 + date.get(Calendar.MINUTE);
        return shiftGroup[timeInMinute >= BAIBAN_BEGIN_IN_MINUTE && timeInMinute < YIEBAN_BEGIN_IN_MINUTE ? 0 : 1];
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