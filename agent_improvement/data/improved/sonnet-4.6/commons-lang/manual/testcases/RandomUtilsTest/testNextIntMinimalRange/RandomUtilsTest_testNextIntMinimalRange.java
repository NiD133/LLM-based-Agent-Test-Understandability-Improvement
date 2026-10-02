package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextIntMinimalRange extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * When startInclusive equals endExclusive, the range contains exactly one
     * possible value, so randomInt must return that value deterministically.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextIntMinimalRange(final RandomUtils ru) {
        assertEquals(42, ru.randomInt(42, 42));
    }
}
