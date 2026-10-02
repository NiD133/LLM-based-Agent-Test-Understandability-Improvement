package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testLeniency extends AnnotationTestUtil {

    @Test
    public void testEmptyValueHasNoLeniencySet() {
        JsonFormat.Value empty = JsonFormat.Value.empty();

        assertFalse(empty.hasLenient());
        assertFalse(empty.isLenient());
        assertNull(empty.getLenient());
    }

    @Test
    public void testWithLenientTrueCreatesLenientValue() {
        JsonFormat.Value lenient = JsonFormat.Value.empty().withLenient(Boolean.TRUE);

        assertTrue(lenient.hasLenient());
        assertTrue(lenient.isLenient());
        assertEquals(Boolean.TRUE, lenient.getLenient());
    }

    @Test
    public void testWithLenientTrueIsIdempotentReturnsSameInstance() {
        JsonFormat.Value lenient = JsonFormat.Value.empty().withLenient(Boolean.TRUE);

        // no new object should be created when the value is already set to TRUE
        assertSame(lenient, lenient.withLenient(Boolean.TRUE));
    }

    @Test
    public void testWithLenientFalseCreatesStrictValue() {
        JsonFormat.Value lenient = JsonFormat.Value.empty().withLenient(Boolean.TRUE);
        JsonFormat.Value strict = lenient.withLenient(Boolean.FALSE);

        assertTrue(strict.hasLenient());
        assertFalse(strict.isLenient());
        assertEquals(Boolean.FALSE, strict.getLenient());
    }

    @Test
    public void testLenientAndEmptyValuesAreNotEqual() {
        JsonFormat.Value empty = JsonFormat.Value.empty();
        JsonFormat.Value lenient = empty.withLenient(Boolean.TRUE);

        assertTrue(lenient.equals(lenient));
        assertFalse(empty.equals(lenient));
        assertFalse(lenient.equals(empty));
    }

    @Test
    public void testStrictAndEmptyValuesAreNotEqual() {
        JsonFormat.Value empty = JsonFormat.Value.empty();
        JsonFormat.Value strict = empty.withLenient(Boolean.FALSE);

        assertTrue(strict.equals(strict));
        assertFalse(empty.equals(strict));
        assertFalse(strict.equals(empty));
    }

    @Test
    public void testLenientAndStrictValuesAreNotEqual() {
        JsonFormat.Value lenient = JsonFormat.Value.empty().withLenient(Boolean.TRUE);
        JsonFormat.Value strict = lenient.withLenient(Boolean.FALSE);

        assertFalse(lenient.equals(strict));
        assertFalse(strict.equals(lenient));
    }

    @Test
    public void testClearingLeniencyWithNullRestoresEmptyEquivalentState() {
        JsonFormat.Value empty = JsonFormat.Value.empty();
        JsonFormat.Value lenient = empty.withLenient(Boolean.TRUE);

        // passing null clears the leniency setting entirely
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
