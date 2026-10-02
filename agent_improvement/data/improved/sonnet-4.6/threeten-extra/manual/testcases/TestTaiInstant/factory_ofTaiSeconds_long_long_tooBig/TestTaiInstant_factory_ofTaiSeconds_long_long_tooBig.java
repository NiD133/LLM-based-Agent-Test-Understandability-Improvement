package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_factory_ofTaiSeconds_long_long_tooBig {

    // 1_000_000_000 nanos = 1 full second; adding it to Long.MAX_VALUE seconds overflows long arithmetic
    private static final long ONE_SECOND_IN_NANOS = 1_000_000_000L;

    @Test
    public void factory_ofTaiSeconds_long_long_tooBig() {
        assertThrows(ArithmeticException.class,
                () -> TaiInstant.ofTaiSeconds(Long.MAX_VALUE, ONE_SECOND_IN_NANOS));
    }
}
