package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceThrowsStringIndexOutOfBoundsException {

    @Test
    void testReplaceThrowsStringIndexOutOfBoundsException() {
        final StringSubstitutor substitutor = new StringSubstitutor();
        final char[] emptySource = {};

        assertThrows(StringIndexOutOfBoundsException.class, () -> substitutor.replace(emptySource, 0, 1));
        assertThrows(StringIndexOutOfBoundsException.class, () -> substitutor.replace(emptySource, 1, 0));
        assertThrows(StringIndexOutOfBoundsException.class, () -> substitutor.replace("", 1, 1));
        assertThrows(StringIndexOutOfBoundsException.class, () -> substitutor.replace("", 0, 1));
    }
}
