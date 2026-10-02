package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_factory_ofTaiSecondslong_long {

    private static final int NANOS_PER_SECOND = 1000000000;

    @Test
    public void factory_ofTaiSecondslong_long() {
        for (long taiSecond = -2; taiSecond <= 2; taiSecond++) {
            assertPositiveNanoAdjustmentsStayWithinSameSecond(taiSecond);
            assertNegativeNanoAdjustmentsCarryToPreviousSecond(taiSecond);
            assertHighNanoAdjustmentsStayWithinSameSecond(taiSecond);
        }
    }

    private void assertPositiveNanoAdjustmentsStayWithinSameSecond(long taiSecond) {
        for (int nanoAdjustment = 0; nanoAdjustment < 10; nanoAdjustment++) {
            assertTaiInstant(taiSecond, nanoAdjustment, taiSecond, nanoAdjustment);
        }
    }

    private void assertNegativeNanoAdjustmentsCarryToPreviousSecond(long taiSecond) {
        for (int nanoAdjustment = -10; nanoAdjustment < 0; nanoAdjustment++) {
            assertTaiInstant(taiSecond, nanoAdjustment, taiSecond - 1, nanoAdjustment + NANOS_PER_SECOND);
        }
    }

    private void assertHighNanoAdjustmentsStayWithinSameSecond(long taiSecond) {
        for (int nanoAdjustment = 999999990; nanoAdjustment < NANOS_PER_SECOND; nanoAdjustment++) {
            assertTaiInstant(taiSecond, nanoAdjustment, taiSecond, nanoAdjustment);
        }
    }

    private void assertTaiInstant(
            long taiSecond,
            long nanoAdjustment,
            long expectedTaiSecond,
            int expectedNano) {

        TaiInstant instant = TaiInstant.ofTaiSeconds(taiSecond, nanoAdjustment);

        assertEquals(expectedTaiSecond, instant.getTaiSeconds());
        assertEquals(expectedNano, instant.getNano());
    }
}
