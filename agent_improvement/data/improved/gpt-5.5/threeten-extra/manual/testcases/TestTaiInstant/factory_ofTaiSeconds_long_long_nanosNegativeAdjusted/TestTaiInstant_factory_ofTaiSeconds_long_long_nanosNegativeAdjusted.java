package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_factory_ofTaiSeconds_long_long_nanosNegativeAdjusted {

    @Test
    public void factory_ofTaiSeconds_long_long_nanosNegativeAdjusted() {
        TaiInstant test = TaiInstant.ofTaiSeconds(2L, -1);

        assertEquals(1, test.getTaiSeconds());
        assertEquals(999999999, test.getNano());
    }
}
