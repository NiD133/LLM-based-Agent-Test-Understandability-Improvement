package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringSubstitutor}'s offset/length variants of {@code replace}
 * reject out-of-range arguments by throwing {@link StringIndexOutOfBoundsException}.
 */
public class StringSubstitutorTest_testReplaceThrowsStringIndexOutOfBoundsException {

    @Test
    void testReplaceThrowsStringIndexOutOfBoundsException() {
        final StringSubstitutor sub = new StringSubstitutor();
        final char[] emptyCharArray = {};

        // replace(char[], offset, length): length runs past the (empty) array.
        assertThrows(StringIndexOutOfBoundsException.class, () -> sub.replace(emptyCharArray, 0, 1));
        // replace(char[], offset, length): offset is beyond the (empty) array.
        assertThrows(StringIndexOutOfBoundsException.class, () -> sub.replace(emptyCharArray, 1, 0));

        // replace(String, offset, length): offset is beyond the (empty) string.
        assertThrows(StringIndexOutOfBoundsException.class, () -> sub.replace("", 1, 1));
        // replace(String, offset, length): length runs past the (empty) string.
        assertThrows(StringIndexOutOfBoundsException.class, () -> sub.replace("", 0, 1));
    }
}
