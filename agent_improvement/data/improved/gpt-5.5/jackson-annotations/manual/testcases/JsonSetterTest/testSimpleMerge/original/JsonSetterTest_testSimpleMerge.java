package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonSetterTest_testSimpleMerge extends AnnotationTestUtil {

    private final JsonSetter.Value EMPTY = JsonSetter.Value.empty();

    @Test
    public void testSimpleMerge() {
        JsonSetter.Value v = EMPTY.withContentNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, v.getContentNulls());
        v = v.withValueNulls(Nulls.FAIL);
        assertEquals(Nulls.FAIL, v.getValueNulls());
    }
}
