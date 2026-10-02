package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link IOCase#getName()}.
 *
 * <p>Verifies that each enum constant reports the human-readable name it was
 * declared with.</p>
 */
public class IOCaseTest_test_getName {

    @Test
    void test_getName() {
        assertEquals("Sensitive", IOCase.SENSITIVE.getName());
        assertEquals("Insensitive", IOCase.INSENSITIVE.getName());
        assertEquals("System", IOCase.SYSTEM.getName());
    }
}
