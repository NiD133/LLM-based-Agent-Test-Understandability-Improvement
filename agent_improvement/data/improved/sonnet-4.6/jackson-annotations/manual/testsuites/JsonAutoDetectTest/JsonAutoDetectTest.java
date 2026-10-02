package com.fasterxml.jackson.annotation;

import java.lang.reflect.Member;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JsonAutoDetect.Visibility} and {@link JsonAutoDetect.Value}:
 * visibility thresholds, value construction, merging, and mutation.
 */
public class JsonAutoDetectTest
    extends AnnotationTestUtil
{
    // A class with a public field, used to verify Visibility.isVisible() against a known-public member
    static class Bogus {
        public String value;
    }

    // Custom annotation configuration used in testFromAnnotation()
    @JsonAutoDetect(fieldVisibility=Visibility.NON_PRIVATE,
            getterVisibility=Visibility.PROTECTED_AND_PUBLIC,
            isGetterVisibility=Visibility.NONE,
            setterVisibility=Visibility.PUBLIC_ONLY,
            creatorVisibility=Visibility.ANY)
    private final static class Custom { }

    private final static JsonAutoDetect.Value NO_OVERRIDES = JsonAutoDetect.Value.noOverrides();
    private final static JsonAutoDetect.Value DEFAULTS = JsonAutoDetect.Value.defaultVisibility();

    /**
     * Verifies that {@link Visibility#isVisible} correctly classifies a public field
     * according to each visibility level.
     */
    @Test
    public void testAnnotationProperties() throws Exception
    {
        Member publicField = Bogus.class.getField("value");

        assertTrue(JsonAutoDetect.Visibility.ANY.isVisible(publicField));
        assertFalse(JsonAutoDetect.Visibility.NONE.isVisible(publicField));

        assertTrue(JsonAutoDetect.Visibility.NON_PRIVATE.isVisible(publicField));
        assertTrue(JsonAutoDetect.Visibility.PUBLIC_ONLY.isVisible(publicField));
        assertTrue(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC.isVisible(publicField));

        // DEFAULT defers to the context default rather than inspecting the modifier, so it returns false
        assertFalse(JsonAutoDetect.Visibility.DEFAULT.isVisible(publicField));
    }

    /**
     * Verifies basic contract of {@link JsonAutoDetect.Value}: correct annotation class,
     * non-zero hash code, and well-behaved equals (reflexive, handles null and wrong type).
     */
    @Test
    public void testBasicValueProperties() {
        JsonAutoDetect.Value v = JsonAutoDetect.Value.DEFAULT;
        assertEquals(JsonAutoDetect.class, v.valueFor());

        int hashCode = v.hashCode();
        assertNotEquals(0, hashCode, "hashCode should not be zero");

        assertTrue(v.equals(v));
        // Verify equals does not throw on null or an incompatible type
        assertFalse(v.equals(null));
        assertFalse(v.equals("foo"));
    }

    /**
     * Verifies that two distinct Value instances (noOverrides vs defaultVisibility)
     * are equal only to themselves, not to each other.
     */
    @Test
    public void testEquality() {
        assertEquals(NO_OVERRIDES, NO_OVERRIDES);
        assertEquals(DEFAULTS, DEFAULTS);
        assertFalse(DEFAULTS.equals(NO_OVERRIDES));
        assertFalse(NO_OVERRIDES.equals(DEFAULTS));
    }

    /**
     * Verifies that {@link JsonAutoDetect.Value#from} produces a Value whose visibility
     * settings match the annotation, and that the Value survives JDK serialization round-trip.
     */
    @Test
    public void testFromAnnotation()
    {
        JsonAutoDetect ann = Custom.class.getAnnotation(JsonAutoDetect.class);
        JsonAutoDetect.Value v  = JsonAutoDetect.Value.from(ann);
        JsonAutoDetect.Value v2 = JsonAutoDetect.Value.from(ann);
        // Two calls return distinct but equal instances
        assertNotSame(v, v2);
        assertEquals(v, v2);
        assertEquals(v2, v);

        // Each visibility setting in the Value must match the annotation attribute
        assertEquals(ann.fieldVisibility(),    v.getFieldVisibility());
        assertEquals(ann.getterVisibility(),   v.getGetterVisibility());
        assertEquals(ann.isGetterVisibility(), v.getIsGetterVisibility());
        assertEquals(ann.setterVisibility(),   v.getSetterVisibility());
        assertEquals(ann.creatorVisibility(),  v.getCreatorVisibility());

        // JDK serialization round-trip must preserve equality
        byte[] serialized = jdkSerialize(v);
        JsonAutoDetect.Value deserialized = jdkDeserialize(serialized);
        assertEquals(v, deserialized);
    }

    /**
     * Verifies the toString() format for the default and no-override Value instances.
     */
    @Test
    public void testToString() {
        assertEquals(
            "JsonAutoDetect.Value(fields=PUBLIC_ONLY,getters=PUBLIC_ONLY," +
            "isGetters=PUBLIC_ONLY,setters=ANY,creators=PUBLIC_ONLY,scalarConstructors=NON_PRIVATE)",
            JsonAutoDetect.Value.defaultVisibility().toString());
        assertEquals(
            "JsonAutoDetect.Value(fields=DEFAULT,getters=DEFAULT," +
            "isGetters=DEFAULT,setters=DEFAULT,creators=DEFAULT,scalarConstructors=DEFAULT)",
            JsonAutoDetect.Value.noOverrides().toString());
    }

    /**
     * Verifies that {@link JsonAutoDetect.Value#merge} applies non-DEFAULT override values
     * on top of a base, in both merge orderings, and handles null operands correctly.
     */
    @Test
    public void testSimpleMerge() {
        JsonAutoDetect.Value base = JsonAutoDetect.Value.construct(
                Visibility.ANY,                  // fields
                Visibility.PUBLIC_ONLY,          // getters
                Visibility.ANY,                  // isGetters
                Visibility.NONE,                 // setters
                Visibility.ANY,                  // creators
                Visibility.PROTECTED_AND_PUBLIC); // scalarConstructors
        JsonAutoDetect.Value overrides = JsonAutoDetect.Value.construct(
                Visibility.NON_PRIVATE,          // fields    — overrides base
                Visibility.DEFAULT,              // getters   — DEFAULT means "keep base"
                Visibility.PUBLIC_ONLY,          // isGetters — overrides base
                Visibility.DEFAULT,              // setters   — DEFAULT means "keep base"
                Visibility.DEFAULT,              // creators  — DEFAULT means "keep base"
                Visibility.PUBLIC_ONLY);         // scalarConstructors — overrides base

        // merge(base, overrides): non-DEFAULT override values win over base values
        JsonAutoDetect.Value merged = JsonAutoDetect.Value.merge(base, overrides);
        assertFalse(merged.equals(base));
        assertFalse(merged.equals(overrides));
        assertEquals(merged, merged);

        assertEquals(Visibility.NON_PRIVATE,          merged.getFieldVisibility());
        assertEquals(Visibility.PUBLIC_ONLY,           merged.getGetterVisibility());
        assertEquals(Visibility.PUBLIC_ONLY,           merged.getIsGetterVisibility());
        assertEquals(Visibility.NONE,                  merged.getSetterVisibility());
        assertEquals(Visibility.ANY,                   merged.getCreatorVisibility());
        assertEquals(Visibility.PUBLIC_ONLY,           merged.getScalarConstructorVisibility());

        // merge(overrides, base): roles reversed — base now acts as the override layer
        merged = JsonAutoDetect.Value.merge(overrides, base);
        assertEquals(Visibility.ANY,                   merged.getFieldVisibility());
        assertEquals(Visibility.PUBLIC_ONLY,           merged.getGetterVisibility());
        assertEquals(Visibility.ANY,                   merged.getIsGetterVisibility());
        assertEquals(Visibility.NONE,                  merged.getSetterVisibility());
        assertEquals(Visibility.ANY,                   merged.getCreatorVisibility());
        assertEquals(Visibility.PROTECTED_AND_PUBLIC,  merged.getScalarConstructorVisibility());

        // A null base or null override leaves the non-null argument unchanged
        assertSame(overrides, JsonAutoDetect.Value.merge(null, overrides));
        assertSame(overrides, JsonAutoDetect.Value.merge(overrides, null));
    }

    /**
     * Verifies {@link JsonAutoDetect.Value#construct(PropertyAccessor, Visibility)}:
     * targeting a single accessor sets only that one (the rest stay DEFAULT),
     * while targeting ALL sets every accessor to the given visibility.
     */
    @Test
    public void testFactoryMethods() {
        JsonAutoDetect.Value fieldOnly = JsonAutoDetect.Value.construct(PropertyAccessor.FIELD,
                Visibility.ANY);
        assertEquals(Visibility.ANY,     fieldOnly.getFieldVisibility());
        assertEquals(Visibility.DEFAULT, fieldOnly.getGetterVisibility());
        assertEquals(Visibility.DEFAULT, fieldOnly.getIsGetterVisibility());
        assertEquals(Visibility.DEFAULT, fieldOnly.getSetterVisibility());
        assertEquals(Visibility.DEFAULT, fieldOnly.getCreatorVisibility());
        assertEquals(Visibility.DEFAULT, fieldOnly.getScalarConstructorVisibility());

        JsonAutoDetect.Value allNone = JsonAutoDetect.Value.construct(PropertyAccessor.ALL,
                Visibility.NONE);
        assertEquals(Visibility.NONE, allNone.getFieldVisibility());
        assertEquals(Visibility.NONE, allNone.getGetterVisibility());
        assertEquals(Visibility.NONE, allNone.getIsGetterVisibility());
        assertEquals(Visibility.NONE, allNone.getSetterVisibility());
        assertEquals(Visibility.NONE, allNone.getCreatorVisibility());
        assertEquals(Visibility.NONE, allNone.getScalarConstructorVisibility());
    }

    /**
     * Verifies the {@code with*Visibility} mutator methods: a no-op change returns the
     * same instance, while a real change returns a new distinct instance with the updated value.
     */
    @Test
    public void testSimpleChanges() {
        // withFieldVisibility(DEFAULT) on a no-overrides value is a no-op: same instance returned
        assertSame(NO_OVERRIDES, NO_OVERRIDES.withFieldVisibility(Visibility.DEFAULT));

        JsonAutoDetect.Value v;

        v = NO_OVERRIDES.withCreatorVisibility(Visibility.PUBLIC_ONLY);
        assertNotSame(NO_OVERRIDES, v);
        assertEquals(Visibility.PUBLIC_ONLY, v.getCreatorVisibility());

        v = NO_OVERRIDES.withFieldVisibility(Visibility.ANY);
        assertEquals(Visibility.ANY, v.getFieldVisibility());

        v = NO_OVERRIDES.withGetterVisibility(Visibility.NON_PRIVATE);
        assertEquals(Visibility.NON_PRIVATE, v.getGetterVisibility());

        v = NO_OVERRIDES.withIsGetterVisibility(Visibility.PROTECTED_AND_PUBLIC);
        assertEquals(Visibility.PROTECTED_AND_PUBLIC, v.getIsGetterVisibility());

        v = NO_OVERRIDES.withSetterVisibility(Visibility.PUBLIC_ONLY);
        assertEquals(Visibility.PUBLIC_ONLY, v.getSetterVisibility());

        v = NO_OVERRIDES.withScalarConstructorVisibility(Visibility.PUBLIC_ONLY);
        assertEquals(Visibility.PUBLIC_ONLY, v.getScalarConstructorVisibility());
    }
}
