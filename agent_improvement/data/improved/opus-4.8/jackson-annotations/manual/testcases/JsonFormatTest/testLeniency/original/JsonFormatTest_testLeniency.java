package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
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
        // should NOT create now one if no change:
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
        // and finally, can also clear up setting
        JsonFormat.Value dunno = lenient.withLenient(null);
        assertFalse(dunno.hasLenient());
        assertFalse(dunno.isLenient());
        assertNull(dunno.getLenient());
        assertTrue(empty.equals(dunno));
        assertTrue(dunno.equals(empty));
        assertFalse(lenient.equals(dunno));
        assertFalse(dunno.equals(lenient));
    }
}
