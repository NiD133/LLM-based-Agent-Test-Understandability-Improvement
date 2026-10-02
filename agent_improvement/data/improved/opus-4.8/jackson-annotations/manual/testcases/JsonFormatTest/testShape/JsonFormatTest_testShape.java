package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies the category-classification helpers on {@link JsonFormat.Shape}:
 * <ul>
 *   <li>{@link Shape#isNumeric()} — true only for the numeric shapes.</li>
 *   <li>{@link Shape#isStructured()} — true only for the structured shapes.</li>
 * </ul>
 */
public class JsonFormatTest_testShape extends AnnotationTestUtil {

    @Test
    public void testShape() {
        // STRING is a scalar text shape: neither numeric nor structured.
        assertFalse(Shape.STRING.isNumeric(), "STRING must not be numeric");
        assertFalse(Shape.STRING.isStructured(), "STRING must not be structured");

        // The three numeric shapes are reported as numeric.
        assertTrue(Shape.NUMBER_INT.isNumeric(), "NUMBER_INT must be numeric");
        assertTrue(Shape.NUMBER_FLOAT.isNumeric(), "NUMBER_FLOAT must be numeric");
        assertTrue(Shape.NUMBER.isNumeric(), "NUMBER must be numeric");

        // ARRAY and OBJECT are structured shapes.
        assertTrue(Shape.ARRAY.isStructured(), "ARRAY must be structured");
        assertTrue(Shape.OBJECT.isStructured(), "OBJECT must be structured");
    }
}
