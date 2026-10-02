package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextDoubleRandomResult extends AbstractLangTest {

    static Stream<RandomUtils> randomUtilsProviders() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomUtilsProviders")
    void testNextDoubleRandomResult(final RandomUtils randomUtils) {
        final double result = randomUtils.randomDouble();

        assertTrue(result >= 0d);
        assertTrue(result < Double.MAX_VALUE);
    }
}
