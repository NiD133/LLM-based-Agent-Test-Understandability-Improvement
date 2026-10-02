package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTypeInfoTest_testFromAnnotation extends AnnotationTestUtil {

    private static final String ANNO1_VALUE_DESCRIPTION =
            "JsonTypeInfo.Value(idType=CLASS,includeAs=PROPERTY,propertyName=@class,"
                    + "defaultImpl=NULL,idVisible=true,requireTypeIdForSubtypes=true,"
                    + "writeTypeIdForDefaultImpl=null)";

    private static final String ANNO2_VALUE_DESCRIPTION =
            "JsonTypeInfo.Value(idType=NAME,includeAs=EXTERNAL_PROPERTY,propertyName=ext,"
                    + "defaultImpl=java.lang.Void,idVisible=false,requireTypeIdForSubtypes=false,"
                    + "writeTypeIdForDefaultImpl=null)";

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, visible = true,
            requireTypeIdForSubtypes = OptBoolean.TRUE)
    private static class Anno1 { }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "ext", defaultImpl = Void.class,
            requireTypeIdForSubtypes = OptBoolean.FALSE)
    private static class Anno2 { }

    @Test
    public void testFromAnnotation() throws Exception {
        JsonTypeInfo.Value classIdDefaults = JsonTypeInfo.Value.from(
                Anno1.class.getAnnotation(JsonTypeInfo.class));

        assertEquals(JsonTypeInfo.Id.CLASS, classIdDefaults.getIdType());
        // default from annotation definition
        assertEquals(JsonTypeInfo.As.PROPERTY, classIdDefaults.getInclusionType());
        // default from annotation definition
        assertEquals("@class", classIdDefaults.getPropertyName());
        assertTrue(classIdDefaults.getIdVisible());
        assertNull(classIdDefaults.getDefaultImpl());
        assertTrue(classIdDefaults.getRequireTypeIdForSubtypes());

        JsonTypeInfo.Value nameIdExternalProperty = JsonTypeInfo.Value.from(
                Anno2.class.getAnnotation(JsonTypeInfo.class));

        assertEquals(JsonTypeInfo.Id.NAME, nameIdExternalProperty.getIdType());
        assertEquals(JsonTypeInfo.As.EXTERNAL_PROPERTY, nameIdExternalProperty.getInclusionType());
        assertEquals("ext", nameIdExternalProperty.getPropertyName());
        assertFalse(nameIdExternalProperty.getIdVisible());
        assertEquals(Void.class, nameIdExternalProperty.getDefaultImpl());
        assertFalse(nameIdExternalProperty.getRequireTypeIdForSubtypes());

        assertTrue(classIdDefaults.equals(classIdDefaults));
        assertTrue(nameIdExternalProperty.equals(nameIdExternalProperty));
        assertFalse(classIdDefaults.equals(nameIdExternalProperty));
        assertFalse(nameIdExternalProperty.equals(classIdDefaults));
        assertEquals(ANNO1_VALUE_DESCRIPTION, classIdDefaults.toString());
        assertEquals(ANNO2_VALUE_DESCRIPTION, nameIdExternalProperty.toString());

        // Let's also verify JDK serializability
        byte[] serialized = jdkSerialize(classIdDefaults);
        JsonTypeInfo.Value deserialized = jdkDeserialize(serialized);
        assertEquals(classIdDefaults, deserialized);
    }
}
