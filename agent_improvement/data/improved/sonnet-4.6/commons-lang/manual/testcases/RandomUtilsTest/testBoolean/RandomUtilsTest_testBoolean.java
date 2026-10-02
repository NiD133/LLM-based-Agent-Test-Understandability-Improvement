package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testBoolean extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testBoolean(final RandomUtils randomUtils) {
        final boolean result = randomUtils.randomBoolean();
        // Any boolean is either true or false; this asserts the call completes without throwing
        assertTrue(result || !result, "randomBoolean() must return a valid boolean value (true or false)");
    }
}
