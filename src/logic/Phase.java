package logic;

public enum Phase {
    PRELIMINARY_ROUND(0),
    GROUP(1),
    KNOCKOUT(2),
    FINAL(3);


    private int value;

    Phase(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void nextPhase() {
        this.value++;
    }
}

