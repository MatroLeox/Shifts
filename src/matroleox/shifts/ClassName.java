package matroleox.shifts;

public class ClassName {
    int index;
    String cjkIdeographic;
    String heavenlyStem;

    public ClassName(int index, String cjkIdeographic, String heavenlyStem) {
        this.index = index;
        this.cjkIdeographic = cjkIdeographic;
        this.heavenlyStem = heavenlyStem;
    }

    public int getIndex() {
        return index;
    }

    public String toCjkIdeographic() {
        return cjkIdeographic;
    }

    public String toHeavenlyStem() {
        return heavenlyStem;
    }
}