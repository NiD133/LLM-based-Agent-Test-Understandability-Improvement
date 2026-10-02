package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextLongMinimalRange extends AbstractLangTest {

    /**
     * Supplies the three {@link RandomUtils} flavours so the same assertion is
     * exercised against the secure, secure-strong and insecure instances.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * When the start and end of the range are identical, {@code randomLong} must
     * return that single value without any randomness involved.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextLongMinimalRange(final RandomUtils randomUtils) {
        final long onlyPossibleValue = 42L;

        final long actual = randomUtils.randomLong(onlyPossibleValue, onlyPossibleValue);

        assertEquals(onlyPossibleValue, actual);
    }
}
