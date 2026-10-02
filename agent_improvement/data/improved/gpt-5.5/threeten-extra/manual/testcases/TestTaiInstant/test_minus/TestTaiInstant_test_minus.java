package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestTaiInstant_test_minus {

    private static final long NANOS_PER_SECOND = 1_000_000_000L;

    private static final long[][] BASE_INSTANTS = {
            { -4, 666_666_667 },
            { -3, 0 },
            { -2, 0 },
            { -1, 0 },
            { -1, 666_666_667 },
            { 0, 0 },
            { 0, 333_333_333 },
            { 1, 0 },
            { 2, 0 },
            { 3, 0 },
            { 3, 333_333_333 },
    };

    private static final long[][] DURATIONS_TO_SUBTRACT = {
            { -4, 666_666_667 },
            { -3, 0 },
            { -2, 0 },
            { -1, 0 },
            { -1, 333_333_334 },
            { -1, 666_666_667 },
            { -1, 999_999_999 },
            { 0, 0 },
            { 0, 1 },
            { 0, 333_333_333 },
            { 0, 666_666_666 },
            { 1, 0 },
            { 2, 0 },
            { 3, 0 },
            { 3, 333_333_333 },
    };

    public static Object[][] data_minus() {
        List<Object[]> cases = new ArrayList<>();
        cases.add(new Object[] { Long.MIN_VALUE, 0, Long.MIN_VALUE + 1, 0, -1, 0 });

        for (long[] baseInstant : BASE_INSTANTS) {
            for (long[] duration : DURATIONS_TO_SUBTRACT) {
                cases.add(minusCase(
                        baseInstant[0],
                        (int) baseInstant[1],
                        duration[0],
                        (int) duration[1]));
            }
        }

        cases.add(new Object[] { Long.MAX_VALUE, 0, Long.MAX_VALUE, 0, 0, 0 });
        return cases.toArray(new Object[0][]);
    }

    private static Object[] minusCase(long seconds, int nanos, long minusSeconds, int minusNanos) {
        long secondDifference = seconds - minusSeconds;
        long nanoDifference = (long) nanos - minusNanos;
        long expectedSeconds = secondDifference + Math.floorDiv(nanoDifference, NANOS_PER_SECOND);
        int expectedNanos = (int) Math.floorMod(nanoDifference, NANOS_PER_SECOND);
        return new Object[] { seconds, nanos, minusSeconds, minusNanos, expectedSeconds, expectedNanos };
    }

    @ParameterizedTest
    @MethodSource("data_minus")
    public void test_minus(
            long seconds,
            int nanos,
            long minusSeconds,
            int minusNanos,
            long expectedSeconds,
            int expectedNanoOfSecond) {

        TaiInstant actual = TaiInstant.ofTaiSeconds(seconds, nanos)
                .minus(Duration.ofSeconds(minusSeconds, minusNanos));

        assertEquals(expectedSeconds, actual.getTaiSeconds());
        assertEquals(expectedNanoOfSecond, actual.getNano());
    }
}
