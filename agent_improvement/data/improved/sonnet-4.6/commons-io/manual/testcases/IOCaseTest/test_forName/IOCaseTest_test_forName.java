package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_forName {

    @Test
    void test_forName_validNames_returnMatchingConstant() {
        assertEquals(IOCase.SENSITIVE, IOCase.forName("Sensitive"));
        assertEquals(IOCase.INSENSITIVE, IOCase.forName("Insensitive"));
        assertEquals(IOCase.SYSTEM, IOCase.forName("System"));
    }

    @Test
    void test_forName_unknownName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> IOCase.forName("Blah"));
    }

    @Test
    void test_forName_nullName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> IOCase.forName(null));
    }
}
