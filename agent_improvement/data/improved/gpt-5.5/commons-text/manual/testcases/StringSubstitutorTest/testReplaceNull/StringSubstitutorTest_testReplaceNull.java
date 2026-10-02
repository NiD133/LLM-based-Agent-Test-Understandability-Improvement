package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceNull {

    private static final int NULL_INPUT_OFFSET = 0;
    private static final int NULL_INPUT_LENGTH = 100;

    private Map<String, String> values;

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

    @Test
    void testReplaceNull() throws IOException {
        doNotReplace(null);
    }

    private void doNotReplace(final String replaceTemplate) throws IOException {
        assertNullInputsAreNotReplaced(new StringSubstitutor(values), replaceTemplate);
    }

    private void assertNullInputsAreNotReplaced(final StringSubstitutor substitutor, final String replaceTemplate)
        throws IOException {
        assertNull(replace(substitutor, replaceTemplate));
        assertNull(substitutor.replace((String) null, NULL_INPUT_OFFSET, NULL_INPUT_LENGTH));
        assertNull(substitutor.replace((char[]) null));
        assertNull(substitutor.replace((char[]) null, NULL_INPUT_OFFSET, NULL_INPUT_LENGTH));
        assertNull(substitutor.replace((StringBuffer) null));
        assertNull(substitutor.replace((StringBuffer) null, NULL_INPUT_OFFSET, NULL_INPUT_LENGTH));
        assertNull(substitutor.replace((TextStringBuilder) null));
        assertNull(substitutor.replace((TextStringBuilder) null, NULL_INPUT_OFFSET, NULL_INPUT_LENGTH));
        assertNull(substitutor.replace((Object) null));
        assertFalse(substitutor.replaceIn((StringBuffer) null));
        assertFalse(substitutor.replaceIn((StringBuffer) null, NULL_INPUT_OFFSET, NULL_INPUT_LENGTH));
        assertFalse(substitutor.replaceIn((TextStringBuilder) null));
        assertFalse(substitutor.replaceIn((TextStringBuilder) null, NULL_INPUT_OFFSET, NULL_INPUT_LENGTH));
    }

    private String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }
}
