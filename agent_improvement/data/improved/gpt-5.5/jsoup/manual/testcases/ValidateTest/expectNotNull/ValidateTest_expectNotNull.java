package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("deprecation")
public class ValidateTest_expectNotNull {

    @Test
    void expectNotNullReturnsTheOriginalObject() {
        String foo = "Foo";

        String returned = Validate.expectNotNull(foo);

        assertSame(foo, returned);
    }

    @Test
    void expectNotNullThrowsWhenObjectIsNull() {
        String bar = null;

        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> Validate.expectNotNull(bar)
        );

        assertEquals("Object must not be null", exception.getMessage());
    }
}
