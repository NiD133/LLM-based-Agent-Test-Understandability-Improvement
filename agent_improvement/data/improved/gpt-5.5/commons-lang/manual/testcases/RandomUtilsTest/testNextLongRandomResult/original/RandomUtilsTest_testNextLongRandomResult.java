package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextLongRandomResult extends AbstractLangTest {

    /**
     * For comparing doubles and floats
     */
    private static final double DELTA = 1e-5;

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Tests next long range, random result.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextLongRandomResult(final RandomUtils ru) {
        final long result = ru.randomLong();
        assertTrue(result >= 0L);
        assertTrue(result < Long.MAX_VALUE);
    }
}
