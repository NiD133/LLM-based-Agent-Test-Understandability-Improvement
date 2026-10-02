package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Verifies how {@link RandomStringGenerator.Builder#selectFrom(char...)} interacts with
 * the {@code accumulate} flag when chained several times and finished with calls that
 * contribute no characters ({@code selectFrom(null)} and {@code selectFrom()}).
 *
 * <p>Behavior under test:</p>
 * <ul>
 *   <li><b>accumulate == false:</b> every {@code selectFrom} call replaces the previously
 *       selected characters. The final two calls supply no characters, so the character set
 *       ends up empty and the generator falls back to the full code-point range. The produced
 *       characters are therefore (effectively) outside {@code "abcde"}.</li>
 *   <li><b>accumulate == true:</b> the {@code selectFrom} calls add to one another. The trailing
 *       null/empty calls add nothing, so the character set remains {@code {a, b, c, d, e}} and
 *       every produced character is one of those letters.</li>
 * </ul>
 */
public class RandomStringGeneratorTest_testSelectFromCharVarargs3 {

    /** The only characters that may appear when {@code accumulate} is enabled. */
    private static final String ALLOWED_WHEN_ACCUMULATING = "abcde";

    /** Number of code points to generate per check. */
    private static final int GENERATED_LENGTH = 10;

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void testSelectFromCharVarargs3(final boolean accumulate) {
        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .setAccumulate(accumulate)
                .selectFrom('a', 'b', 'c', 'd', 'e')
                .selectFrom('a', 'b', 'c', 'd')
                .selectFrom('a', 'b', 'c')
                .selectFrom('a', 'b')
                .selectFrom(null)
                .selectFrom()
                .get();

        final String randomText = generator.generate(GENERATED_LENGTH);

        // When accumulating, every character must come from "abcde"; otherwise none should.
        for (final char c : randomText.toCharArray()) {
            final boolean isAllowedLetter = ALLOWED_WHEN_ACCUMULATING.indexOf(c) != -1;
            assertEquals(accumulate, isAllowedLetter);
        }
    }
}
