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
     * Verifies that {@link RandomUtils#randomInt()} returns a value strictly between
     * 0 (exclusive) and {@link Integer#MAX_VALUE} (exclusive), for each of the three
     * available RandomUtils instances (secure, secureStrong, insecure).
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextIntRandomResult(final RandomUtils randomUtils) {
        final int randomResult = randomUtils.randomInt();
        assertTrue(randomResult > 0, "randomInt() result must be greater than 0");
        assertTrue(randomResult < Integer.MAX_VALUE, "randomInt() result must be less than Integer.MAX_VALUE");
    }
}
