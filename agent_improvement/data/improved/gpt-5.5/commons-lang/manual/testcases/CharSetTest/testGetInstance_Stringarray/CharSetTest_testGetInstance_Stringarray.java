package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CharSetTest_testGetInstance_Stringarray extends AbstractLangTest {

    private static final String EMPTY_CHAR_SET = "[]";
    private static final String LOWERCASE_A_TO_E_RANGE = "[a-e]";

    @Test
    void testGetInstance_Stringarray() {
        assertCharSetToString(EMPTY_CHAR_SET, (String[]) null);
        assertCharSetToString(EMPTY_CHAR_SET);
        assertCharSetToString(EMPTY_CHAR_SET, new String[] { null });
        assertCharSetToString(LOWERCASE_A_TO_E_RANGE, "a-e");
    }

    private void assertCharSetToString(final String expected, final String... setDefinitions) {
        assertEquals(expected, CharSet.getInstance(setDefinitions).toString());
    }
}
