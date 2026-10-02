package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonSetterTest_testSimpleMerge extends AnnotationTestUtil {

    private final JsonSetter.Value emptySetterValue = JsonSetter.Value.empty();

    @Test
    public void testSimpleMerge() {
        JsonSetter.Value setterValue = emptySetterValue.withContentNulls(Nulls.SKIP);

        assertEquals(Nulls.SKIP, setterValue.getContentNulls());

        setterValue = setterValue.withValueNulls(Nulls.FAIL);

        assertEquals(Nulls.FAIL, setterValue.getValueNulls());
    }
}
