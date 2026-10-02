package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_minus_overflowTooBig {

    @Test
    public void test_minus_overflowTooBig() {
        TaiInstant instantAtUpperBound = TaiInstant.ofTaiSeconds(Long.MAX_VALUE, 999999999);
        Duration durationThatWouldMovePastUpperBound = Duration.ofSeconds(-1, 999999999);

        assertThrows(
                ArithmeticException.class,
                () -> instantAtUpperBound.minus(durationThatWouldMovePastUpperBound));
    }
}
