package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_factory_ofTaiSeconds_long_long_tooBig {

    @Test
    public void factory_ofTaiSeconds_long_long_tooBig() {
        assertThrows(
                ArithmeticException.class,
                () -> TaiInstant.ofTaiSeconds(Long.MAX_VALUE, 1000000000));
    }
}
