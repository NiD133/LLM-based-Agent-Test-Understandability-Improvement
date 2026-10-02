package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonSetterTest_testFactories extends AnnotationTestUtil {

    private final JsonSetter.Value EMPTY = JsonSetter.Value.empty();

    @Test
    public void testFactories() throws Exception {
        JsonSetter.Value contentNullsOnly = JsonSetter.Value.forContentNulls(Nulls.SET);
        assertValueNullHandling(contentNullsOnly, Nulls.DEFAULT, Nulls.SET);
        assertEquals(Nulls.SET, contentNullsOnly.nonDefaultContentNulls());

        JsonSetter.Value valueNullsOnly = JsonSetter.Value.forValueNulls(Nulls.SKIP);
        assertValueNullHandling(valueNullsOnly, Nulls.SKIP, Nulls.DEFAULT);
        assertEquals(Nulls.SKIP, valueNullsOnly.nonDefaultValueNulls());
    }

    private void assertValueNullHandling(JsonSetter.Value value,
            Nulls expectedValueNulls, Nulls expectedContentNulls) {
        assertEquals(expectedValueNulls, value.getValueNulls());
        assertEquals(expectedContentNulls, value.getContentNulls());
    }
}
