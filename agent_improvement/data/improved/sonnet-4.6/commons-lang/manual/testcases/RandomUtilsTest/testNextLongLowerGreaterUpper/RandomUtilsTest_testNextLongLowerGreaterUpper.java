package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link RandomUtils#randomLong(long, long)} rejects invocations where
 * {@code startInclusive} is greater than {@code endExclusive} by throwing an
 * {@link IllegalArgumentException}.
 */
public class RandomUtilsTest_testNextLongLowerGreaterUpper extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextLongLowerGreaterUpper(final RandomUtils ru) {
        // lower (2) > upper (1): contract requires IllegalArgumentException
        assertIllegalArgumentException(() -> ru.randomLong(2, 1));
    }
}
