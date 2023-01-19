package fi.donhut.koinly.ftxusdfix.ftx;

import lombok.Getter;

public enum Label {
    SWAP("Swap");

    @Getter
    private final String value;

    Label(final String value) {
        this.value = value;
    }
}
