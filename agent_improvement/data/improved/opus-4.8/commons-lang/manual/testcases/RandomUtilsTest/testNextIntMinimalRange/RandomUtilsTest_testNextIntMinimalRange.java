package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextIntMinimalRange extends AbstractLangTest {

    /**
     * Provides each RandomUtils flavour (secure, secure-strong and insecure) so
     * every test is exercised against all three random sources.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * When the start and end bounds are equal, {@link RandomUtils#randomInt(int, int)}
     * must return that single value without invoking the underlying random source.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextIntMinimalRange(final RandomUtils ru) {
        final int onlyPossibleValue = 42;
        assertEquals(onlyPossibleValue, ru.randomInt(onlyPossibleValue, onlyPossibleValue));
    }
}
