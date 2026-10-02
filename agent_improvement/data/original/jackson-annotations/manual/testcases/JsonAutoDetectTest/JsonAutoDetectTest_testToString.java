package com.fasterxml.jackson.annotation;

import java.lang.reflect.Member;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonAutoDetectTest_testToString extends AnnotationTestUtil {

    private final static JsonAutoDetect.Value NO_OVERRIDES = JsonAutoDetect.Value.noOverrides();

    private final static JsonAutoDetect.Value DEFAULTS = JsonAutoDetect.Value.defaultVisibility();

    @Test
    public void testToString() {
        assertEquals("JsonAutoDetect.Value(fields=PUBLIC_ONLY,getters=PUBLIC_ONLY," + "isGetters=PUBLIC_ONLY,setters=ANY,creators=PUBLIC_ONLY,scalarConstructors=NON_PRIVATE)", JsonAutoDetect.Value.defaultVisibility().toString());
        assertEquals("JsonAutoDetect.Value(fields=DEFAULT,getters=DEFAULT," + "isGetters=DEFAULT,setters=DEFAULT,creators=DEFAULT,scalarConstructors=DEFAULT)", JsonAutoDetect.Value.noOverrides().toString());
    }
}
