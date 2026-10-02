package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTypeInfoTest
    extends AnnotationTestUtil
{
    // CLASS id, type id visible to deserializer, explicit "no default" sentinel, type id required for subtypes
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, visible = true,
            defaultImpl = JsonTypeInfo.class, requireTypeIdForSubtypes = OptBoolean.TRUE)
    private final static class ClassIdWithRequireTypeId { }

    // NAME id, external-property inclusion, Void default impl, type id NOT required for subtypes
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.EXTERNAL_PROPERTY,
            property = "ext",
            defaultImpl = Void.class, requireTypeIdForSubtypes = OptBoolean.FALSE)
    private final static class NameIdExternalPropRequireTypeIdFalse { }

    // NAME id, external-property inclusion, Void default impl; requireTypeIdForSubtypes left at DEFAULT
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.EXTERNAL_PROPERTY,
            property = "ext",
            defaultImpl = Void.class)
    private final static class NameIdExternalPropNoRequireTypeId { }

    // CLASS id, type id visible, Void default impl; writeTypeIdForDefaultImpl explicitly disabled
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, visible = true,
            defaultImpl = Void.class,
            writeTypeIdForDefaultImpl = OptBoolean.FALSE)
    private final static class ClassIdWriteTypeIdFalse { }

    // CLASS id; writeTypeIdForDefaultImpl explicitly enabled
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
            writeTypeIdForDefaultImpl = OptBoolean.TRUE)
    private final static class ClassIdWriteTypeIdTrue { }

    @Test
    public void testEmpty() {
        // "none" (null annotation) must not be confused with an empty Value
        assertNull(JsonTypeInfo.Value.from(null));
    }

    @Test
    public void testFromAnnotation() throws Exception
    {
        // --- CLASS id, visible, requireTypeIdForSubtypes=true ---
        JsonTypeInfo.Value classIdValue = JsonTypeInfo.Value.from(
                ClassIdWithRequireTypeId.class.getAnnotation(JsonTypeInfo.class));
        assertEquals(JsonTypeInfo.Id.CLASS, classIdValue.getIdType());
        assertEquals(JsonTypeInfo.As.PROPERTY, classIdValue.getInclusionType()); // annotation default
        assertEquals("@class", classIdValue.getPropertyName());                  // annotation default
        assertTrue(classIdValue.getIdVisible());
        assertNull(classIdValue.getDefaultImpl());
        assertTrue(classIdValue.getRequireTypeIdForSubtypes());

        // --- NAME id, EXTERNAL_PROPERTY, property="ext", requireTypeIdForSubtypes=false ---
        JsonTypeInfo.Value nameIdExtPropValue = JsonTypeInfo.Value.from(
                NameIdExternalPropRequireTypeIdFalse.class.getAnnotation(JsonTypeInfo.class));
        assertEquals(JsonTypeInfo.Id.NAME, nameIdExtPropValue.getIdType());
        assertEquals(JsonTypeInfo.As.EXTERNAL_PROPERTY, nameIdExtPropValue.getInclusionType());
        assertEquals("ext", nameIdExtPropValue.getPropertyName());
        assertFalse(nameIdExtPropValue.getIdVisible());
        assertEquals(Void.class, nameIdExtPropValue.getDefaultImpl());
        assertFalse(nameIdExtPropValue.getRequireTypeIdForSubtypes());

        // Reflexive equality; distinct configs are not equal
        assertTrue(classIdValue.equals(classIdValue));
        assertTrue(nameIdExtPropValue.equals(nameIdExtPropValue));
        assertFalse(classIdValue.equals(nameIdExtPropValue));
        assertFalse(nameIdExtPropValue.equals(classIdValue));

        // Full toString representation
        assertEquals("JsonTypeInfo.Value(idType=CLASS,includeAs=PROPERTY,propertyName=@class,defaultImpl=NULL,idVisible=true,requireTypeIdForSubtypes=true,writeTypeIdForDefaultImpl=null)",
                classIdValue.toString());
        assertEquals("JsonTypeInfo.Value(idType=NAME,includeAs=EXTERNAL_PROPERTY,propertyName=ext,defaultImpl=java.lang.Void,idVisible=false,requireTypeIdForSubtypes=false,writeTypeIdForDefaultImpl=null)",
                nameIdExtPropValue.toString());

        // JDK serialization round-trip
        byte[] serialized = jdkSerialize(classIdValue);
        JsonTypeInfo.Value deserialized = jdkDeserialize(serialized);
        assertEquals(classIdValue, deserialized);
    }

    @Test
    public void testMutators() throws Exception
    {
        JsonTypeInfo.Value base = JsonTypeInfo.Value.from(
                ClassIdWithRequireTypeId.class.getAnnotation(JsonTypeInfo.class));
        assertEquals(JsonTypeInfo.Id.CLASS, base.getIdType());

        // withIdType: same enum constant returns same instance; different constant creates new instance
        assertSame(base, base.withIdType(JsonTypeInfo.Id.CLASS));
        assertEquals(JsonTypeInfo.Id.MINIMAL_CLASS, base.withIdType(JsonTypeInfo.Id.MINIMAL_CLASS).getIdType());
        assertEquals(JsonTypeInfo.Id.SIMPLE_NAME, base.withIdType(JsonTypeInfo.Id.SIMPLE_NAME).getIdType());

        // withInclusionType: same value returns same instance; different value creates new instance
        assertEquals(JsonTypeInfo.As.PROPERTY, base.getInclusionType());
        assertSame(base, base.withInclusionType(JsonTypeInfo.As.PROPERTY));
        assertEquals(JsonTypeInfo.As.EXTERNAL_PROPERTY,
                base.withInclusionType(JsonTypeInfo.As.EXTERNAL_PROPERTY).getInclusionType());

        // withDefaultImpl: null→null is a no-op; non-null value is reflected in new instance
        assertSame(base, base.withDefaultImpl(null));
        assertEquals(String.class, base.withDefaultImpl(String.class).getDefaultImpl());

        // withIdVisible: same boolean is a no-op; toggling creates a new instance
        assertSame(base, base.withIdVisible(true));
        assertFalse(base.withIdVisible(false).getIdVisible());

        // withPropertyName: new name is reflected in the returned instance
        assertEquals("foobar", base.withPropertyName("foobar").getPropertyName());
    }

    @Test
    public void testWithRequireTypeIdForSubtypes() {
        JsonTypeInfo.Value empty = JsonTypeInfo.Value.EMPTY;
        assertNull(empty.getRequireTypeIdForSubtypes());

        JsonTypeInfo.Value requireTypeIdTrue = empty.withRequireTypeIdForSubtypes(Boolean.TRUE);
        assertEquals(Boolean.TRUE, requireTypeIdTrue.getRequireTypeIdForSubtypes());

        JsonTypeInfo.Value requireTypeIdFalse = empty.withRequireTypeIdForSubtypes(Boolean.FALSE);
        assertEquals(Boolean.FALSE, requireTypeIdFalse.getRequireTypeIdForSubtypes());

        JsonTypeInfo.Value requireTypeIdDefault = empty.withRequireTypeIdForSubtypes(null);
        assertNull(requireTypeIdDefault.getRequireTypeIdForSubtypes());
    }

    @Test
    public void testDefaultValueForRequireTypeIdForSubtypes() {
        // When requireTypeIdForSubtypes is omitted from the annotation, the parsed Value must hold null (DEFAULT)
        JsonTypeInfo.Value noRequireTypeId = JsonTypeInfo.Value.from(
                NameIdExternalPropNoRequireTypeId.class.getAnnotation(JsonTypeInfo.class));
        assertNull(noRequireTypeId.getRequireTypeIdForSubtypes());

        assertEquals("JsonTypeInfo.Value(idType=NAME,includeAs=EXTERNAL_PROPERTY,propertyName=ext,"
                + "defaultImpl=java.lang.Void,idVisible=false,requireTypeIdForSubtypes=null,writeTypeIdForDefaultImpl=null)",
                noRequireTypeId.toString());
    }

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplFromAnnotation() {
        // writeTypeIdForDefaultImpl = FALSE: flag is false and shouldWrite returns false
        JsonTypeInfo.Value writeTypeIdFalse = JsonTypeInfo.Value.from(
                ClassIdWriteTypeIdFalse.class.getAnnotation(JsonTypeInfo.class));
        assertEquals(Boolean.FALSE, writeTypeIdFalse.getWriteTypeIdForDefaultImpl());
        assertFalse(writeTypeIdFalse.shouldWriteTypeIdForDefaultImpl());

        // writeTypeIdForDefaultImpl = TRUE: flag is true and shouldWrite returns true
        JsonTypeInfo.Value writeTypeIdTrue = JsonTypeInfo.Value.from(
                ClassIdWriteTypeIdTrue.class.getAnnotation(JsonTypeInfo.class));
        assertEquals(Boolean.TRUE, writeTypeIdTrue.getWriteTypeIdForDefaultImpl());
        assertTrue(writeTypeIdTrue.shouldWriteTypeIdForDefaultImpl());

        // writeTypeIdForDefaultImpl not set (DEFAULT -> null): shouldWrite defaults to true for backwards compat
        JsonTypeInfo.Value writeTypeIdDefault = JsonTypeInfo.Value.from(
                NameIdExternalPropNoRequireTypeId.class.getAnnotation(JsonTypeInfo.class));
        assertNull(writeTypeIdDefault.getWriteTypeIdForDefaultImpl());
        assertTrue(writeTypeIdDefault.shouldWriteTypeIdForDefaultImpl());
    }

    // [annotations#342]
    @Test
    public void testWithWriteTypeIdForDefaultImpl() {
        JsonTypeInfo.Value empty = JsonTypeInfo.Value.EMPTY;
        assertNull(empty.getWriteTypeIdForDefaultImpl());
        assertTrue(empty.shouldWriteTypeIdForDefaultImpl());

        // Mutate to FALSE
        JsonTypeInfo.Value vFalse = empty.withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        assertEquals(Boolean.FALSE, vFalse.getWriteTypeIdForDefaultImpl());
        assertFalse(vFalse.shouldWriteTypeIdForDefaultImpl());

        // Mutate to TRUE
        JsonTypeInfo.Value vTrue = empty.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        assertEquals(Boolean.TRUE, vTrue.getWriteTypeIdForDefaultImpl());
        assertTrue(vTrue.shouldWriteTypeIdForDefaultImpl());

        // Mutate back to null
        JsonTypeInfo.Value vNull = vFalse.withWriteTypeIdForDefaultImpl(null);
        assertNull(vNull.getWriteTypeIdForDefaultImpl());
        assertTrue(vNull.shouldWriteTypeIdForDefaultImpl());

        // Same value returns same instance
        assertSame(vFalse, vFalse.withWriteTypeIdForDefaultImpl(Boolean.FALSE));
        assertSame(vTrue, vTrue.withWriteTypeIdForDefaultImpl(Boolean.TRUE));
    }

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplEqualsAndHashCode() {
        JsonTypeInfo.Value v1 = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        JsonTypeInfo.Value v2 = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        JsonTypeInfo.Value v3 = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        JsonTypeInfo.Value vNull = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(null);

        assertEquals(v1, v2);
        assertEquals(v1.hashCode(), v2.hashCode());

        assertNotEquals(v1, v3);
        assertNotEquals(v1, vNull);
        assertNotEquals(v3, vNull);
    }

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplToString() {
        JsonTypeInfo.Value vFalse = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        assertTrue(vFalse.toString().contains("writeTypeIdForDefaultImpl=false"));

        JsonTypeInfo.Value vTrue = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        assertTrue(vTrue.toString().contains("writeTypeIdForDefaultImpl=true"));
    }

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplConstruct() {
        JsonTypeInfo.Value v = JsonTypeInfo.Value.construct(
                JsonTypeInfo.Id.CLASS, JsonTypeInfo.As.PROPERTY,
                null, Void.class, false, null, Boolean.FALSE);
        assertEquals(Boolean.FALSE, v.getWriteTypeIdForDefaultImpl());
        assertFalse(v.shouldWriteTypeIdForDefaultImpl());
    }

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplSerialization() throws Exception {
        JsonTypeInfo.Value original = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        byte[] serialized = jdkSerialize(original);
        JsonTypeInfo.Value deserialized = jdkDeserialize(serialized);
        assertEquals(original, deserialized);
        assertEquals(Boolean.FALSE, deserialized.getWriteTypeIdForDefaultImpl());
    }
}
