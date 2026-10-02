package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that withContentNulls() and withValueNulls() produce new Value instances
 * with the specified null-handling strategies applied incrementally.
 */
public class JsonSetterTest_testSimpleMerge extends AnnotationTestUtil {

    private final JsonSetter.Value EMPTY = JsonSetter.Value.empty();

    @Test
    public void testSimpleMerge() {
        // Apply SKIP for content nulls to the empty baseline value
        JsonSetter.Value withContentNullsSkip = EMPTY.withContentNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, withContentNullsSkip.getContentNulls(),
                "contentNulls should be SKIP after withContentNulls(Nulls.SKIP)");

        // Chain withValueNulls on the previous result to also set value null handling to FAIL
        JsonSetter.Value withValueNullsFail = withContentNullsSkip.withValueNulls(Nulls.FAIL);
        assertEquals(Nulls.FAIL, withValueNullsFail.getValueNulls(),
                "valueNulls should be FAIL after withValueNulls(Nulls.FAIL)");
    }
}
