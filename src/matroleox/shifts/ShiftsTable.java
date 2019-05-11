package matroleox.shifts;

public interface ShiftsTable {
    public static final Shift NONE_SHIFT = new Shift(-1, "无", "无", "无");
    public static final ClassName NONE_CLASS_NAME = new ClassName(-1, "无", "无");
    int getRepetition();
    int getDelta();
    int getCycle();
    Shift getShift(int index);

    int getAmountOfClasses();
    ClassName getClassName(int index);
}