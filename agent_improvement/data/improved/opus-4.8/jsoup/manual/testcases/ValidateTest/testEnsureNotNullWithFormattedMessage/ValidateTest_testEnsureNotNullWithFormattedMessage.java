package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests {@link Validate#ensureNotNull(Object, String, Object...)}, which formats the supplied
 * message template with the given arguments only when the validated object is null.
 */
@SuppressWarnings("deprecation")
public class ValidateTest_testEnsureNotNullWithFormattedMessage {

    private static final String MESSAGE_TEMPLATE = "Object must not be null: %s";
    private static final String MESSAGE_ARGUMENT = "additional info";

    @Test
    public void returnsSameObjectWhenNotNull() {
        Object input = new Object();

        Object result = Validate.ensureNotNull(input, MESSAGE_TEMPLATE, MESSAGE_ARGUMENT);

        assertSame(input, result);
    }

    @Test
    public void throwsWithFormattedMessageWhenNull() {
        ValidationException thrown = assertThrows(ValidationException.class,
            () -> Validate.ensureNotNull(null, MESSAGE_TEMPLATE, MESSAGE_ARGUMENT));

        assertEquals("Object must not be null: additional info", thrown.getMessage());
    }
}
