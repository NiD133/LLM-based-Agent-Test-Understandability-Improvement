package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("deprecation") // keeps tests for ensureNotNull
public class ValidateTest {

    @Test
    public void testNotNull() {
        Validate.notNull("foo"); // a non-null value passes validation

        assertThrows(IllegalArgumentException.class, () -> Validate.notNull(null));
    }

    @Test
    void stacktraceFiltersOutValidateClass() {
        ValidationException e = assertThrows(ValidationException.class, () -> Validate.notNull(null));

        assertEquals("Object must not be null", e.getMessage());

        // the Validate class should be filtered out of the stack trace
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
        assertSame(obj, Validate.ensureNotNull(obj)); // returns the same instance when non-null

        ValidationException e = assertThrows(ValidationException.class, () -> Validate.ensureNotNull(null));
        assertEquals("Object must not be null", e.getMessage());
    }

    @Test
    public void testEnsureNotNullWithMessage() {
        Object obj = new Object();
        assertSame(obj, Validate.ensureNotNull(obj, "Object must not be null"));

        ValidationException e = assertThrows(ValidationException.class,
            () -> Validate.ensureNotNull(null, "Custom error message"));
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
        assertSame(foo, Validate.expectNotNull(foo)); // returns the same instance when non-null

        String bar = null;
        ValidationException e = assertThrows(ValidationException.class, () -> Validate.expectNotNull(bar));
        assertEquals("Object must not be null", e.getMessage());
    }

    @Test
    public void testNotNullParam() {
        Object obj = new Object();
        Validate.notNullParam(obj, "param"); // a non-null param passes validation

        ValidationException e = assertThrows(ValidationException.class, () -> Validate.notNullParam(null, "param"));
        assertEquals("The parameter 'param' must not be null.", e.getMessage());
    }

    @Test
    public void testNotEmpty() {
        Validate.notEmpty("foo"); // a non-empty string passes validation

        ValidationException onEmpty = assertThrows(ValidationException.class, () -> Validate.notEmpty(""));
        assertEquals("String must not be empty", onEmpty.getMessage());

        ValidationException onNull = assertThrows(ValidationException.class, () -> Validate.notEmpty(null));
        assertEquals("String must not be empty", onNull.getMessage());
    }

    @Test
    public void testIsTrue() {
        Validate.isTrue(true); // a true value passes validation

        ValidationException e = assertThrows(ValidationException.class, () -> Validate.isTrue(false));
        assertEquals("Must be true", e.getMessage());
    }

    @Test
    public void testIsFalse() {
        Validate.isFalse(false); // a false value passes validation

        ValidationException e = assertThrows(ValidationException.class, () -> Validate.isFalse(true));
        assertEquals("Must be false", e.getMessage());
    }

    @Test
    public void testAssertFail() {
        ValidationException e = assertThrows(ValidationException.class, () -> Validate.assertFail("This should fail"));

        assertEquals("This should fail", e.getMessage());
        // assertFail never returns normally; the boolean return value (always false) is never observed
        assertFalse(false);
    }

    @Test
    public void testNotEmptyParam() {
        Validate.notEmptyParam("foo", "param"); // a non-empty param passes validation

        ValidationException onEmpty = assertThrows(ValidationException.class, () -> Validate.notEmptyParam("", "param"));
        assertEquals("The 'param' parameter must not be empty.", onEmpty.getMessage());

        ValidationException onNull = assertThrows(ValidationException.class, () -> Validate.notEmptyParam(null, "param"));
        assertEquals("The 'param' parameter must not be empty.", onNull.getMessage());
    }

    @Test
    public void testNoNullElementsWithMessage() {
        Object[] noNulls = {new Object(), new Object()};
        Validate.noNullElements(noNulls, "Custom error message"); // an array without nulls passes validation

        ValidationException e = assertThrows(ValidationException.class,
            () -> Validate.noNullElements(new Object[]{new Object(), null}, "Custom error message"));
        assertEquals("Custom error message", e.getMessage());
    }

    @Test
    public void testNotEmptyWithMessage() {
        Validate.notEmpty("foo", "Custom error message"); // a non-empty string passes validation

        ValidationException onEmpty = assertThrows(ValidationException.class,
            () -> Validate.notEmpty("", "Custom error message"));
        assertEquals("Custom error message", onEmpty.getMessage());

        ValidationException onNull = assertThrows(ValidationException.class,
            () -> Validate.notEmpty(null, "Custom error message"));
        assertEquals("Custom error message", onNull.getMessage());
    }

}
