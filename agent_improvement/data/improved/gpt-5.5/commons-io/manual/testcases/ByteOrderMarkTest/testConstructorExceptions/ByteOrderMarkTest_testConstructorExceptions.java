package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_testConstructorExceptions {

    @Test
    void testConstructorExceptions() {
        assertThrows(NullPointerException.class, () -> new ByteOrderMark(null, 1, 2, 3));
        assertThrows(IllegalArgumentException.class, () -> new ByteOrderMark("", 1, 2, 3));
        assertThrows(NullPointerException.class, () -> new ByteOrderMark("a", (int[]) null));
        assertThrows(IllegalArgumentException.class, () -> new ByteOrderMark("b"));
    }
}
