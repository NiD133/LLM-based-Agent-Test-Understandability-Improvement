package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class JsonSetterTest_testConstruct extends AnnotationTestUtil {

    private final JsonSetter.Value emptyValue = JsonSetter.Value.empty();

    @Test
    public void testConstruct() throws Exception {
        JsonSetter.Value valueFromNullSettings = JsonSetter.Value.construct(null, null);

        assertSame(emptyValue, valueFromNullSettings);
    }
}
