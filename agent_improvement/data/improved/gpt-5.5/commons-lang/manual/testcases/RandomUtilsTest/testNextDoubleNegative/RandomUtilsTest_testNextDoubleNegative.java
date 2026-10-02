package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextDoubleNegative extends AbstractLangTest {

    static Stream<RandomUtils> randomUtilsInstances() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomUtilsInstances")
    void testNextDoubleNegative(final RandomUtils randomUtils) {
        assertIllegalArgumentException(() -> randomUtils.randomDouble(-1, 1));
    }
}
