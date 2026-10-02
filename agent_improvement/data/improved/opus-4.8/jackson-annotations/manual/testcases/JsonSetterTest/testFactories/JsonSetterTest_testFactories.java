package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies the {@code JsonSetter.Value} factory methods that build an instance
 * from a single {@link Nulls} setting, leaving the other setting at its default.
 */
public class JsonSetterTest_testFactories extends AnnotationTestUtil {

    private final JsonSetter.Value EMPTY = JsonSetter.Value.empty();

    @Test
    public void testFactories() throws Exception {
        // forContentNulls(...) sets only the "content nulls"; the value nulls stay at DEFAULT.
        JsonSetter.Value contentOnly = JsonSetter.Value.forContentNulls(Nulls.SET);
        assertEquals(Nulls.DEFAULT, contentOnly.getValueNulls());
        assertEquals(Nulls.SET, contentOnly.getContentNulls());
        // nonDefaultContentNulls() returns the content setting since it is not DEFAULT.
        assertEquals(Nulls.SET, contentOnly.nonDefaultContentNulls());

        // forValueNulls(...) sets only the "value nulls"; the content nulls stay at DEFAULT.
        JsonSetter.Value valueOnly = JsonSetter.Value.forValueNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, valueOnly.getValueNulls());
        assertEquals(Nulls.DEFAULT, valueOnly.getContentNulls());
        // nonDefaultValueNulls() returns the value setting since it is not DEFAULT.
        assertEquals(Nulls.SKIP, valueOnly.nonDefaultValueNulls());
    }
}
