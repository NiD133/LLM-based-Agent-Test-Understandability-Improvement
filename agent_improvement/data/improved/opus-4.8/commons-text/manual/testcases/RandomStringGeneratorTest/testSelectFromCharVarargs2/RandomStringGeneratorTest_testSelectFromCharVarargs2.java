package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class RandomStringGeneratorTest_testSelectFromCharVarargs2 {

    /**
     * Verifies that chaining several {@code selectFrom(...)} calls restricts the generated
     * characters to the supplied set, regardless of whether the {@code accumulate} flag is set.
     *
     * <p>The chain below supplies characters drawn only from {@code "abcde"}:</p>
     * <ul>
     *   <li>When {@code accumulate} is {@code false}, each call replaces the previous set, so only
     *       the final {@code selectFrom('a', 'b', 'c', 'd', 'e')} call takes effect.</li>
     *   <li>When {@code accumulate} is {@code true}, all calls combine, but every supplied
     *       character is still one of {@code 'a'..'e'}.</li>
     * </ul>
     *
     * <p>Either way, every generated character must be a member of {@code "abcde"}.</p>
     */
    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    void generatedCharactersComeFromSelectedSet(final boolean accumulate) {
        final String allowedCharacters = "abcde";

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
            assertTrue(allowedCharacters.indexOf(c) != -1,
                    "Generated character '" + c + "' is not in the allowed set \"" + allowedCharacters + "\"");
        }
    }
}
