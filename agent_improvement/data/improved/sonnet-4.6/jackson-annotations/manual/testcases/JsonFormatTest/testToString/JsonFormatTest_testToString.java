package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testToString extends AnnotationTestUtil {

    // Expected toString template:
    // JsonFormat.Value(pattern=<p>,shape=<s>,lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1)

    @Test
    public void testToString_valueForShape_STRING() {
        JsonFormat.Value value = JsonFormat.Value.forShape(JsonFormat.Shape.STRING);

        String expected = "JsonFormat.Value(pattern=,shape=STRING,"
                + "lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1)";

        assertEquals(expected, value.toString());
    }

    @Test
    public void testToString_valueForPattern() {
        JsonFormat.Value value = JsonFormat.Value.forPattern("[.]");

        String expected = "JsonFormat.Value(pattern=[.],shape=ANY,"
                + "lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1)";

        assertEquals(expected, value.toString());
    }
}
