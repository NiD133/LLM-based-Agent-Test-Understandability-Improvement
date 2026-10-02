package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link RandomUtils#randomLong(long, long)} rejects a negative
 * {@code startInclusive} argument by throwing {@link IllegalArgumentException}.
 */
@DisplayName("RandomUtils – randomLong with negative startInclusive")
public class RandomUtilsTest_testNextLongNegative extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    @DisplayName("randomLong(-1, 1) throws IllegalArgumentException because startInclusive is negative")
    void testNextLongNegative(final RandomUtils ru) {
        assertIllegalArgumentException(() -> ru.randomLong(-1, 1));
    }
}
