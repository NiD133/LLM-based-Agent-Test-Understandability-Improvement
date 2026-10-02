package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("deprecation") // keeps tests for ensureNotNull
public class ValidateTest {

    @Test
    public void testNotNull() {
        Validate.notNull("foo");
        assertThrows(ValidationException.class, () -> Validate.notNull(null));
    }

    @Test
    void stacktraceFiltersOutValidateClass() {
        ValidationException e = assertThrows(ValidationException.class, () -> Validate.notNull(null));
        assertEquals("Object must not be null", e.getMessage());
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (StackTraceElement trace : stackTrace) {
            assertNotEquals(trace.getClassName(), Validate.class.getName());
        }
        assertTrue(stackTrace.length >= 1);
    }

    @Test
    void nonnullParam() {
        ValidationException e = assertThrows(ValidationException.class, () -> Validate.notNullParam(null, "foo"));
        assertEquals("The parameter 'foo' must not be null.", e.getMessage());
    }

    @Test
    public void testWtf() {
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> Validate.wtf("Unexpected state reached"));
        assertEquals("Unexpected state reached", e.getMessage());
    }

    @Test
    public void testEnsureNotNull() {
        Object obj = new Object();
        assertSame(obj, Validate.ensureNotNull(obj));

        ValidationException e = assertThrows(ValidationException.class, () -> Validate.ensureNotNull(null));
        assertEquals("Object must not be null", e.getMessage());
    }

    @Test
    public void testEnsureNotNullWithMessage() {
        Object obj = new Object();
        assertSame(obj, Validate.ensureNotNull(obj, "Object must not be null"));

        ValidationException e = assertThrows(ValidationException.class, () -> Validate.ensureNotNull(null, "Custom error message"));
        assertEquals("Custom error message", e.getMessage());
    }

    @Test
    public void testEnsureNotNullWithFormattedMessage() {
        Object obj = new Object();
        assertSame(obj, Validate.ensureNotNull(obj, "Object must not be null: %s", "additional info"));

        ValidationException e = assertThrows(ValidationException.class,
            () -> Validate.ensureNotNull(null, "Object must not be null: %s", "additional info"));
        assertEquals("Object must not be null: additional info", e.getMessage());
    }

    @Test
    void expectNotNull() {
        String foo = "Foo";
        assertSame(foo, Validate.expectNotNull(foo));

        ValidationException e = assertThrows(ValidationException.class, () -> Validate.expectNotNull(null));
        assertEquals("Object must not be null", e.getMessage());
    }

    @Test
    public void testNotNullParam() {
        Object obj = new Object();
        Validate.notNullParam(obj, "param");

        ValidationException e = assertThrows(ValidationException.class, () -> Validate.notNullParam(null, "param"));
        assertEquals("The parameter 'param' must not be null.", e.getMessage());
    }

    @Test
    public void testNotEmpty() {
        Validate.notEmpty("foo");

        ValidationException emptyStringException = assertThrows(ValidationException.class, () -> Validate.notEmpty(""));
        assertEquals("String must not be empty", emptyStringException.getMessage());

        ValidationException nullStringException = assertThrows(ValidationException.class, () -> Validate.notEmpty(null));
        assertEquals("String must not be empty", nullStringException.getMessage());
    }

    @Test
    public void testIsTrue() {
        Validate.isTrue(true);

        ValidationException e = assertThrows(ValidationException.class, () -> Validate.isTrue(false));
        assertEquals("Must be true", e.getMessage());
    }

    @Test
    public void testIsFalse() {
        Validate.isFalse(false);

        ValidationException e = assertThrows(ValidationException.class, () -> Validate.isFalse(true));
        assertEquals("Must be false", e.getMessage());
    }

    @Test
    public void testAssertFail() {
        ValidationException e = assertThrows(ValidationException.class, () -> Validate.assertFail("This should fail"));
        assertEquals("This should fail", e.getMessage());
    }

    @Test
    public void testNotEmptyParam() {
        Validate.notEmptyParam("foo", "param");

        ValidationException emptyStringException = assertThrows(ValidationException.class, () -> Validate.notEmptyParam("", "param"));
        assertEquals("The 'param' parameter must not be empty.", emptyStringException.getMessage());

        ValidationException nullStringException = assertThrows(ValidationException.class, () -> Validate.notEmptyParam(null, "param"));
        assertEquals("The 'param' parameter must not be empty.", nullStringException.getMessage());
    }

    @Test
    public void testNoNullElementsWithMessage() {
        Object[] array = {new Object(), new Object()};
        Validate.noNullElements(array, "Custom error message");

        ValidationException e = assertThrows(ValidationException.class,
            () -> Validate.noNullElements(new Object[]{new Object(), null}, "Custom error message"));
        assertEquals("Custom error message", e.getMessage());
    }

    @Test
    public void testNotEmptyWithMessage() {
        Validate.notEmpty("foo", "Custom error message");

        ValidationException emptyStringException = assertThrows(ValidationException.class, () -> Validate.notEmpty("", "Custom error message"));
        assertEquals("Custom error message", emptyStringException.getMessage());

        ValidationException nullStringException = assertThrows(ValidationException.class, () -> Validate.notEmpty(null, "Custom error message"));
        assertEquals("Custom error message", nullStringException.getMessage());
    }
}
