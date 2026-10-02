package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_plus_overflowTooBig {

    @Test
    public void test_plus_overflowTooBig() {
        TaiInstant maxInstant = TaiInstant.ofTaiSeconds(Long.MAX_VALUE, 999999999);

        assertThrows(
                ArithmeticException.class,
                () -> maxInstant.plus(Duration.ofSeconds(0, 1)));
    }
}
