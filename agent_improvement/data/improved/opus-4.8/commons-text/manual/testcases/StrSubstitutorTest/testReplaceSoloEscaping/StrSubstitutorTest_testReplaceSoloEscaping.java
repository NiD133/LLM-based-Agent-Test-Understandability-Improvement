package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StrSubstitutor} escaping behaviour.
 *
 * <p>A doubled variable prefix ("$$") is an escape sequence: it collapses to a
 * single literal "$" and the variable expression that follows is emitted as-is,
 * without any lookup being performed.</p>
 */
public class StrSubstitutorTest_testReplaceSoloEscaping {

    /** Template whose leading "$$" escapes the variable marker. */
    private static final String ESCAPED_TEMPLATE = "$${animal}";

    /** Expected output: "$$" collapses to "$" and "${animal}" stays literal. */
    private static final String EXPECTED_RESULT = "${animal}";

    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * Tests that an escaped variable marker is emitted literally, verified across
     * every input source type that {@link StrSubstitutor} accepts.
     */
    @Test
    void testReplaceSoloEscaping() {
        final StrSubstitutor sub = new StrSubstitutor(values);

        // replace(...) reads from each supported source type and returns a new String.
        assertEquals(EXPECTED_RESULT, sub.replace(ESCAPED_TEMPLATE));
        assertEquals(EXPECTED_RESULT, sub.replace(ESCAPED_TEMPLATE.toCharArray()));
        assertEquals(EXPECTED_RESULT, sub.replace(new StringBuffer(ESCAPED_TEMPLATE)));
        assertEquals(EXPECTED_RESULT, sub.replace(new StringBuilder(ESCAPED_TEMPLATE)));
        assertEquals(EXPECTED_RESULT, sub.replace(new StrBuilder(ESCAPED_TEMPLATE)));
        // replace(Object) substitutes into the object's toString() value.
        assertEquals(EXPECTED_RESULT, sub.replace(new MutableObject<>(ESCAPED_TEMPLATE)));

        // replaceIn(...) rewrites a mutable buffer in place and returns true when it changed.
        final StringBuffer stringBuffer = new StringBuffer(ESCAPED_TEMPLATE);
        assertTrue(sub.replaceIn(stringBuffer));
        assertEquals(EXPECTED_RESULT, stringBuffer.toString());

        final StringBuilder stringBuilder = new StringBuilder(ESCAPED_TEMPLATE);
        assertTrue(sub.replaceIn(stringBuilder));
        assertEquals(EXPECTED_RESULT, stringBuilder.toString());

        final StrBuilder strBuilder = new StrBuilder(ESCAPED_TEMPLATE);
        assertTrue(sub.replaceIn(strBuilder));
        assertEquals(EXPECTED_RESULT, strBuilder.toString());
    }
}
