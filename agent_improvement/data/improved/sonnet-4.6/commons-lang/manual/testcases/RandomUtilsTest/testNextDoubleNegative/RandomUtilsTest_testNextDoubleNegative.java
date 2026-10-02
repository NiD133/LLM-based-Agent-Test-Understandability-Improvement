package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("RandomUtils - randomDouble with negative start bound")
public class RandomUtilsTest_testNextDoubleNegative extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    @DisplayName("randomDouble(-1, 1) throws IllegalArgumentException because startInclusive must be non-negative")
    void testNextDoubleNegative(final RandomUtils ru) {
        assertIllegalArgumentException(() -> ru.randomDouble(-1, 1));
    }
}
