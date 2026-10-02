package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link RandomUtils#randomInt()}, which returns a random int in the
 * range {@code [0, Integer.MAX_VALUE)}.
 */
public class RandomUtilsTest_testNextIntRandomResult extends AbstractLangTest {

    /**
     * Supplies the three {@link RandomUtils} flavours so each test runs once per flavour.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * The result of {@link RandomUtils#randomInt()} should fall strictly inside
     * the documented bounds: greater than 0 and less than {@link Integer#MAX_VALUE}.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextIntRandomResult(final RandomUtils randomUtils) {
        final int randomResult = randomUtils.randomInt();

        assertTrue(randomResult > 0, "result should be greater than 0");
        assertTrue(randomResult < Integer.MAX_VALUE, "result should be less than Integer.MAX_VALUE");
    }
}
