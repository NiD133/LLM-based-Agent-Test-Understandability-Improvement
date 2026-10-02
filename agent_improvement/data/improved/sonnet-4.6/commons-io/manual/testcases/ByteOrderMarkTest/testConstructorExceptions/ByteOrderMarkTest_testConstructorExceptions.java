package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_testConstructorExceptions {

    @Test
    void testConstructor_nullCharsetName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ByteOrderMark(null, 1, 2, 3));
    }

    @Test
    void testConstructor_emptyCharsetName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new ByteOrderMark("", 1, 2, 3));
    }

    @Test
    void testConstructor_nullBytesArray_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ByteOrderMark("a", (int[]) null));
    }

    @Test
    void testConstructor_emptyBytesArray_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new ByteOrderMark("b"));
    }
}
