package com.fasterxml.jackson.annotation;

import java.lang.reflect.Member;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonAutoDetectTest
    extends AnnotationTestUtil
{
    private static final String DEFAULT_VISIBILITY_DESCRIPTION =
"JsonAutoDetect.Value(fields=PUBLIC_ONLY,getters=PUBLIC_ONLY,"+
"isGetters=PUBLIC_ONLY,setters=ANY,creators=PUBLIC_ONLY,scalarConstructors=NON_PRIVATE)";

    private static final String NO_OVERRIDES_DESCRIPTION =
"JsonAutoDetect.Value(fields=DEFAULT,getters=DEFAULT,"+
"isGetters=DEFAULT,setters=DEFAULT,creators=DEFAULT,scalarConstructors=DEFAULT)";

    private final static JsonAutoDetect.Value NO_OVERRIDES = JsonAutoDetect.Value.noOverrides();
    private final static JsonAutoDetect.Value DEFAULTS = JsonAutoDetect.Value.defaultVisibility();

    static class Bogus {
        public String value;
    }

    @JsonAutoDetect(fieldVisibility=Visibility.NON_PRIVATE,
            getterVisibility=Visibility.PROTECTED_AND_PUBLIC,
            isGetterVisibility=Visibility.NONE,
            setterVisibility=Visibility.PUBLIC_ONLY,
            creatorVisibility=Visibility.ANY)
    private final static class Custom { }

    @Test
    public void testAnnotationProperties() throws Exception
    {
        Member publicField = Bogus.class.getField("value");

        assertTrue(JsonAutoDetect.Visibility.ANY.isVisible(publicField));
        assertFalse(JsonAutoDetect.Visibility.NONE.isVisible(publicField));

        assertTrue(JsonAutoDetect.Visibility.NON_PRIVATE.isVisible(publicField));
        assertTrue(JsonAutoDetect.Visibility.PUBLIC_ONLY.isVisible(publicField));
        assertTrue(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC.isVisible(publicField));
        assertTrue(JsonAutoDetect.Visibility.NON_PRIVATE.isVisible(publicField));

        assertFalse(JsonAutoDetect.Visibility.DEFAULT.isVisible(publicField));
    }

    @Test
    public void testBasicValueProperties() {
        JsonAutoDetect.Value value = JsonAutoDetect.Value.DEFAULT;

        assertEquals(JsonAutoDetect.class, value.valueFor());

        int hashCode = value.hashCode();
        if (hashCode == 0) {
            fail();
        }

        assertTrue(value.equals(value));
        assertFalse(value.equals(null));
        assertFalse(value.equals("foo"));
    }

    @Test
    public void testEquality() {
        assertEquals(NO_OVERRIDES, NO_OVERRIDES);
        assertEquals(DEFAULTS, DEFAULTS);
        assertFalse(DEFAULTS.equals(NO_OVERRIDES));
        assertFalse(NO_OVERRIDES.equals(DEFAULTS));
    }

    @Test
    public void testFromAnnotation()
    {
        JsonAutoDetect annotation = Custom.class.getAnnotation(JsonAutoDetect.class);
        JsonAutoDetect.Value value = JsonAutoDetect.Value.from(annotation);
        JsonAutoDetect.Value sameSettings = JsonAutoDetect.Value.from(annotation);

        assertNotSame(value, sameSettings);
        assertEquals(value, sameSettings);
        assertEquals(sameSettings, value);

        assertEquals(annotation.fieldVisibility(), value.getFieldVisibility());
        assertEquals(annotation.getterVisibility(), value.getGetterVisibility());
        assertEquals(annotation.isGetterVisibility(), value.getIsGetterVisibility());
        assertEquals(annotation.setterVisibility(), value.getSetterVisibility());
        assertEquals(annotation.creatorVisibility(), value.getCreatorVisibility());

        byte[] serialized = jdkSerialize(value);
        JsonAutoDetect.Value deserialized = jdkDeserialize(serialized);

        assertEquals(value, deserialized);
    }

    @Test
    public void testToString() {
        assertEquals(DEFAULT_VISIBILITY_DESCRIPTION,
                JsonAutoDetect.Value.defaultVisibility().toString());
        assertEquals(NO_OVERRIDES_DESCRIPTION,
                JsonAutoDetect.Value.noOverrides().toString());
    }

    @Test
    public void testSimpleMerge() {
        JsonAutoDetect.Value base = JsonAutoDetect.Value.construct(
                Visibility.ANY,
                Visibility.PUBLIC_ONLY,
                Visibility.ANY,
                Visibility.NONE,
                Visibility.ANY,
                Visibility.PROTECTED_AND_PUBLIC);
        JsonAutoDetect.Value overrides = JsonAutoDetect.Value.construct(
                Visibility.NON_PRIVATE,
                Visibility.DEFAULT,
                Visibility.PUBLIC_ONLY,
                Visibility.DEFAULT,
                Visibility.DEFAULT,
                Visibility.PUBLIC_ONLY);

        JsonAutoDetect.Value merged = JsonAutoDetect.Value.merge(base, overrides);

        assertFalse(merged.equals(base));
        assertFalse(merged.equals(overrides));
        assertEquals(merged, merged);

        assertEquals(Visibility.NON_PRIVATE, merged.getFieldVisibility());
        assertEquals(Visibility.PUBLIC_ONLY, merged.getGetterVisibility());
        assertEquals(Visibility.PUBLIC_ONLY, merged.getIsGetterVisibility());
        assertEquals(Visibility.NONE, merged.getSetterVisibility());
        assertEquals(Visibility.ANY, merged.getCreatorVisibility());
        assertEquals(Visibility.PUBLIC_ONLY, merged.getScalarConstructorVisibility());

        merged = JsonAutoDetect.Value.merge(overrides, base);

        assertEquals(Visibility.ANY, merged.getFieldVisibility());
        assertEquals(Visibility.PUBLIC_ONLY, merged.getGetterVisibility());
        assertEquals(Visibility.ANY, merged.getIsGetterVisibility());
        assertEquals(Visibility.NONE, merged.getSetterVisibility());
        assertEquals(Visibility.ANY, merged.getCreatorVisibility());
        assertEquals(Visibility.PROTECTED_AND_PUBLIC, merged.getScalarConstructorVisibility());

        assertSame(overrides, JsonAutoDetect.Value.merge(null, overrides));
        assertSame(overrides, JsonAutoDetect.Value.merge(overrides, null));
    }

    @Test
    public void testFactoryMethods() {
        JsonAutoDetect.Value fieldOnly = JsonAutoDetect.Value.construct(PropertyAccessor.FIELD,
                Visibility.ANY);

        assertEquals(Visibility.ANY, fieldOnly.getFieldVisibility());
        assertEquals(Visibility.DEFAULT, fieldOnly.getGetterVisibility());
        assertEquals(Visibility.DEFAULT, fieldOnly.getIsGetterVisibility());
        assertEquals(Visibility.DEFAULT, fieldOnly.getSetterVisibility());
        assertEquals(Visibility.DEFAULT, fieldOnly.getCreatorVisibility());
        assertEquals(Visibility.DEFAULT, fieldOnly.getScalarConstructorVisibility());

        JsonAutoDetect.Value allAccessors = JsonAutoDetect.Value.construct(PropertyAccessor.ALL,
                Visibility.NONE);

        assertEquals(Visibility.NONE, allAccessors.getFieldVisibility());
        assertEquals(Visibility.NONE, allAccessors.getGetterVisibility());
        assertEquals(Visibility.NONE, allAccessors.getIsGetterVisibility());
        assertEquals(Visibility.NONE, allAccessors.getSetterVisibility());
        assertEquals(Visibility.NONE, allAccessors.getCreatorVisibility());
        assertEquals(Visibility.NONE, allAccessors.getScalarConstructorVisibility());
    }

    @Test
    public void testSimpleChanges() {
        assertSame(NO_OVERRIDES, NO_OVERRIDES.withFieldVisibility(Visibility.DEFAULT));

        JsonAutoDetect.Value changed = NO_OVERRIDES.withCreatorVisibility(Visibility.PUBLIC_ONLY);
        assertNotSame(NO_OVERRIDES, changed);
        assertEquals(Visibility.PUBLIC_ONLY, changed.getCreatorVisibility());

        changed = NO_OVERRIDES.withFieldVisibility(Visibility.ANY);
        assertEquals(Visibility.ANY, changed.getFieldVisibility());

        changed = NO_OVERRIDES.withGetterVisibility(Visibility.NON_PRIVATE);
        assertEquals(Visibility.NON_PRIVATE, changed.getGetterVisibility());

        changed = NO_OVERRIDES.withIsGetterVisibility(Visibility.PROTECTED_AND_PUBLIC);
        assertEquals(Visibility.PROTECTED_AND_PUBLIC, changed.getIsGetterVisibility());

        changed = NO_OVERRIDES.withSetterVisibility(Visibility.PUBLIC_ONLY);
        assertEquals(Visibility.PUBLIC_ONLY, changed.getSetterVisibility());

        changed = NO_OVERRIDES.withScalarConstructorVisibility(Visibility.PUBLIC_ONLY);
        assertEquals(Visibility.PUBLIC_ONLY, changed.getScalarConstructorVisibility());
    }
}
