package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testToString extends AnnotationTestUtil {

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    public void testToString() {
        assertEquals("JsonFormat.Value(pattern=,shape=STRING,lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1)", JsonFormat.Value.forShape(JsonFormat.Shape.STRING).toString());
        assertEquals("JsonFormat.Value(pattern=[.],shape=ANY,lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1)", JsonFormat.Value.forPattern("[.]").toString());
    }
}
