package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTypeInfoTest_testFromAnnotation extends AnnotationTestUtil {

    @Test
    public void testFromAnnotation() throws Exception {
        JsonTypeInfo.Value v1 = JsonTypeInfo.Value.from(Anno1.class.getAnnotation(JsonTypeInfo.class));
        assertEquals(JsonTypeInfo.Id.CLASS, v1.getIdType());
        // default from annotation definition
        assertEquals(JsonTypeInfo.As.PROPERTY, v1.getInclusionType());
        // default from annotation definition
        assertEquals("@class", v1.getPropertyName());
        assertTrue(v1.getIdVisible());
        assertNull(v1.getDefaultImpl());
        assertTrue(v1.getRequireTypeIdForSubtypes());
        JsonTypeInfo.Value v2 = JsonTypeInfo.Value.from(Anno2.class.getAnnotation(JsonTypeInfo.class));
        assertEquals(JsonTypeInfo.Id.NAME, v2.getIdType());
        assertEquals(JsonTypeInfo.As.EXTERNAL_PROPERTY, v2.getInclusionType());
        assertEquals("ext", v2.getPropertyName());
        assertFalse(v2.getIdVisible());
        assertEquals(Void.class, v2.getDefaultImpl());
        assertFalse(v2.getRequireTypeIdForSubtypes());
        assertTrue(v1.equals(v1));
        assertTrue(v2.equals(v2));
        assertFalse(v1.equals(v2));
        assertFalse(v2.equals(v1));
        assertEquals("JsonTypeInfo.Value(idType=CLASS,includeAs=PROPERTY,propertyName=@class,defaultImpl=NULL,idVisible=true,requireTypeIdForSubtypes=true,writeTypeIdForDefaultImpl=null)", v1.toString());
        assertEquals("JsonTypeInfo.Value(idType=NAME,includeAs=EXTERNAL_PROPERTY,propertyName=ext,defaultImpl=java.lang.Void,idVisible=false,requireTypeIdForSubtypes=false,writeTypeIdForDefaultImpl=null)", v2.toString());
        // Let's also verify JDK serializability
        byte[] b = jdkSerialize(v1);
        JsonTypeInfo.Value deser = jdkDeserialize(b);
        assertEquals(v1, deser);
    }
}
