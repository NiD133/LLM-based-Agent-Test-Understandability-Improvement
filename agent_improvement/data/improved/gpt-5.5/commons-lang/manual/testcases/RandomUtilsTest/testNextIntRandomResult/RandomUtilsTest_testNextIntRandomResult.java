package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextIntRandomResult extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Verifies that each RandomUtils provider returns an int in the documented default range.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextIntRandomResult(final RandomUtils ru) {
        final int randomResult = ru.randomInt();
        assertTrue(randomResult > 0);
        assertTrue(randomResult < Integer.MAX_VALUE);
    }
}
