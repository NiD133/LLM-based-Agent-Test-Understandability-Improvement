package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that omitting {@code requireTypeIdForSubtypes} in a {@link JsonTypeInfo}
 * annotation results in a {@code null} value (i.e. the {@link OptBoolean#DEFAULT}
 * sentinel maps to {@code null} via {@link OptBoolean#asBoolean()}).
 */
public class JsonTypeInfoTest_testDefaultValueForRequireTypeIdForSubtypes extends AnnotationTestUtil {

    /**
     * Annotation target with NAME-based type id, EXTERNAL_PROPERTY inclusion,
     * and Void as the default implementation — but {@code requireTypeIdForSubtypes}
     * is intentionally left at its default ({@link OptBoolean#DEFAULT}).
     */
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = As.EXTERNAL_PROPERTY,
            property = "ext",
            defaultImpl = Void.class
            // requireTypeIdForSubtypes not set — defaults to OptBoolean.DEFAULT
    )
    private static final class TypeWithDefaultRequireTypeId { }

    @Test
    public void testDefaultValueForRequireTypeIdForSubtypes() {
        // Build a Value from the annotation whose requireTypeIdForSubtypes is unset.
        // OptBoolean.DEFAULT.asBoolean() returns null, so the Value field must be null.
        JsonTypeInfo.Value value = JsonTypeInfo.Value.from(
                TypeWithDefaultRequireTypeId.class.getAnnotation(JsonTypeInfo.class));

        assertNull(value.getRequireTypeIdForSubtypes(),
                "requireTypeIdForSubtypes should be null when the annotation attribute is not set (OptBoolean.DEFAULT -> null)");

        // Verify the full toString() to confirm every field is rendered correctly,
        // including the two nullable trailing fields (requireTypeIdForSubtypes and
        // writeTypeIdForDefaultImpl) both appearing as "null".
        String expectedToString =
                "JsonTypeInfo.Value(idType=NAME,includeAs=EXTERNAL_PROPERTY,propertyName=ext,"
                + "defaultImpl=java.lang.Void,idVisible=false,"
                + "requireTypeIdForSubtypes=null,writeTypeIdForDefaultImpl=null)";
        assertEquals(expectedToString, value.toString());
    }
}
