package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonSetterTest_testConstruct extends AnnotationTestUtil {

    private final JsonSetter.Value EMPTY = JsonSetter.Value.empty();

    @Test
    public void testConstruct() throws Exception {
        JsonSetter.Value v = JsonSetter.Value.construct(null, null);
        assertSame(EMPTY, v);
    }
}
