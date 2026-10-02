package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextLongMinimalRange extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * When the range has a single possible value, that value is returned.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextLongMinimalRange(final RandomUtils randomUtils) {
        assertEquals(42L, randomUtils.randomLong(42L, 42L));
    }
}
