package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInTakingStringBuilderWithNonNull {

    /**
     * When the variable prefix and suffix are both configured to the literal {@code "b<H"},
     * the input {@code "b<H"} cannot form a complete {@code prefix...suffix} variable reference,
     * so {@link StrSubstitutor#replaceIn(StringBuilder)} performs no substitution and returns
     * {@code false}, leaving the builder unchanged. The configured escape character is also
     * exposed unchanged via {@link StrSubstitutor#getEscapeChar()}.
     */
    @Test
    void testReplaceInTakingStringBuilderWithNonNull() {
        final char escapeChar = '\'';
        final StrLookup<String> systemPropertiesLookup = StrLookup.systemPropertiesLookup();
        final StrSubstitutor substitutor =
                new StrSubstitutor(systemPropertiesLookup, "b<H", "b<H", escapeChar);

        assertEquals(escapeChar, substitutor.getEscapeChar());

        final StringBuilder target = new StringBuilder("b<H");
        assertFalse(substitutor.replaceIn(target));
    }
}
