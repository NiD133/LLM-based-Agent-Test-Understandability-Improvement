package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link IOCase#forName(String)}.
 */
public class IOCaseTest_test_forName {

    @Test
    void test_forName() {
        // Each known name maps back to its matching IOCase constant.
        assertEquals(IOCase.SENSITIVE, IOCase.forName("Sensitive"));
        assertEquals(IOCase.INSENSITIVE, IOCase.forName("Insensitive"));
        assertEquals(IOCase.SYSTEM, IOCase.forName("System"));

        // Unrecognized and null names are rejected with IllegalArgumentException.
        assertThrows(IllegalArgumentException.class, () -> IOCase.forName("Blah"));
        assertThrows(IllegalArgumentException.class, () -> IOCase.forName(null));
    }
}
