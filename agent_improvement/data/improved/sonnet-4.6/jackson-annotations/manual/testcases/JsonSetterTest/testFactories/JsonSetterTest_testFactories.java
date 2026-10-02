package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonSetterTest_testFactories extends AnnotationTestUtil {

    @Test
    public void testForContentNullsFactory() throws Exception {
        JsonSetter.Value contentNullsValue = JsonSetter.Value.forContentNulls(Nulls.SET);
        assertEquals(Nulls.DEFAULT, contentNullsValue.getValueNulls());
        assertEquals(Nulls.SET, contentNullsValue.getContentNulls());
        assertEquals(Nulls.SET, contentNullsValue.nonDefaultContentNulls());
    }

    @Test
    public void testForValueNullsFactory() throws Exception {
        JsonSetter.Value valueNullsValue = JsonSetter.Value.forValueNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, valueNullsValue.getValueNulls());
        assertEquals(Nulls.DEFAULT, valueNullsValue.getContentNulls());
        assertEquals(Nulls.SKIP, valueNullsValue.nonDefaultValueNulls());
    }
}
