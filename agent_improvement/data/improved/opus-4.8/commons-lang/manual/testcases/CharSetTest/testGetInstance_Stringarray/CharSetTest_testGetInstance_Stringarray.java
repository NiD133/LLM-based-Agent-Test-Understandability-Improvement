package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharSet#getInstance(String...)}, the varargs factory method.
 *
 * <p>Each case below verifies the {@code toString()} of the produced
 * {@link CharSet}, which renders as the bracketed list of character ranges it
 * holds (e.g. an empty set prints {@code "[]"}).</p>
 */
public class CharSetTest_testGetInstance_Stringarray extends AbstractLangTest {

    @Test
    void testGetInstance_Stringarray() {
        // A null array argument yields the shared EMPTY set -> no ranges.
        assertEquals("[]", CharSet.getInstance((String[]) null).toString());

        // No arguments at all (empty varargs) likewise yields an empty set.
        assertEquals("[]", CharSet.getInstance().toString());

        // An array whose only element is null contributes no characters.
        assertEquals("[]", CharSet.getInstance(new String[] { null }).toString());

        // The range definition "a-e" produces a single range printed as "[a-e]".
        assertEquals("[a-e]", CharSet.getInstance("a-e").toString());
    }
}
