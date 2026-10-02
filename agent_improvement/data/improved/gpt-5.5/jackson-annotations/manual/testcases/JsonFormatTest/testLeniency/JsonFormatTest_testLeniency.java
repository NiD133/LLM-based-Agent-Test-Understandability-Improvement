package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testLeniency extends AnnotationTestUtil {

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    public void testLeniency() {
        JsonFormat.Value empty = JsonFormat.Value.empty();
        assertFalse(empty.hasLenient());
        assertFalse(empty.isLenient());
        assertNull(empty.getLenient());

        JsonFormat.Value lenient = empty.withLenient(Boolean.TRUE);
        assertTrue(lenient.hasLenient());
        assertTrue(lenient.isLenient());
        assertEquals(Boolean.TRUE, lenient.getLenient());
        assertTrue(lenient.equals(lenient));
        assertFalse(empty.equals(lenient));
        assertFalse(lenient.equals(empty));

        // Reapplying the same setting should reuse the existing value.
        assertSame(lenient, lenient.withLenient(Boolean.TRUE));

        JsonFormat.Value strict = lenient.withLenient(Boolean.FALSE);
        assertTrue(strict.hasLenient());
        assertFalse(strict.isLenient());
        assertEquals(Boolean.FALSE, strict.getLenient());
        assertTrue(strict.equals(strict));
        assertFalse(empty.equals(strict));
        assertFalse(strict.equals(empty));
        assertFalse(lenient.equals(strict));
        assertFalse(strict.equals(lenient));

        JsonFormat.Value unspecified = lenient.withLenient(null);
        assertFalse(unspecified.hasLenient());
        assertFalse(unspecified.isLenient());
        assertNull(unspecified.getLenient());
        assertTrue(empty.equals(unspecified));
        assertTrue(unspecified.equals(empty));
        assertFalse(lenient.equals(unspecified));
        assertFalse(unspecified.equals(lenient));
    }
}
