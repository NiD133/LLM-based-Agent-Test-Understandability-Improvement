package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceNull {

    protected Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Verifies that every replace/replaceIn overload returns null or false when
     * given a null source, regardless of the variable map that was configured.
     */
    @Test
    void testReplaceNull() {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // All replace(...) overloads must return null for a null source.
        assertNull(substitutor.replace((String) null));
        assertNull(substitutor.replace((String) null, 0, 100));
        assertNull(substitutor.replace((char[]) null));
        assertNull(substitutor.replace((char[]) null, 0, 100));
        assertNull(substitutor.replace((StringBuffer) null));
        assertNull(substitutor.replace((StringBuffer) null, 0, 100));
        assertNull(substitutor.replace((TextStringBuilder) null));
        assertNull(substitutor.replace((TextStringBuilder) null, 0, 100));
        assertNull(substitutor.replace((Object) null));

        // All replaceIn(...) overloads must return false for a null source.
        assertFalse(substitutor.replaceIn((StringBuffer) null));
        assertFalse(substitutor.replaceIn((StringBuffer) null, 0, 100));
        assertFalse(substitutor.replaceIn((TextStringBuilder) null));
        assertFalse(substitutor.replaceIn((TextStringBuilder) null, 0, 100));
    }
}
