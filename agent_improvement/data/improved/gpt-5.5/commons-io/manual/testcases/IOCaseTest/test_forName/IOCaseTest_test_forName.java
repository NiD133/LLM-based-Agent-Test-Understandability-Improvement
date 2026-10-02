package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_forName {

    @Test
    void test_forName() {
        assertEquals(IOCase.SENSITIVE, IOCase.forName("Sensitive"));
        assertEquals(IOCase.INSENSITIVE, IOCase.forName("Insensitive"));
        assertEquals(IOCase.SYSTEM, IOCase.forName("System"));

        assertThrows(IllegalArgumentException.class, () -> IOCase.forName("Blah"));
        assertThrows(IllegalArgumentException.class, () -> IOCase.forName(null));
    }
}
