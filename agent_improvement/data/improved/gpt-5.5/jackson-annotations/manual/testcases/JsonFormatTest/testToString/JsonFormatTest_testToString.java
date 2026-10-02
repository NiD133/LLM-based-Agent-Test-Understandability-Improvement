package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonFormatTest_testToString extends AnnotationTestUtil {

    @Test
    public void testToString() {
        String shapeStringValue = JsonFormat.Value.forShape(JsonFormat.Shape.STRING).toString();
        assertEquals(
                "JsonFormat.Value(pattern=,shape=STRING,lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1)",
                shapeStringValue);

        String patternValue = JsonFormat.Value.forPattern("[.]").toString();
        assertEquals(
                "JsonFormat.Value(pattern=[.],shape=ANY,lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1)",
                patternValue);
    }
}
