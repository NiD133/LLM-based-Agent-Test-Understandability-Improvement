package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextIntLowerGreaterUpper extends AbstractLangTest {

    /**
     * Supplies the three {@link RandomUtils} flavours so each test case runs against
     * the secure, secure-strong and insecure singletons.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * {@code randomInt(startInclusive, endExclusive)} must reject a range whose lower bound
     * is greater than its upper bound. Here the lower bound (2) exceeds the upper bound (1),
     * so an {@link IllegalArgumentException} is expected.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextIntLowerGreaterUpper(final RandomUtils ru) {
        final int lowerBoundGreaterThanUpper = 2;
        final int upperBound = 1;
        assertIllegalArgumentException(() -> ru.randomInt(lowerBoundGreaterThanUpper, upperBound));
    }
}
