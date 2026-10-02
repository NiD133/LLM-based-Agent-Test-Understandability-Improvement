package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceEmpty {

    private static final String EMPTY_TEMPLATE = "";

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

    @Test
    void testReplaceEmpty() {
        final StrSubstitutor substitutor = new StrSubstitutor(values);

        assertEquals(EMPTY_TEMPLATE, substitutor.replace(EMPTY_TEMPLATE));

        final StrBuilder templateBuilder = new StrBuilder(EMPTY_TEMPLATE);
        assertFalse(substitutor.replaceIn(templateBuilder));
        assertEquals(EMPTY_TEMPLATE, templateBuilder.toString());
    }
}
