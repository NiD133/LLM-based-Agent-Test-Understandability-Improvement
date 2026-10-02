package com.fasterxml.jackson.annotation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class JsonFormatTest_testShape extends AnnotationTestUtil {

    @Test
    public void testShape() {
        assertFalse(JsonFormat.Shape.STRING.isNumeric());
        assertFalse(JsonFormat.Shape.STRING.isStructured());

        assertTrue(JsonFormat.Shape.NUMBER_INT.isNumeric());
        assertTrue(JsonFormat.Shape.NUMBER_FLOAT.isNumeric());
        assertTrue(JsonFormat.Shape.NUMBER.isNumeric());

        assertTrue(JsonFormat.Shape.ARRAY.isStructured());
        assertTrue(JsonFormat.Shape.OBJECT.isStructured());
    }
}
