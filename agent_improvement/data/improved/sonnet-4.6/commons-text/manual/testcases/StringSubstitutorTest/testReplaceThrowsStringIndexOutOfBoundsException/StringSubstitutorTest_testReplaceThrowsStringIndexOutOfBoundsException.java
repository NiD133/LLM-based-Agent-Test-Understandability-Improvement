package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceThrowsStringIndexOutOfBoundsException {

    @Test
    void testReplaceThrowsStringIndexOutOfBoundsException() {
        final StringSubstitutor sub = new StringSubstitutor();
        final char[] emptyCharArray = {};

        // replace(char[], int, int): requested length (1) exceeds the number of chars available from offset 0
        assertThrows(StringIndexOutOfBoundsException.class, () -> sub.replace(emptyCharArray, 0, 1));

        // replace(char[], int, int): offset (1) is past the end of the empty array
        assertThrows(StringIndexOutOfBoundsException.class, () -> sub.replace(emptyCharArray, 1, 0));

        // replace(String, int, int): offset (1) is past the end of the empty string
        assertThrows(StringIndexOutOfBoundsException.class, () -> sub.replace("", 1, 1));

        // replace(String, int, int): requested length (1) exceeds the number of chars available from offset 0
        assertThrows(StringIndexOutOfBoundsException.class, () -> sub.replace("", 0, 1));
    }
}
