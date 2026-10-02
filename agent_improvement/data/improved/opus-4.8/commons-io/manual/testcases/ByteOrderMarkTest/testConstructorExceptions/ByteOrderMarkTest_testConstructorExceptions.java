package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests the argument validation performed by the {@link ByteOrderMark#ByteOrderMark(String, int...)}
 * constructor.
 */
public class ByteOrderMarkTest_testConstructorExceptions {

    /**
     * Verifies that the constructor rejects each kind of invalid argument:
     * <ul>
     *   <li>a {@code null} charset name,</li>
     *   <li>an empty charset name,</li>
     *   <li>a {@code null} byte array, and</li>
     *   <li>an empty byte array.</li>
     * </ul>
     */
    @Test
    void testConstructorExceptions() {
        // A null charset name is not allowed.
        assertThrows(NullPointerException.class,
                () -> new ByteOrderMark(null, 1, 2, 3));

        // An empty charset name is not allowed.
        assertThrows(IllegalArgumentException.class,
                () -> new ByteOrderMark("", 1, 2, 3));

        // A null byte array is not allowed.
        assertThrows(NullPointerException.class,
                () -> new ByteOrderMark("a", (int[]) null));

        // At least one byte must be supplied.
        assertThrows(IllegalArgumentException.class,
                () -> new ByteOrderMark("b"));
    }
}
