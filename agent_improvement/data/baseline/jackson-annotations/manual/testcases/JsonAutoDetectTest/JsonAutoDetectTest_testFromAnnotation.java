package com.fasterxml.jackson.annotation;

import java.lang.reflect.Member;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonAutoDetectTest_testFromAnnotation extends AnnotationTestUtil {

    private final static JsonAutoDetect.Value NO_OVERRIDES = JsonAutoDetect.Value.noOverrides();

    private final static JsonAutoDetect.Value DEFAULTS = JsonAutoDetect.Value.defaultVisibility();

    @Test
    public void testFromAnnotation() {
        JsonAutoDetect ann = Custom.class.getAnnotation(JsonAutoDetect.class);
        JsonAutoDetect.Value v = JsonAutoDetect.Value.from(ann);
        JsonAutoDetect.Value v2 = JsonAutoDetect.Value.from(ann);
        assertNotSame(v, v2);
        assertEquals(v, v2);
        assertEquals(v2, v);
        assertEquals(ann.fieldVisibility(), v.getFieldVisibility());
        assertEquals(ann.getterVisibility(), v.getGetterVisibility());
        assertEquals(ann.isGetterVisibility(), v.getIsGetterVisibility());
        assertEquals(ann.setterVisibility(), v.getSetterVisibility());
        assertEquals(ann.creatorVisibility(), v.getCreatorVisibility());
        // Let's also verify JDK serializability
        byte[] b = jdkSerialize(v);
        JsonAutoDetect.Value deser = jdkDeserialize(b);
        assertEquals(v, deser);
    }
}
