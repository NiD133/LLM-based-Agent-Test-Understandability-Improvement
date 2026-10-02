package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testShape extends AnnotationTestUtil {

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

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
