package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JsonTypeInfoTest_testDefaultValueForRequireTypeIdForSubtypes
        extends AnnotationTestUtil {

    private static final String EXPECTED_DEFAULT_VALUE_DESCRIPTION =
            "JsonTypeInfo.Value(idType=NAME,includeAs=EXTERNAL_PROPERTY,propertyName=ext,"
                    + "defaultImpl=java.lang.Void,idVisible=false,requireTypeIdForSubtypes=null,"
                    + "writeTypeIdForDefaultImpl=null)";

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "ext",
            defaultImpl = Void.class)
    private static class Anno3 { }

    @Test
    public void testDefaultValueForRequireTypeIdForSubtypes() {
        JsonTypeInfo.Value value = JsonTypeInfo.Value.from(
                Anno3.class.getAnnotation(JsonTypeInfo.class));

        assertNull(value.getRequireTypeIdForSubtypes());
        assertEquals(EXPECTED_DEFAULT_VALUE_DESCRIPTION, value.toString());
    }
}
