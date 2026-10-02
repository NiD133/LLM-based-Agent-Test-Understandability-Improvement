package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.Reader;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link LineIterator#LineIterator(Reader)}.
 */
public class LineIteratorTest_testConstructor {

    /**
     * The constructor rejects a {@code null} reader by throwing a
     * {@link NullPointerException} (see {@code Objects.requireNonNull} in the CUT).
     */
    @Test
    void constructorRejectsNullReader() {
        assertThrows(NullPointerException.class, () -> new LineIterator(null));
    }
}
