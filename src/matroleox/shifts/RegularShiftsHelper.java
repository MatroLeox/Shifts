package matroleox.shifts;

import java.util.Calendar;

public class RegularShiftsHelper implements ShiftsHelper {
    private ShiftsTable shiftsTable;

    public RegularShiftsHelper(ShiftsTable shiftsTable) {
        setShiftsTable(shiftsTable);
    }

    @Override
    public void setShiftsTable(ShiftsTable shiftsTable) {
        this.shiftsTable = shiftsTable;
    }

    @Override
    public ShiftsTable getShiftsTable() {
        return shiftsTable;
    }

    protected int getShiftInInt(Calendar date, ClassName className) {
        return (getShiftForClassOne(date) + getOffset(className)) % shiftsTable.getCycle();
    }

    private int getOffset(ClassName className) {
        return (shiftsTable.getRepetition() + 1) * className.getIndex(); //(classNumber - 1)
    }

    private int getShiftForClassOne(Calendar date) {
        long totalDays = getTotalDays(date);
        final int DAY_IN_MILLIS = 24 * 60 * 60 * 1000;
        long amend = (totalDays / DAY_IN_MILLIS) + shiftsTable.getDelta();
        return (int) (amend % shiftsTable.getCycle());
    }

    private long getTotalDays(Calendar date) {
        long timeInMillis = date.getTimeInMillis();
        int zoneOffset = date.get(Calendar.ZONE_OFFSET);
        return timeInMillis + zoneOffset;
    }

    @Override
    public Shift getShift(Calendar date, ClassName className) {
        int index = getShiftInInt(date, className);
        return shiftsTable.getShift(index);
    }

    protected int getClassNameInInt(Calendar date, Shift shift) {
        int shiftForClassOne = getShiftForClassOne(date);
        int offset = shift.getIndex() - shiftForClassOne;
        if (offset < 0) {
            offset = shift.getIndex() + shiftsTable.getCycle() - shiftForClassOne;
        }
        int repetition = shiftsTable.getRepetition() + 1;
        if (offset % repetition != 0) return -1;
        return (offset / (shiftsTable.getRepetition() + 1));

    }

    @Override
    public ClassName getClassName(Calendar date, Shift shift) {
        int index = getClassNameInInt(date, shift);
        return shiftsTable.getClassName(index);
    }

    @Override
    public ClassName getClassName(Calendar date) {
        Shift shift = shiftsTable.getShift(date);
        date = (Calendar) date.clone();
        date.add(Calendar.MILLISECOND, shiftsTable.getAntedateInMillis());
        return getClassName(date, shift);
    }
}