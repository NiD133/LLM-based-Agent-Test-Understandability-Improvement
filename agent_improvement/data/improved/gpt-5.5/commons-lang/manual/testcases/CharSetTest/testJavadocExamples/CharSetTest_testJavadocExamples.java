package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CharSetTest_testJavadocExamples extends AbstractLangTest {

    @Test
    void testJavadocExamples() {
        // A leading caret negates the following range.
        assertFalse(CharSet.getInstance("^a-c").contains('a'));
        assertTrue(CharSet.getInstance("^a-c").contains('d'));

        // In "^^a-c", only the literal caret is negated.
        assertTrue(CharSet.getInstance("^^a-c").contains('a'));
        assertFalse(CharSet.getInstance("^^a-c").contains('^'));

        // A negated range can be combined with ordinary ranges.
        assertTrue(CharSet.getInstance("^a-cd-f").contains('d'));

        // A caret is literal when it is the last character or a separate set element.
        assertTrue(CharSet.getInstance("a-c^").contains('^'));
        assertTrue(CharSet.getInstance("^", "a-c").contains('^'));
    }
}
