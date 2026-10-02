package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LineIteratorTest_testConstructor {

    @Test
    void testConstructor() {
        assertThrows(NullPointerException.class, () -> new LineIterator(null));
    }
}
