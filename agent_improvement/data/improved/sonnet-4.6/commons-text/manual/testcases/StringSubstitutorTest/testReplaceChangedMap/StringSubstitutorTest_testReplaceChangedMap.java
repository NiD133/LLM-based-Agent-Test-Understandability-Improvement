package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceChangedMap {

    private static final String ACTUAL_ANIMAL = "quick brown fox";

    private static final String ACTUAL_TARGET = "lazy dog";

    private static final String CLASSIC_RESULT = "The quick brown fox jumps over the lazy dog.";

    private static final String CLASSIC_TEMPLATE = "The ${animal} jumps over the ${target}.";

    protected Map<String, String> values;

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * For subclasses to override.
     *
     * @throws IOException Thrown by subclasses.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Tests key replace changing map after initialization (not recommended).
     */
    @Test
    void testReplaceChangedMap() throws IOException {
        final StringSubstitutor sub = new StringSubstitutor(values);

        // Before map change: substitutes original values
        assertEqualsCharSeq(CLASSIC_RESULT, replace(sub, CLASSIC_TEMPLATE));

        // After map change: substitutor picks up the updated map value
        values.put("target", "moon");
        assertEqualsCharSeq("The quick brown fox jumps over the moon.", replace(sub, CLASSIC_TEMPLATE));
    }
}
