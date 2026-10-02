package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testShape extends AnnotationTestUtil {

    @Test
    public void testStringShapeIsNotNumeric() {
        assertFalse(Shape.STRING.isNumeric());
    }

    @Test
    public void testStringShapeIsNotStructured() {
        assertFalse(Shape.STRING.isStructured());
    }

    @Test
    public void testNumericShapesAreNumeric() {
        assertTrue(Shape.NUMBER_INT.isNumeric());
        assertTrue(Shape.NUMBER_FLOAT.isNumeric());
        assertTrue(Shape.NUMBER.isNumeric());
    }

    @Test
    public void testStructuredShapesAreStructured() {
        assertTrue(Shape.ARRAY.isStructured());
        assertTrue(Shape.OBJECT.isStructured());
    }
}
