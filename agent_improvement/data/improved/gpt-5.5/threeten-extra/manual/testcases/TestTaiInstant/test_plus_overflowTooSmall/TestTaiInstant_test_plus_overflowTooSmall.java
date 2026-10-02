package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_plus_overflowTooSmall {

    @Test
    public void test_plus_overflowTooSmall() {
        TaiInstant instantAtMinimumSecond = TaiInstant.ofTaiSeconds(Long.MIN_VALUE, 0);
        Duration durationThatBorrowsOneSecond = Duration.ofSeconds(-1, 999999999);

        assertThrows(
                ArithmeticException.class,
                () -> instantAtMinimumSecond.plus(durationThatBorrowsOneSecond));
    }
}
