package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_factory_parse_CharSequence {

    private static final int MIN_SECONDS = -1000;
    private static final int MAX_SECONDS_EXCLUSIVE = 1000;
    private static final int FIRST_NANO_VALUE = 900_000_000;
    private static final int MAX_NANO_VALUE_EXCLUSIVE = 990_000_000;
    private static final int NANO_INCREMENT = 10_000_000;

    @Test
    public void factory_parse_CharSequence() {
        for (int seconds = MIN_SECONDS; seconds < MAX_SECONDS_EXCLUSIVE; seconds++) {
            for (int nanos = FIRST_NANO_VALUE; nanos < MAX_NANO_VALUE_EXCLUSIVE; nanos += NANO_INCREMENT) {
                String text = seconds + "." + nanos + "s(TAI)";

                TaiInstant parsed = TaiInstant.parse(text);

                assertEquals(seconds, parsed.getTaiSeconds());
                assertEquals(nanos, parsed.getNano());
            }
        }
    }
}
