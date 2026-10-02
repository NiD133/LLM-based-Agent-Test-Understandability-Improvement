package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("deprecation") // keeps tests for ensureNotNull
public class ValidateTest {
    private static ValidationException assertValidationException(
        String expectedMessage,
        Executable validation
    ) {
        ValidationException exception = assertThrows(ValidationException.class, validation);
        assertEquals(expectedMessage, exception.getMessage());
        return exception;
    }

    @Test
    public void testNotNull() {
        Validate.notNull("foo");

        assertValidationException(
            "Object must not be null",
            () -> Validate.notNull(null)
        );
    }

    @Test
    void stacktraceFiltersOutValidateClass() {
        ValidationException exception = assertValidationException(
            "Object must not be null",
            () -> Validate.notNull(null)
        );

        StackTraceElement[] stackTrace = exception.getStackTrace();
        for (StackTraceElement trace : stackTrace) {
            assertNotEquals(trace.getClassName(), Validate.class.getName());
        }
        assertTrue(stackTrace.length >= 1);
    }

    @Test
    void nonnullParam() {
        assertValidationException(
            "The parameter 'foo' must not be null.",
            () -> Validate.notNullParam(null, "foo")
        );
    }

    @Test
    public void testWtf() {
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> Validate.wtf("Unexpected state reached")
        );
        assertEquals("Unexpected state reached", exception.getMessage());
    }

    @Test
    public void testEnsureNotNull() {
        Object obj = new Object();
        assertSame(obj, Validate.ensureNotNull(obj));

        assertValidationException(
            "Object must not be null",
            () -> Validate.ensureNotNull(null)
        );
    }

    @Test
    public void testEnsureNotNullWithMessage() {
        Object obj = new Object();
        assertSame(obj, Validate.ensureNotNull(obj, "Object must not be null"));

        assertValidationException(
            "Custom error message",
            () -> Validate.ensureNotNull(null, "Custom error message")
        );
    }

    @Test
    public void testEnsureNotNullWithFormattedMessage() {
        Object obj = new Object();
        assertSame(
            obj,
            Validate.ensureNotNull(obj, "Object must not be null: %s", "additional info")
        );

        assertValidationException(
            "Object must not be null: additional info",
            () -> Validate.ensureNotNull(null, "Object must not be null: %s", "additional info")
        );
    }

    @Test
    void expectNotNull() {
        String foo = "Foo";
        String foo2 = Validate.expectNotNull(foo);
        assertSame(foo, foo2);

        String bar = null;
        assertValidationException(
            "Object must not be null",
            () -> Validate.expectNotNull(bar)
        );
    }

    @Test
    public void testNotNullParam() {
        Object obj = new Object();
        Validate.notNullParam(obj, "param");

        assertValidationException(
            "The parameter 'param' must not be null.",
            () -> Validate.notNullParam(null, "param")
        );
    }

    @Test
    public void testNotEmpty() {
        Validate.notEmpty("foo");

        assertValidationException(
            "String must not be empty",
            () -> Validate.notEmpty("")
        );
        assertValidationException(
            "String must not be empty",
            () -> Validate.notEmpty(null)
        );
    }

    @Test
    public void testIsTrue() {
        Validate.isTrue(true);

        assertValidationException(
            "Must be true",
            () -> Validate.isTrue(false)
        );
    }

    @Test
    public void testIsFalse() {
        Validate.isFalse(false);

        assertValidationException(
            "Must be false",
            () -> Validate.isFalse(true)
        );
    }

    @Test
    public void testAssertFail() {
        boolean result = false;

        assertValidationException(
            "This should fail",
            () -> Validate.assertFail("This should fail")
        );
        assertFalse(result);
    }

    @Test
    public void testNotEmptyParam() {
        Validate.notEmptyParam("foo", "param");

        assertValidationException(
            "The 'param' parameter must not be empty.",
            () -> Validate.notEmptyParam("", "param")
        );
        assertValidationException(
            "The 'param' parameter must not be empty.",
            () -> Validate.notEmptyParam(null, "param")
        );
    }

    @Test
    public void testNoNullElementsWithMessage() {
        Object[] array = {new Object(), new Object()};
        Validate.noNullElements(array, "Custom error message");

        assertValidationException(
            "Custom error message",
            () -> Validate.noNullElements(new Object[]{new Object(), null}, "Custom error message")
        );
    }

    @Test
    public void testNotEmptyWithMessage() {
        Validate.notEmpty("foo", "Custom error message");

        assertValidationException(
            "Custom error message",
            () -> Validate.notEmpty("", "Custom error message")
        );
        assertValidationException(
            "Custom error message",
            () -> Validate.notEmpty(null, "Custom error message")
        );
    }
}
