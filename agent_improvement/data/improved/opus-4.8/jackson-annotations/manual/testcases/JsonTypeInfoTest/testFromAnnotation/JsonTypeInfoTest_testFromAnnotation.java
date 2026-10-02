package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that {@link JsonTypeInfo.Value#from(JsonTypeInfo)} correctly translates a
 * {@code @JsonTypeInfo} annotation into a {@link JsonTypeInfo.Value}, covering:
 * <ul>
 *   <li>a minimally-configured annotation where most attributes fall back to their defaults ({@code Anno1}),</li>
 *   <li>a fully-customized annotation ({@code Anno2}),</li>
 *   <li>{@code equals} semantics between the two resulting values, their {@code toString} output,</li>
 *   <li>and JDK serialization round-tripping of a value.</li>
 * </ul>
 */
public class JsonTypeInfoTest_testFromAnnotation extends AnnotationTestUtil {

    /** Minimally-configured annotation: only {@code use}, {@code visible} and the require flag are set. */
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
            visible = true,
            requireTypeIdForSubtypes = OptBoolean.TRUE)
    private static class Anno1 { }

    /** Fully-customized annotation: every attribute is given an explicit, non-default value. */
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "ext",
            visible = false,
            defaultImpl = Void.class,
            requireTypeIdForSubtypes = OptBoolean.FALSE)
    private static class Anno2 { }

    @Test
    public void testFromAnnotation() throws Exception {
        // --- Anno1: relies on annotation-level defaults for inclusion type and property name ---
        JsonTypeInfo.Value fromAnno1 =
                JsonTypeInfo.Value.from(Anno1.class.getAnnotation(JsonTypeInfo.class));
        assertEquals(JsonTypeInfo.Id.CLASS, fromAnno1.getIdType());
        assertEquals(JsonTypeInfo.As.PROPERTY, fromAnno1.getInclusionType(), "default include from annotation definition");
        assertEquals("@class", fromAnno1.getPropertyName(), "default property name for Id.CLASS");
        assertTrue(fromAnno1.getIdVisible());
        assertNull(fromAnno1.getDefaultImpl());
        assertTrue(fromAnno1.getRequireTypeIdForSubtypes());

        // --- Anno2: every attribute is explicitly customized ---
        JsonTypeInfo.Value fromAnno2 =
                JsonTypeInfo.Value.from(Anno2.class.getAnnotation(JsonTypeInfo.class));
        assertEquals(JsonTypeInfo.Id.NAME, fromAnno2.getIdType());
        assertEquals(JsonTypeInfo.As.EXTERNAL_PROPERTY, fromAnno2.getInclusionType());
        assertEquals("ext", fromAnno2.getPropertyName());
        assertFalse(fromAnno2.getIdVisible());
        assertEquals(Void.class, fromAnno2.getDefaultImpl());
        assertFalse(fromAnno2.getRequireTypeIdForSubtypes());

        // --- equals: each value matches itself but not the other ---
        assertTrue(fromAnno1.equals(fromAnno1));
        assertTrue(fromAnno2.equals(fromAnno2));
        assertFalse(fromAnno1.equals(fromAnno2));
        assertFalse(fromAnno2.equals(fromAnno1));

        // --- toString: full, human-readable rendering of each value's attributes ---
        assertEquals(
                "JsonTypeInfo.Value(idType=CLASS,includeAs=PROPERTY,propertyName=@class,defaultImpl=NULL,"
                        + "idVisible=true,requireTypeIdForSubtypes=true,writeTypeIdForDefaultImpl=null)",
                fromAnno1.toString());
        assertEquals(
                "JsonTypeInfo.Value(idType=NAME,includeAs=EXTERNAL_PROPERTY,propertyName=ext,defaultImpl=java.lang.Void,"
                        + "idVisible=false,requireTypeIdForSubtypes=false,writeTypeIdForDefaultImpl=null)",
                fromAnno2.toString());

        // --- JDK serialization: a value survives a serialize/deserialize round-trip unchanged ---
        byte[] serialized = jdkSerialize(fromAnno1);
        JsonTypeInfo.Value deserialized = jdkDeserialize(serialized);
        assertEquals(fromAnno1, deserialized);
    }
}
