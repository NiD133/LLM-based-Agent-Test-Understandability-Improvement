package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextFloatNegative extends AbstractLangTest {

    private static final float NEGATIVE_START_INCLUSIVE = -1;
    private static final float POSITIVE_END_EXCLUSIVE = 1;

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextFloatNegative(final RandomUtils randomUtils) {
        assertIllegalArgumentException(
                () -> randomUtils.randomFloat(NEGATIVE_START_INCLUSIVE, POSITIVE_END_EXCLUSIVE));
    }
}
