package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LineIteratorTest_testConstructor {

    @Test
    @DisplayName("Constructor throws NullPointerException when reader is null")
    void testConstructor() {
        assertThrows(NullPointerException.class, () -> new LineIterator(null));
    }
}
