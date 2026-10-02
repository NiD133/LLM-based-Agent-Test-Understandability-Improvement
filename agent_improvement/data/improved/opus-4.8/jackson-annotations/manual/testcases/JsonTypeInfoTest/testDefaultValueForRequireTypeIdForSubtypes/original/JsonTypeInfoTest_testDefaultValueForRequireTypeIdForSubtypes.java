package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTypeInfoTest_testDefaultValueForRequireTypeIdForSubtypes extends AnnotationTestUtil {

    @Test
    public void testDefaultValueForRequireTypeIdForSubtypes() {
        // default value
        JsonTypeInfo.Value v3 = JsonTypeInfo.Value.from(Anno3.class.getAnnotation(JsonTypeInfo.class));
        assertNull(v3.getRequireTypeIdForSubtypes());
        // toString()
        assertEquals("JsonTypeInfo.Value(idType=NAME,includeAs=EXTERNAL_PROPERTY,propertyName=ext," + "defaultImpl=java.lang.Void,idVisible=false,requireTypeIdForSubtypes=null,writeTypeIdForDefaultImpl=null)", v3.toString());
    }
}
