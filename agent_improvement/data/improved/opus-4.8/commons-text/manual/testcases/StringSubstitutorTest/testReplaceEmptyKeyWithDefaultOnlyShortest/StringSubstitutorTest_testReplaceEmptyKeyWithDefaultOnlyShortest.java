package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that an empty variable name combined with a default value, e.g. {@code ${:-a}},
 * resolves to its default value.
 *
 * <p>The expression {@code ${:-a}} has no variable name to look up, so the substitutor must
 * fall back to the default value {@code "a"}. This behaviour is checked consistently across
 * every {@code replace}/{@code replaceIn} input type supported by {@link StringSubstitutor}.</p>
 */
public class StringSubstitutorTest_testReplaceEmptyKeyWithDefaultOnlyShortest {

    /** Template with an empty variable name and the default value "a". */
    private static final String TEMPLATE = "${:-a}";

    /** Expected result: the default value, since the variable name is empty. */
    private static final String EXPECTED = "a";

    private StringSubstitutor substitutor;

    @BeforeEach
    public void setUp() {
        // The lookup map is intentionally unrelated to TEMPLATE: ${:-a} never consults it,
        // so the result depends only on the inline default value.
        final Map<String, String> values = new HashMap<>();
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
        substitutor = new StringSubstitutor(values);
    }

    @Test
    void testReplaceEmptyKeyWithDefaultOnlyShortest() throws IOException {
        // replace(...) variants return the substituted string for each input type.
        assertEquals(EXPECTED, substitutor.replace(TEMPLATE));
        assertEquals(EXPECTED, substitutor.replace(TEMPLATE.toCharArray()));
        assertEquals(EXPECTED, substitutor.replace(new StringBuffer(TEMPLATE)));
        assertEquals(EXPECTED, substitutor.replace(new StringBuilder(TEMPLATE)));
        assertEquals(EXPECTED, substitutor.replace(new TextStringBuilder(TEMPLATE)));
        assertEquals(EXPECTED, substitutor.replace(new MutableObject<>(TEMPLATE)));

        // replaceIn(...) variants mutate the buffer in place and report that a replacement occurred.
        final StringBuffer stringBuffer = new StringBuffer(TEMPLATE);
        assertTrue(substitutor.replaceIn(stringBuffer));
        assertEquals(EXPECTED, stringBuffer.toString());

        final StringBuilder stringBuilder = new StringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(stringBuilder));
        assertEquals(EXPECTED, stringBuilder.toString());

        final TextStringBuilder textStringBuilder = new TextStringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(textStringBuilder));
        assertEquals(EXPECTED, textStringBuilder.toString());
    }
}
