package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testResolveVariable {

    private static final String VARIABLE_NAME = "name";
    private static final int VARIABLE_START_POSITION = 3;
    private static final int VARIABLE_END_POSITION = 10;

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
     * Tests protected.
     */
    @Test
    void testResolveVariable() {
        final StrBuilder builder = new StrBuilder("Hi ${name}!");
        final Map<String, String> map = new HashMap<>();
        map.put(VARIABLE_NAME, "commons");

        final StrSubstitutor sub = new StrSubstitutor(map) {

            @Override
            protected String resolveVariable(final String variableName, final StrBuilder buf, final int startPos, final int endPos) {
                assertEquals(VARIABLE_NAME, variableName);
                assertSame(builder, buf);
                assertEquals(VARIABLE_START_POSITION, startPos);
                assertEquals(VARIABLE_END_POSITION, endPos);
                return "jakarta";
            }
        };

        sub.replaceIn(builder);

        assertEquals("Hi jakarta!", builder.toString());
    }
}
