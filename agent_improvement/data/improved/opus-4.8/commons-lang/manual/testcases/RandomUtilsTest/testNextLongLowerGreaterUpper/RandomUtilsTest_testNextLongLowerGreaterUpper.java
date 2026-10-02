package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link RandomUtils#randomLong(long, long)} rejects a range whose
 * lower bound is greater than its upper bound.
 */
public class RandomUtilsTest_testNextLongLowerGreaterUpper extends AbstractLangTest {

    /**
     * Provides each flavor of {@link RandomUtils} so the behavior is verified
     * for the secure, secure-strong, and insecure instances alike.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextLongLowerGreaterUpper(final RandomUtils randomUtils) {
        // Lower bound (2) is greater than the upper bound (1), which is invalid.
        final long invalidLowerBound = 2;
        final long invalidUpperBound = 1;

        assertIllegalArgumentException(() -> randomUtils.randomLong(invalidLowerBound, invalidUpperBound));
    }
}
