package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_factory_ofTaiSeconds_long_long_nanosNegativeAdjusted {

    // When nanoAdjustment is negative, ofTaiSeconds normalizes it by borrowing from the
    // seconds field so that the stored nano is always in [0, 999_999_999].
    // ofTaiSeconds(2, -1) → seconds = 2 + floor(-1 / 1e9) = 2 - 1 = 1
    //                        nano   = floorMod(-1, 1e9)   = 999_999_999
    @Test
    public void factory_ofTaiSeconds_long_long_nanosNegativeAdjusted() {
        TaiInstant test = TaiInstant.ofTaiSeconds(2L, -1);
        assertEquals(1, test.getTaiSeconds());
        assertEquals(999999999, test.getNano());
    }
}
