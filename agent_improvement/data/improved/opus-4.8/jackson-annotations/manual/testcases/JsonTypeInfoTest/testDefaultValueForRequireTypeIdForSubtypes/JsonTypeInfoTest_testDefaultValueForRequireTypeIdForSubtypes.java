package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Verifies the default handling of the {@code requireTypeIdForSubtypes} property
 * on {@link JsonTypeInfo.Value}.
 *
 * <p>{@link AnnotatedWithoutRequireTypeId} is annotated with {@code @JsonTypeInfo}
 * but does NOT set {@code requireTypeIdForSubtypes}. When the annotation default
 * ({@link OptBoolean#DEFAULT}) is converted into a {@link JsonTypeInfo.Value},
 * that property is expected to become {@code null}.
 */
public class JsonTypeInfoTest_testDefaultValueForRequireTypeIdForSubtypes extends AnnotationTestUtil {

    /**
     * Sample type whose {@code @JsonTypeInfo} mirrors the original test's {@code Anno3}:
     * it omits {@code requireTypeIdForSubtypes}, so the default applies.
     */
    @JsonTypeInfo(use = Id.NAME, include = As.EXTERNAL_PROPERTY,
            property = "ext", defaultImpl = Void.class)
    private static final class AnnotatedWithoutRequireTypeId { }

    @Test
    public void testDefaultValueForRequireTypeIdForSubtypes() {
        // Build a Value from a type whose @JsonTypeInfo leaves requireTypeIdForSubtypes unset.
        JsonTypeInfo.Value typeInfoValue = JsonTypeInfo.Value.from(
                AnnotatedWithoutRequireTypeId.class.getAnnotation(JsonTypeInfo.class));

        // An unset requireTypeIdForSubtypes maps to null (no explicit override).
        assertNull(typeInfoValue.getRequireTypeIdForSubtypes());

        // toString() should reflect Anno3's configuration with requireTypeIdForSubtypes=null.
        String expectedToString = "JsonTypeInfo.Value("
                + "idType=NAME,"
                + "includeAs=EXTERNAL_PROPERTY,"
                + "propertyName=ext,"
                + "defaultImpl=java.lang.Void,"
                + "idVisible=false,"
                + "requireTypeIdForSubtypes=null,"
                + "writeTypeIdForDefaultImpl=null)";
        assertEquals(expectedToString, typeInfoValue.toString());
    }
}
