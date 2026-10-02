package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link RandomUtils#randomBytes(int)} rejects a negative count.
 */
public class RandomUtilsTest_testNextBytesNegative extends AbstractLangTest {

    /**
     * Supplies every flavour of {@link RandomUtils} so the test runs once per
     * instance: secure, secure-strong and insecure.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextBytesNegative(final RandomUtils randomUtils) {
        // A negative byte count is invalid and must raise IllegalArgumentException.
        assertIllegalArgumentException(() -> randomUtils.randomBytes(-1));
    }
}
