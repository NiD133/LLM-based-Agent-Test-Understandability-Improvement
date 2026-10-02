package org.jsoup.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// keeps tests for ensureNotNull
@SuppressWarnings("deprecation")
public class ValidateTest_testAssertFail {

    @Test
    public void assertFailAlwaysThrowsWithGivenMessage() {
        String failureMessage = "This should fail";

        // Validate.assertFail always throws instead of returning, so it never
        // produces its (declared) false result.
        ValidationException thrown = assertThrows(
            ValidationException.class,
            () -> Validate.assertFail(failureMessage));

        assertEquals(failureMessage, thrown.getMessage());
    }
}
