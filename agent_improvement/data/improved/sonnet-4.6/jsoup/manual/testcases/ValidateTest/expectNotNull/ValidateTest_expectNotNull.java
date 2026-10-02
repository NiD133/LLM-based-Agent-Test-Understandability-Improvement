package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for Validate.expectNotNull: verifies it returns non-null objects unchanged and throws on null input.
@SuppressWarnings("deprecation")
public class ValidateTest_expectNotNull {

    @Test
    void expectNotNull_returnsTheSameObjectWhenNotNull() {
        String foo = "Foo";
        String foo2 = Validate.expectNotNull(foo);
        assertSame(foo, foo2);
    }

    @Test
    void expectNotNull_throwsValidationExceptionWithDefaultMessageWhenNull() {
        String bar = null;
        ValidationException thrown = assertThrows(ValidationException.class,
            () -> Validate.expectNotNull(bar));
        assertEquals("Object must not be null", thrown.getMessage());
    }
}
