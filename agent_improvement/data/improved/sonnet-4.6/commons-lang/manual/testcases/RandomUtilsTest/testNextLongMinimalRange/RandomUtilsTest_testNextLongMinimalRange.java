package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link RandomUtils#randomLong(long, long)} returns the boundary value itself
 * when the start and end of the range are equal (i.e., a zero-width range).
 *
 * <p>When {@code startInclusive == endExclusive}, there is only one possible value that
 * satisfies the contract, so the method must return that exact value deterministically,
 * regardless of which {@link RandomUtils} instance (secure, strong-secure, or insecure)
 * is used.</p>
 */
@DisplayName("RandomUtils – randomLong with minimal (zero-width) range")
public class RandomUtilsTest_testNextLongMinimalRange extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Verifies that a zero-width range [42, 42) forces {@code randomLong} to return
     * exactly 42, because no other value fits within the range.
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("randomProvider")
    void testNextLongMinimalRange(final RandomUtils ru) {
        final long startAndEnd = 42L;
        assertEquals(startAndEnd, ru.randomLong(startAndEnd, startAndEnd),
                "randomLong(n, n) must return n when start equals end");
    }
}
