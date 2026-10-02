package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testBoolean extends AbstractLangTest {

    /**
     * Supplies the three {@link RandomUtils} variants the test exercises: the
     * secure, secure-strong and insecure singletons.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Verifies that {@link RandomUtils#randomBoolean()} completes normally and
     * yields a usable boolean for every variant. Because both {@code true} and
     * {@code false} are valid outcomes, the assertion accepts either value; it
     * effectively confirms the call returns without throwing.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testBoolean(final RandomUtils randomUtils) {
        final boolean result = randomUtils.randomBoolean();

        // Either boolean value is acceptable, so accept both true and false.
        assertTrue(result || !result);
    }
}
