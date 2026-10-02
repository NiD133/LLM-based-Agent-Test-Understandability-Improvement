package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextBytesNegative extends AbstractLangTest {

    static Stream<RandomUtils> randomUtilsProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomUtilsProvider")
    void testNextBytesNegative(final RandomUtils randomUtils) {
        assertIllegalArgumentException(() -> randomUtils.randomBytes(-1));
    }
}
