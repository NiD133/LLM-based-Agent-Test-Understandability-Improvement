package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTypeInfoTest_testFromAnnotation extends AnnotationTestUtil {

    // CLASS id type: visible=true, defaultImpl=JsonTypeInfo.class (annotation type -> treated as null),
    // requireTypeIdForSubtypes=TRUE; include and property use their defaults (PROPERTY / "@class")
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, visible = true,
            defaultImpl = JsonTypeInfo.class, requireTypeIdForSubtypes = OptBoolean.TRUE)
    private static final class Anno1 { }

    // NAME id type: EXTERNAL_PROPERTY inclusion, explicit property="ext",
    // defaultImpl=Void.class, requireTypeIdForSubtypes=FALSE
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "ext",
            defaultImpl = Void.class, requireTypeIdForSubtypes = OptBoolean.FALSE)
    private static final class Anno2 { }

    @Test
    public void testFromAnnotation() throws Exception {

        // --- Anno1: CLASS id, default PROPERTY inclusion, visible, no defaultImpl ---
        JsonTypeInfo.Value v1 = JsonTypeInfo.Value.from(Anno1.class.getAnnotation(JsonTypeInfo.class));
        assertEquals(JsonTypeInfo.Id.CLASS, v1.getIdType());
        assertEquals(JsonTypeInfo.As.PROPERTY, v1.getInclusionType()); // default from annotation definition
        assertEquals("@class", v1.getPropertyName());                   // default from annotation definition
        assertTrue(v1.getIdVisible());
        assertNull(v1.getDefaultImpl());                                // annotation type defaultImpl maps to null
        assertTrue(v1.getRequireTypeIdForSubtypes());

        // --- Anno2: NAME id, EXTERNAL_PROPERTY inclusion, explicit property, Void defaultImpl ---
        JsonTypeInfo.Value v2 = JsonTypeInfo.Value.from(Anno2.class.getAnnotation(JsonTypeInfo.class));
        assertEquals(JsonTypeInfo.Id.NAME, v2.getIdType());
        assertEquals(JsonTypeInfo.As.EXTERNAL_PROPERTY, v2.getInclusionType());
        assertEquals("ext", v2.getPropertyName());
        assertFalse(v2.getIdVisible());
        assertEquals(Void.class, v2.getDefaultImpl());
        assertFalse(v2.getRequireTypeIdForSubtypes());

        // --- Equality: reflexive and asymmetric checks ---
        assertTrue(v1.equals(v1));
        assertTrue(v2.equals(v2));
        assertFalse(v1.equals(v2));
        assertFalse(v2.equals(v1));

        // --- toString: full string representation of each Value ---
        assertEquals(
                "JsonTypeInfo.Value(idType=CLASS,includeAs=PROPERTY,propertyName=@class,"
                + "defaultImpl=NULL,idVisible=true,requireTypeIdForSubtypes=true,"
                + "writeTypeIdForDefaultImpl=null)",
                v1.toString());
        assertEquals(
                "JsonTypeInfo.Value(idType=NAME,includeAs=EXTERNAL_PROPERTY,propertyName=ext,"
                + "defaultImpl=java.lang.Void,idVisible=false,requireTypeIdForSubtypes=false,"
                + "writeTypeIdForDefaultImpl=null)",
                v2.toString());

        // --- JDK serialization round-trip for v1 ---
        byte[] serialized = jdkSerialize(v1);
        JsonTypeInfo.Value deserialized = jdkDeserialize(serialized);
        assertEquals(v1, deserialized);
    }
}
