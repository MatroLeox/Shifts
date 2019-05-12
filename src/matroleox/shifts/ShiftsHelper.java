package matroleox.shifts;

import java.util.Calendar;

public interface ShiftsHelper {
    void setShiftsTable(ShiftsTable shiftsTable);
    ShiftsTable getShiftsTable();

    Shift getShift(Calendar date, ClassName className);
    ClassName getClassName(Calendar date, Shift shift);
    ClassName getClassName(Calendar date);
}