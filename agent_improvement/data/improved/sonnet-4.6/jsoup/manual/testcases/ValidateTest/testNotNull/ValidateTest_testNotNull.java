package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidateTest_testNotNull {

    @Test
    public void notNull_withNonNullObject_doesNotThrow() {
        assertDoesNotThrow(() -> Validate.notNull("foo"));
    }

    @Test
    public void notNull_withNull_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Validate.notNull(null));
    }
}
