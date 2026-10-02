package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CharSetTest_testGetInstance_Stringarray extends AbstractLangTest {

    // Expected string representation of a CharSet with no characters
    private static final String EMPTY_SET_REPR = "[]";

    @Test
    void testGetInstance_nullArray_returnsEmptySet() {
        CharSet result = CharSet.getInstance((String[]) null);
        assertEquals(EMPTY_SET_REPR, result.toString());
    }

    @Test
    void testGetInstance_noArgs_returnsEmptySet() {
        CharSet result = CharSet.getInstance();
        assertEquals(EMPTY_SET_REPR, result.toString());
    }

    @Test
    void testGetInstance_arrayContainingSingleNullElement_returnsEmptySet() {
        CharSet result = CharSet.getInstance(new String[] { null });
        assertEquals(EMPTY_SET_REPR, result.toString());
    }

    @Test
    void testGetInstance_rangeString_returnsSetWithThatRange() {
        String rangeSpec = "a-e";
        CharSet result = CharSet.getInstance(rangeSpec);
        assertEquals("[a-e]", result.toString());
    }
}
