package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies the worked examples documented in the Javadoc of
 * {@link CharSet#getInstance(String...)}, focusing on how the negation
 * character ({@code ^}) is interpreted.
 */
public class CharSetTest_testJavadocExamples extends AbstractLangTest {

    @Test
    void testJavadocExamples() {
        // "^a-c" is a negated range: it contains every character EXCEPT 'a', 'b', 'c'.
        assertFalse(CharSet.getInstance("^a-c").contains('a'), "'a' is excluded by the negated range");
        assertTrue(CharSet.getInstance("^a-c").contains('d'), "'d' is outside the negated range, so it is included");

        // "^^a-c": the leading "^^" negates only the literal '^' character,
        // leaving "a-c" as an ordinary (non-negated) range.
        assertTrue(CharSet.getInstance("^^a-c").contains('a'), "'a' is inside the ordinary range a-c");
        assertFalse(CharSet.getInstance("^^a-c").contains('^'), "the literal '^' is the negated character");

        // "^a-cd-f" combines a negated range "^a-c" with an ordinary range "d-f".
        assertTrue(CharSet.getInstance("^a-cd-f").contains('d'), "'d' is inside the ordinary range d-f");

        // A trailing '^' is treated as a literal '^' character.
        assertTrue(CharSet.getInstance("a-c^").contains('^'), "trailing '^' is a literal character");

        // Passing "^" as a separate element also adds a literal '^' character.
        assertTrue(CharSet.getInstance("^", "a-c").contains('^'), "'^' supplied as its own element is a literal character");
    }
}
