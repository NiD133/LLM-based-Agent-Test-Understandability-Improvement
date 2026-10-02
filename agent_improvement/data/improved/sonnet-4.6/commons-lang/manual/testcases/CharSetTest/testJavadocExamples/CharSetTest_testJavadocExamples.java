package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CharSetTest_testJavadocExamples extends AbstractLangTest {

    // "^a-c" means "NOT in range a-c"

    @Test
    void testNegatedRange_excludesCharacterInsideRange() {
        // 'a' is inside the negated range [a-c], so it must not be contained
        assertFalse(CharSet.getInstance("^a-c").contains('a'));
    }

    @Test
    void testNegatedRange_includesCharacterOutsideRange() {
        // 'd' is outside the negated range [a-c], so it must be contained
        assertTrue(CharSet.getInstance("^a-c").contains('d'));
    }

    // "^^a-c" means "NOT '^'" followed by literal range [a-c];
    // only the first '^' acts as negation, giving the set {a,b,c} ∪ NOT('^')

    @Test
    void testDoubleNegation_includesCharacterFromLiteralRange() {
        // 'a' is in the literal [a-c] portion of "^^a-c", so it must be contained
        assertTrue(CharSet.getInstance("^^a-c").contains('a'));
    }

    @Test
    void testDoubleNegation_excludesCaretCharItself() {
        // The second '^' negates the literal caret, so '^' must not be contained
        assertFalse(CharSet.getInstance("^^a-c").contains('^'));
    }

    // "^a-cd-f" means "NOT [a-c]" unioned with literal [d-f]; 'd' is in [d-f]

    @Test
    void testNegatedRangeWithAdditionalRange_includesCharacterFromSecondRange() {
        // 'd' is in the literal [d-f] portion appended after the negated [a-c]
        assertTrue(CharSet.getInstance("^a-cd-f").contains('d'));
    }

    // Placing '^' at the end of the pattern treats it as a literal character

    @Test
    void testCaretAtEndOfPattern_treatedAsLiteralCharacter() {
        // "a-c^" means range [a-c] plus the literal caret character
        assertTrue(CharSet.getInstance("a-c^").contains('^'));
    }

    // Passing '^' as a separate argument also treats it as a literal character

    @Test
    void testCaretAsStandaloneArgument_treatedAsLiteralCharacter() {
        // "^" alone as one argument is a literal caret; "a-c" is a separate range
        assertTrue(CharSet.getInstance("^", "a-c").contains('^'));
    }
}
