package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceUnknownKeyOnlyExtraLast {

    private static final String ACTUAL_ANIMAL = "quick brown fox";

    private static final String ACTUAL_TARGET = "lazy dog";

    protected Map<String, String> values;

    /**
     * Asserts two CharSequences are equal, including their lengths in the failure message for easier debugging.
     */
    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * Delegates to {@link StringSubstitutor#replace(String)}.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Verifies that a template containing only an unknown variable key followed by a trailing character
     * is returned unchanged — i.e. no substitution occurs when the key is not in the value map.
     */
    @Test
    void testReplaceUnknownKeyOnlyExtraLast() throws IOException {
        final String expected = "${person}.";
        assertEqualsCharSeq(expected, replace(new StringSubstitutor(values), expected));
    }
}
