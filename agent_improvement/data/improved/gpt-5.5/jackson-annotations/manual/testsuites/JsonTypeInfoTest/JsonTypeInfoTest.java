package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTypeInfoTest
    extends AnnotationTestUtil
{
    private static final String CLASS_VALUE_DESCRIPTION =
            "JsonTypeInfo.Value(idType=CLASS,includeAs=PROPERTY,propertyName=@class,"
            + "defaultImpl=NULL,idVisible=true,requireTypeIdForSubtypes=true,"
            + "writeTypeIdForDefaultImpl=null)";

    private static final String EXTERNAL_PROPERTY_VALUE_DESCRIPTION =
            "JsonTypeInfo.Value(idType=NAME,includeAs=EXTERNAL_PROPERTY,propertyName=ext,"
            + "defaultImpl=java.lang.Void,idVisible=false,requireTypeIdForSubtypes=false,"
            + "writeTypeIdForDefaultImpl=null)";

    private static final String DEFAULT_REQUIRE_TYPE_ID_DESCRIPTION =
            "JsonTypeInfo.Value(idType=NAME,includeAs=EXTERNAL_PROPERTY,propertyName=ext,"
            + "defaultImpl=java.lang.Void,idVisible=false,requireTypeIdForSubtypes=null,"
            + "writeTypeIdForDefaultImpl=null)";

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, visible = true,
            defaultImpl = JsonTypeInfo.class, requireTypeIdForSubtypes = OptBoolean.TRUE)
    private final static class ClassIdWithVisibleTypeId { }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.EXTERNAL_PROPERTY,
            property = "ext",
            defaultImpl = Void.class, requireTypeIdForSubtypes = OptBoolean.FALSE)
    private final static class NamedExternalPropertyWithoutRequiredTypeId { }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.EXTERNAL_PROPERTY,
            property = "ext",
            defaultImpl = Void.class)
    private final static class NamedExternalPropertyWithDefaultRequirement { }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, visible = true,
            defaultImpl = Void.class,
            writeTypeIdForDefaultImpl = OptBoolean.FALSE)
    private final static class ClassIdWithoutDefaultImplTypeId { }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
            writeTypeIdForDefaultImpl = OptBoolean.TRUE)
    private final static class ClassIdWithDefaultImplTypeId { }

    @Test
    public void testEmpty() {
        // 07-Mar-2017, tatu: Important to distinguish "none" from 'empty' value...
        assertNull(JsonTypeInfo.Value.from(null));
    }

    @Test
    public void testFromAnnotation() throws Exception
    {
        JsonTypeInfo.Value classValue = typeInfoValueFrom(ClassIdWithVisibleTypeId.class);
        assertEquals(JsonTypeInfo.Id.CLASS, classValue.getIdType());
        // default from annotation definition
        assertEquals(JsonTypeInfo.As.PROPERTY, classValue.getInclusionType());
        // default from annotation definition
        assertEquals("@class", classValue.getPropertyName());
        assertTrue(classValue.getIdVisible());
        assertNull(classValue.getDefaultImpl());
        assertTrue(classValue.getRequireTypeIdForSubtypes());

        JsonTypeInfo.Value externalPropertyValue =
                typeInfoValueFrom(NamedExternalPropertyWithoutRequiredTypeId.class);
        assertEquals(JsonTypeInfo.Id.NAME, externalPropertyValue.getIdType());
        assertEquals(JsonTypeInfo.As.EXTERNAL_PROPERTY, externalPropertyValue.getInclusionType());
        assertEquals("ext", externalPropertyValue.getPropertyName());
        assertFalse(externalPropertyValue.getIdVisible());
        assertEquals(Void.class, externalPropertyValue.getDefaultImpl());
        assertFalse(externalPropertyValue.getRequireTypeIdForSubtypes());

        assertTrue(classValue.equals(classValue));
        assertTrue(externalPropertyValue.equals(externalPropertyValue));

        assertFalse(classValue.equals(externalPropertyValue));
        assertFalse(externalPropertyValue.equals(classValue));

        assertEquals(CLASS_VALUE_DESCRIPTION, classValue.toString());
        assertEquals(EXTERNAL_PROPERTY_VALUE_DESCRIPTION, externalPropertyValue.toString());

        // Let's also verify JDK serializability
        byte[] serializedValue = jdkSerialize(classValue);
        JsonTypeInfo.Value deserializedValue = jdkDeserialize(serializedValue);

        assertEquals(classValue, deserializedValue);
    }

    @Test
    public void testMutators() throws Exception
    {
        JsonTypeInfo.Value value = typeInfoValueFrom(ClassIdWithVisibleTypeId.class);
        assertEquals(JsonTypeInfo.Id.CLASS, value.getIdType());

        assertSame(value, value.withIdType(JsonTypeInfo.Id.CLASS));
        JsonTypeInfo.Value minimalClassValue = value.withIdType(JsonTypeInfo.Id.MINIMAL_CLASS);
        assertEquals(JsonTypeInfo.Id.MINIMAL_CLASS, minimalClassValue.getIdType());
        JsonTypeInfo.Value simpleNameValue = value.withIdType(JsonTypeInfo.Id.SIMPLE_NAME);
        assertEquals(JsonTypeInfo.Id.SIMPLE_NAME, simpleNameValue.getIdType());

        assertEquals(JsonTypeInfo.As.PROPERTY, value.getInclusionType());
        assertSame(value, value.withInclusionType(JsonTypeInfo.As.PROPERTY));
        JsonTypeInfo.Value externalPropertyValue =
                value.withInclusionType(JsonTypeInfo.As.EXTERNAL_PROPERTY);
        assertEquals(JsonTypeInfo.As.EXTERNAL_PROPERTY, externalPropertyValue.getInclusionType());

        assertSame(value, value.withDefaultImpl(null));
        JsonTypeInfo.Value stringDefaultValue = value.withDefaultImpl(String.class);
        assertEquals(String.class, stringDefaultValue.getDefaultImpl());

        assertSame(value, value.withIdVisible(true));
        assertFalse(value.withIdVisible(false).getIdVisible());

        assertEquals("foobar", value.withPropertyName("foobar").getPropertyName());
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
        // default value
        JsonTypeInfo.Value defaultRequirementValue =
                typeInfoValueFrom(NamedExternalPropertyWithDefaultRequirement.class);
        assertNull(defaultRequirementValue.getRequireTypeIdForSubtypes());

        // toString()
        assertEquals(DEFAULT_REQUIRE_TYPE_ID_DESCRIPTION, defaultRequirementValue.toString());
    }

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplFromAnnotation() {
        // writeTypeIdForDefaultImpl = FALSE
        JsonTypeInfo.Value disabledValue = typeInfoValueFrom(ClassIdWithoutDefaultImplTypeId.class);
        assertEquals(Boolean.FALSE, disabledValue.getWriteTypeIdForDefaultImpl());
        assertFalse(disabledValue.shouldWriteTypeIdForDefaultImpl());

        // writeTypeIdForDefaultImpl = TRUE
        JsonTypeInfo.Value enabledValue = typeInfoValueFrom(ClassIdWithDefaultImplTypeId.class);
        assertEquals(Boolean.TRUE, enabledValue.getWriteTypeIdForDefaultImpl());
        assertTrue(enabledValue.shouldWriteTypeIdForDefaultImpl());

        // writeTypeIdForDefaultImpl not set (DEFAULT -> null)
        JsonTypeInfo.Value defaultValue =
                typeInfoValueFrom(NamedExternalPropertyWithDefaultRequirement.class);
        assertNull(defaultValue.getWriteTypeIdForDefaultImpl());
        // default should be treated as "write" (true)
        assertTrue(defaultValue.shouldWriteTypeIdForDefaultImpl());
    }

    // [annotations#342]
    @Test
    public void testWithWriteTypeIdForDefaultImpl() {
        JsonTypeInfo.Value empty = JsonTypeInfo.Value.EMPTY;
        assertNull(empty.getWriteTypeIdForDefaultImpl());
        assertTrue(empty.shouldWriteTypeIdForDefaultImpl());

        // Mutate to FALSE
        JsonTypeInfo.Value writeDisabled = empty.withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        assertEquals(Boolean.FALSE, writeDisabled.getWriteTypeIdForDefaultImpl());
        assertFalse(writeDisabled.shouldWriteTypeIdForDefaultImpl());

        // Mutate to TRUE
        JsonTypeInfo.Value writeEnabled = empty.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        assertEquals(Boolean.TRUE, writeEnabled.getWriteTypeIdForDefaultImpl());
        assertTrue(writeEnabled.shouldWriteTypeIdForDefaultImpl());

        // Mutate back to null
        JsonTypeInfo.Value writeDefault = writeDisabled.withWriteTypeIdForDefaultImpl(null);
        assertNull(writeDefault.getWriteTypeIdForDefaultImpl());
        assertTrue(writeDefault.shouldWriteTypeIdForDefaultImpl());

        // Same value returns same instance
        assertSame(writeDisabled, writeDisabled.withWriteTypeIdForDefaultImpl(Boolean.FALSE));
        assertSame(writeEnabled, writeEnabled.withWriteTypeIdForDefaultImpl(Boolean.TRUE));
    }

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplEqualsAndHashCode() {
        JsonTypeInfo.Value enabledValue = JsonTypeInfo.Value.EMPTY
                .withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        JsonTypeInfo.Value sameEnabledValue = JsonTypeInfo.Value.EMPTY
                .withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        JsonTypeInfo.Value disabledValue = JsonTypeInfo.Value.EMPTY
                .withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        JsonTypeInfo.Value defaultValue = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(null);

        assertEquals(enabledValue, sameEnabledValue);
        assertEquals(enabledValue.hashCode(), sameEnabledValue.hashCode());

        assertNotEquals(enabledValue, disabledValue);
        assertNotEquals(enabledValue, defaultValue);
        assertNotEquals(disabledValue, defaultValue);
    }

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplToString() {
        JsonTypeInfo.Value disabledValue = JsonTypeInfo.Value.EMPTY
                .withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        assertTrue(disabledValue.toString().contains("writeTypeIdForDefaultImpl=false"));

        JsonTypeInfo.Value enabledValue = JsonTypeInfo.Value.EMPTY
                .withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        assertTrue(enabledValue.toString().contains("writeTypeIdForDefaultImpl=true"));
    }

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplConstruct() {
        JsonTypeInfo.Value value = JsonTypeInfo.Value.construct(
                JsonTypeInfo.Id.CLASS, JsonTypeInfo.As.PROPERTY,
                null, Void.class, false, null, Boolean.FALSE);
        assertEquals(Boolean.FALSE, value.getWriteTypeIdForDefaultImpl());
        assertFalse(value.shouldWriteTypeIdForDefaultImpl());
    }

    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplSerialization() throws Exception {
        JsonTypeInfo.Value disabledValue = JsonTypeInfo.Value.EMPTY
                .withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        byte[] serializedValue = jdkSerialize(disabledValue);
        JsonTypeInfo.Value deserializedValue = jdkDeserialize(serializedValue);
        assertEquals(disabledValue, deserializedValue);
        assertEquals(Boolean.FALSE, deserializedValue.getWriteTypeIdForDefaultImpl());
    }

    private JsonTypeInfo.Value typeInfoValueFrom(Class<?> annotatedType) {
        return JsonTypeInfo.Value.from(annotatedType.getAnnotation(JsonTypeInfo.class));
    }
}
