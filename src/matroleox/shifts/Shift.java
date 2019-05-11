package matroleox.shifts;

public class Shift {
    private int index;
    private String fullText;
    private String shortText;
    private String timeText;

    public Shift(int index, String fullText, String shortText, String timeText) {
        this.index = index;
        this.fullText = fullText;
        this.shortText = fullText;
        this.timeText = timeText;
    }

    public int getIndex() {
        return index;
    }

    public String toFullText() {
        return fullText;
    }
    public String toShortText() {
        return shortText;
    }
    public String toTimeText() {
        return timeText;
    }
}