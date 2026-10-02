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
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() throws Exception {
        values = null;
    }

    /**
     * Tests replace with null.
     */
    @Test
    void testReplaceNull() {
        final StrSubstitutor substitutor = new StrSubstitutor(values);

        assertNullReplacementResults(substitutor);
        assertNullInPlaceReplacementResults(substitutor);
    }

    private void assertNullReplacementResults(final StrSubstitutor substitutor) {
        assertNull(substitutor.replace((String) null));
        assertNull(substitutor.replace((String) null, 0, 100));

        assertNull(substitutor.replace((char[]) null));
        assertNull(substitutor.replace((char[]) null, 0, 100));

        assertNull(substitutor.replace((StringBuffer) null));
        assertNull(substitutor.replace((StringBuffer) null, 0, 100));

        assertNull(substitutor.replace((StrBuilder) null));
        assertNull(substitutor.replace((StrBuilder) null, 0, 100));

        assertNull(substitutor.replace((Object) null));
    }

    private void assertNullInPlaceReplacementResults(final StrSubstitutor substitutor) {
        assertFalse(substitutor.replaceIn((StringBuffer) null));
        assertFalse(substitutor.replaceIn((StringBuffer) null, 0, 100));

        assertFalse(substitutor.replaceIn((StrBuilder) null));
        assertFalse(substitutor.replaceIn((StrBuilder) null, 0, 100));
    }
}
