package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextIntNegative extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    @DisplayName("randomInt throws IllegalArgumentException when startInclusive is negative")
    void testNextIntNegative(final RandomUtils ru) {
        assertIllegalArgumentException(() -> ru.randomInt(-1, 1));
    }
}
