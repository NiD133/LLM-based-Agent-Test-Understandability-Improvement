package com.fasterxml.jackson.annotation;

import java.lang.reflect.Member;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JsonAutoDetect.Visibility} and the {@link JsonAutoDetect.Value}
 * helper class that bundles the six accessor visibility settings and supports
 * merging of layered configuration.
 */
public class JsonAutoDetectTest
    extends AnnotationTestUtil
{
    /** Holder whose single {@code public} field is used to probe {@link Visibility#isVisible}. */
    static class Bogus {
        public String value;
    }

    /** Class carrying a fully-populated {@code @JsonAutoDetect} annotation, read back via reflection. */
    @JsonAutoDetect(fieldVisibility=Visibility.NON_PRIVATE,
            getterVisibility=Visibility.PROTECTED_AND_PUBLIC,
            isGetterVisibility=Visibility.NONE,
            setterVisibility=Visibility.PUBLIC_ONLY,
            creatorVisibility=Visibility.ANY)
    private final static class Custom { }

    // Two contrasting baseline Values reused across tests:
    private final static JsonAutoDetect.Value NO_OVERRIDES = JsonAutoDetect.Value.noOverrides();
    private final static JsonAutoDetect.Value DEFAULTS = JsonAutoDetect.Value.defaultVisibility();

    /**
     * {@link Visibility#isVisible} should accept a {@code public} member for every threshold
     * except the two that exclude everything ({@code NONE} and {@code DEFAULT}).
     */
    @Test
    public void testVisibilityIsVisibleForPublicMember() throws Exception
    {
        Member publicField = Bogus.class.getField("value");

        assertTrue(Visibility.ANY.isVisible(publicField));
        assertTrue(Visibility.NON_PRIVATE.isVisible(publicField));
        assertTrue(Visibility.PUBLIC_ONLY.isVisible(publicField));
        assertTrue(Visibility.PROTECTED_AND_PUBLIC.isVisible(publicField));

        assertFalse(Visibility.NONE.isVisible(publicField));
        // DEFAULT defers to context-supplied defaults, so on its own it reports "not visible".
        assertFalse(Visibility.DEFAULT.isVisible(publicField));
    }

    /**
     * Basic Object-contract sanity checks on the shared {@code DEFAULT} Value instance.
     */
    @Test
    public void testBasicValueProperties() {
        JsonAutoDetect.Value defaultValue = JsonAutoDetect.Value.DEFAULT;

        // valueFor() identifies the annotation type this Value belongs to.
        assertEquals(JsonAutoDetect.class, defaultValue.valueFor());

        // hashCode() is exercised mainly to ensure it runs; a zero result is allowed in
        // theory but never expected for this instance.
        assertNotEquals(0, defaultValue.hashCode());

        // equals(): reflexive, and null-safe / type-safe against unrelated objects.
        assertTrue(defaultValue.equals(defaultValue));
        assertFalse(defaultValue.equals(null));
        assertFalse(defaultValue.equals("foo"));
    }

    /**
     * The "no overrides" and "default visibility" instances are each equal to themselves
     * but never to each other.
     */
    @Test
    public void testEquality() {
        assertEquals(NO_OVERRIDES, NO_OVERRIDES);
        assertEquals(DEFAULTS, DEFAULTS);

        assertFalse(DEFAULTS.equals(NO_OVERRIDES));
        assertFalse(NO_OVERRIDES.equals(DEFAULTS));
    }

    /**
     * {@code Value.from(annotation)} copies every visibility from the annotation, produces a
     * distinct-but-equal instance on each call, and survives JDK serialization round-trips.
     */
    @Test
    public void testFromAnnotation()
    {
        JsonAutoDetect ann = Custom.class.getAnnotation(JsonAutoDetect.class);

        JsonAutoDetect.Value value = JsonAutoDetect.Value.from(ann);
        JsonAutoDetect.Value valueAgain = JsonAutoDetect.Value.from(ann);

        // Separate calls yield separate instances that nonetheless compare equal both ways.
        assertNotSame(value, valueAgain);
        assertEquals(value, valueAgain);
        assertEquals(valueAgain, value);

        // Each accessor visibility matches the annotation it was built from.
        assertEquals(ann.fieldVisibility(), value.getFieldVisibility());
        assertEquals(ann.getterVisibility(), value.getGetterVisibility());
        assertEquals(ann.isGetterVisibility(), value.getIsGetterVisibility());
        assertEquals(ann.setterVisibility(), value.getSetterVisibility());
        assertEquals(ann.creatorVisibility(), value.getCreatorVisibility());

        // Round-trip through JDK serialization preserves equality.
        byte[] serialized = jdkSerialize(value);
        JsonAutoDetect.Value deserialized = jdkDeserialize(serialized);
        assertEquals(value, deserialized);
    }

    /**
     * {@code toString()} renders all six visibility settings in a fixed, documented format.
     */
    @Test
    public void testToString() {
        assertEquals(
                "JsonAutoDetect.Value(fields=PUBLIC_ONLY,getters=PUBLIC_ONLY,"
                        + "isGetters=PUBLIC_ONLY,setters=ANY,creators=PUBLIC_ONLY,scalarConstructors=NON_PRIVATE)",
                JsonAutoDetect.Value.defaultVisibility().toString());

        assertEquals(
                "JsonAutoDetect.Value(fields=DEFAULT,getters=DEFAULT,"
                        + "isGetters=DEFAULT,setters=DEFAULT,creators=DEFAULT,scalarConstructors=DEFAULT)",
                JsonAutoDetect.Value.noOverrides().toString());
    }

    /**
     * {@code merge(base, overrides)} keeps each {@code base} visibility unless the corresponding
     * {@code overrides} value is something other than {@link Visibility#DEFAULT}. Merging is
     * order-sensitive, and a {@code null} argument is a no-op that returns the other operand.
     */
    @Test
    public void testSimpleMerge() {
        // Fully-specified base (no DEFAULTs).
        JsonAutoDetect.Value base = JsonAutoDetect.Value.construct(
                Visibility.ANY,                  // fields
                Visibility.PUBLIC_ONLY,          // getters
                Visibility.ANY,                  // isGetters
                Visibility.NONE,                 // setters
                Visibility.ANY,                  // creators
                Visibility.PROTECTED_AND_PUBLIC);// scalarConstructors

        // Overrides: DEFAULT entries should leave the base untouched.
        JsonAutoDetect.Value overrides = JsonAutoDetect.Value.construct(
                Visibility.NON_PRIVATE,          // fields    -> overrides base
                Visibility.DEFAULT,              // getters   -> keep base
                Visibility.PUBLIC_ONLY,          // isGetters -> overrides base
                Visibility.DEFAULT,              // setters   -> keep base
                Visibility.DEFAULT,              // creators  -> keep base
                Visibility.PUBLIC_ONLY);         // scalarCtr -> overrides base

        // base overridden by overrides.
        JsonAutoDetect.Value merged = JsonAutoDetect.Value.merge(base, overrides);
        assertFalse(merged.equals(base));
        assertFalse(merged.equals(overrides));
        assertEquals(merged, merged);

        assertEquals(Visibility.NON_PRIVATE, merged.getFieldVisibility());          // from overrides
        assertEquals(Visibility.PUBLIC_ONLY, merged.getGetterVisibility());         // kept from base
        assertEquals(Visibility.PUBLIC_ONLY, merged.getIsGetterVisibility());       // from overrides
        assertEquals(Visibility.NONE, merged.getSetterVisibility());                // kept from base
        assertEquals(Visibility.ANY, merged.getCreatorVisibility());                // kept from base
        assertEquals(Visibility.PUBLIC_ONLY, merged.getScalarConstructorVisibility());// from overrides

        // Reverse the operands: now "base" acts as the override set; its concrete values win.
        merged = JsonAutoDetect.Value.merge(overrides, base);
        assertEquals(Visibility.ANY, merged.getFieldVisibility());
        assertEquals(Visibility.PUBLIC_ONLY, merged.getGetterVisibility());
        assertEquals(Visibility.ANY, merged.getIsGetterVisibility());
        assertEquals(Visibility.NONE, merged.getSetterVisibility());
        assertEquals(Visibility.ANY, merged.getCreatorVisibility());
        assertEquals(Visibility.PROTECTED_AND_PUBLIC, merged.getScalarConstructorVisibility());

        // A null operand means "nothing to merge": the non-null operand is returned as-is.
        assertSame(overrides, JsonAutoDetect.Value.merge(null, overrides));
        assertSame(overrides, JsonAutoDetect.Value.merge(overrides, null));
    }

    /**
     * The {@code construct(PropertyAccessor, Visibility)} factory sets visibility only for the
     * named accessor(s), leaving every other accessor at {@link Visibility#DEFAULT}.
     */
    @Test
    public void testFactoryMethods() {
        // FIELD: only the field visibility is set; the rest stay DEFAULT.
        JsonAutoDetect.Value fieldOnly = JsonAutoDetect.Value.construct(PropertyAccessor.FIELD,
                Visibility.ANY);
        assertEquals(Visibility.ANY, fieldOnly.getFieldVisibility());
        assertEquals(Visibility.DEFAULT, fieldOnly.getGetterVisibility());
        assertEquals(Visibility.DEFAULT, fieldOnly.getIsGetterVisibility());
        assertEquals(Visibility.DEFAULT, fieldOnly.getSetterVisibility());
        assertEquals(Visibility.DEFAULT, fieldOnly.getCreatorVisibility());
        assertEquals(Visibility.DEFAULT, fieldOnly.getScalarConstructorVisibility());

        // ALL: every accessor receives the supplied visibility.
        JsonAutoDetect.Value allAccessors = JsonAutoDetect.Value.construct(PropertyAccessor.ALL,
                Visibility.NONE);
        assertEquals(Visibility.NONE, allAccessors.getFieldVisibility());
        assertEquals(Visibility.NONE, allAccessors.getGetterVisibility());
        assertEquals(Visibility.NONE, allAccessors.getIsGetterVisibility());
        assertEquals(Visibility.NONE, allAccessors.getSetterVisibility());
        assertEquals(Visibility.NONE, allAccessors.getCreatorVisibility());
        assertEquals(Visibility.NONE, allAccessors.getScalarConstructorVisibility());
    }

    /**
     * Each {@code withXxxVisibility} mutator returns a new instance carrying the requested change,
     * except when the change is a no-op (setting a visibility already at {@link Visibility#DEFAULT}),
     * in which case the same instance is returned.
     */
    @Test
    public void testSimpleChanges() {
        // No-op change on an all-DEFAULT instance returns the very same instance.
        assertSame(NO_OVERRIDES, NO_OVERRIDES.withFieldVisibility(Visibility.DEFAULT));

        // A real change produces a distinct instance with the new value.
        JsonAutoDetect.Value changed = NO_OVERRIDES.withCreatorVisibility(Visibility.PUBLIC_ONLY);
        assertNotSame(NO_OVERRIDES, changed);
        assertEquals(Visibility.PUBLIC_ONLY, changed.getCreatorVisibility());

        assertEquals(Visibility.ANY,
                NO_OVERRIDES.withFieldVisibility(Visibility.ANY).getFieldVisibility());
        assertEquals(Visibility.NON_PRIVATE,
                NO_OVERRIDES.withGetterVisibility(Visibility.NON_PRIVATE).getGetterVisibility());
        assertEquals(Visibility.PROTECTED_AND_PUBLIC,
                NO_OVERRIDES.withIsGetterVisibility(Visibility.PROTECTED_AND_PUBLIC).getIsGetterVisibility());
        assertEquals(Visibility.PUBLIC_ONLY,
                NO_OVERRIDES.withSetterVisibility(Visibility.PUBLIC_ONLY).getSetterVisibility());
        assertEquals(Visibility.PUBLIC_ONLY,
                NO_OVERRIDES.withScalarConstructorVisibility(Visibility.PUBLIC_ONLY).getScalarConstructorVisibility());
    }
}
