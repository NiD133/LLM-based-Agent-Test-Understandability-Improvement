package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceNull {

    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Tests that every replace() overload returns null and every replaceIn() overload
     * returns false when passed a null source, regardless of the variable map configured.
     */
    @Test
    void testReplaceNull() {
        final StrSubstitutor sub = new StrSubstitutor(values);

        // replace(String) / replace(String, offset, length) → null
        assertNull(sub.replace((String) null));
        assertNull(sub.replace((String) null, 0, 100));

        // replace(char[]) / replace(char[], offset, length) → null
        assertNull(sub.replace((char[]) null));
        assertNull(sub.replace((char[]) null, 0, 100));

        // replace(StringBuffer) / replace(StringBuffer, offset, length) → null
        assertNull(sub.replace((StringBuffer) null));
        assertNull(sub.replace((StringBuffer) null, 0, 100));

        // replace(StrBuilder) / replace(StrBuilder, offset, length) → null
        assertNull(sub.replace((StrBuilder) null));
        assertNull(sub.replace((StrBuilder) null, 0, 100));

        // replace(Object) → null
        assertNull(sub.replace((Object) null));

        // replaceIn(StringBuffer) / replaceIn(StringBuffer, offset, length) → false
        assertFalse(sub.replaceIn((StringBuffer) null));
        assertFalse(sub.replaceIn((StringBuffer) null, 0, 100));

        // replaceIn(StrBuilder) / replaceIn(StrBuilder, offset, length) → false
        assertFalse(sub.replaceIn((StrBuilder) null));
        assertFalse(sub.replaceIn((StrBuilder) null, 0, 100));
    }
}
