package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class RandomStringGeneratorTest_testSelectFromCharVarargs3 {

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void testSelectFromCharVarargs3(final boolean accumulate) {
        // When accumulate=true, each selectFrom() call adds to the running character set.
        // The union of all calls below is {'a','b','c','d','e'}, so every generated char
        // must be found in "abcde".
        //
        // When accumulate=false (default), each selectFrom() call resets the set.
        // The last two calls are selectFrom(null) and selectFrom() — both clear the set to
        // empty — so the generator falls back to the full Unicode range. Characters from
        // "abcde" are therefore effectively never produced (probability ~5 / 1_114_112).
        final String allowedChars = "abcde";

        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .setAccumulate(accumulate)
                .selectFrom('a', 'b', 'c', 'd', 'e')
                .selectFrom('a', 'b', 'c', 'd')
                .selectFrom('a', 'b', 'c')
                .selectFrom('a', 'b')
                .selectFrom(null)
                .selectFrom()
                .get();

        final String randomText = generator.generate(10);

        for (final char c : randomText.toCharArray()) {
            // accumulate=true  → char must be in allowedChars
            // accumulate=false → char must NOT be in allowedChars (full-range fallback)
            assertEquals(accumulate, allowedChars.indexOf(c) != -1);
        }
    }
}
