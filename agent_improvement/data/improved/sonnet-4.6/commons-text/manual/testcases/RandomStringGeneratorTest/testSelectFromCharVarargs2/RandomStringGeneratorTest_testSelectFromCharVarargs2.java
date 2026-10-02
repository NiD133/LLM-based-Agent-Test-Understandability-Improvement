package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class RandomStringGeneratorTest_testSelectFromCharVarargs2 {

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void testSelectFromCharVarargs2(final boolean accumulate) {
        // The allowed character set is 'a'-'e'. When accumulate=false, only the
        // final selectFrom call is used; when accumulate=true, all calls are merged.
        // Either way, every generated character must be within "abcde".
        final String allowedChars = "abcde";

        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .setAccumulate(accumulate)
                .selectFrom()
                .selectFrom((char[]) null)
                .selectFrom('a', 'b')
                .selectFrom('a', 'b', 'c')
                .selectFrom('a', 'b', 'c', 'd')
                .selectFrom('a', 'b', 'c', 'd', 'e')
                .get();

        final String randomText = generator.generate(10);

        for (final char c : randomText.toCharArray()) {
            assertTrue(allowedChars.indexOf(c) != -1,
                    "Generated character '" + c + "' is not in the allowed set: " + allowedChars);
        }
    }
}
