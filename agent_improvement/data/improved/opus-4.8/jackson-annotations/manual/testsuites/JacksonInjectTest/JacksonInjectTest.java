package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link JacksonInject.Value}, the helper class that captures the
 * settings (<code>id</code>, <code>useInput</code>, <code>optional</code>) of a
 * {@link JacksonInject} annotation and exposes factory / "mutant factory" methods.
 */
public class JacksonInjectTest
    extends AnnotationTestUtil
{
    /**
     * Holder for sample {@code @JacksonInject}-annotated fields. The fields are
     * looked up by name via reflection, so the field names must stay in sync with
     * the {@code getField(...)} calls below.
     */
    private final static class AnnotatedFields {
        // Fully specified injection: explicit id, input ignored, not optional.
        @JacksonInject(value = "inject", useInput = OptBoolean.FALSE,
                optional = OptBoolean.FALSE)
        public int field;

        // All attributes left at their annotation defaults.
        @JacksonInject
        public int vanilla;

        // Only "optional" is overridden (to TRUE).
        @JacksonInject(optional = OptBoolean.TRUE)
        public int optionalField;
    }

    /** Shared "no settings" value (id, useInput and optional all {@code null}). */
    private final JacksonInject.Value EMPTY = JacksonInject.Value.empty();

    /**
     * The empty value reports no settings, defaults {@code useInput} to the
     * caller-supplied fallback, and is canonical: constructing from all-null
     * (or an empty-string id, which is coerced to null) returns the same instance.
     */
    @Test
    public void testEmpty()
    {
        assertNull(EMPTY.getId());
        assertNull(EMPTY.getUseInput());

        // willUseInput falls back to the provided default when useInput is null
        assertTrue(EMPTY.willUseInput(true));
        assertFalse(EMPTY.willUseInput(false));

        // construct(...) returns the shared EMPTY singleton when nothing is set
        assertSame(EMPTY, JacksonInject.Value.construct(null, null, null));
        // an empty-string id is coerced to null, so this is "empty" too
        assertSame(EMPTY, JacksonInject.Value.construct("", null, null));
    }

    /**
     * {@link JacksonInject.Value#from(JacksonInject)} reads settings off an actual
     * annotation instance (and tolerates a {@code null} annotation). Also checks
     * {@code toString}, equality against EMPTY, and JDK serializability.
     */
    @Test
    public void testFromAnnotation() throws Exception
    {
        // A null annotation maps to the empty value
        assertSame(EMPTY, JacksonInject.Value.from(null));

        // Fully specified field -> id and useInput populated from the annotation
        JacksonInject fullySpecifiedAnn =
                AnnotatedFields.class.getField("field").getAnnotation(JacksonInject.class);
        JacksonInject.Value fullySpecified = JacksonInject.Value.from(fullySpecifiedAnn);
        assertEquals("inject", fullySpecified.getId());
        assertEquals(Boolean.FALSE, fullySpecified.getUseInput());
        assertEquals("JacksonInject.Value(id=inject,useInput=false,optional=false)",
                fullySpecified.toString());

        // A populated value is never equal to EMPTY (symmetric check)
        assertFalse(fullySpecified.equals(EMPTY));
        assertFalse(EMPTY.equals(fullySpecified));

        // A round-trip through JDK serialization preserves equality
        byte[] serialized = jdkSerialize(fullySpecified);
        JacksonInject.Value deserialized = jdkDeserialize(serialized);
        assertEquals(fullySpecified, deserialized);

        // Vanilla field uses annotation defaults -> "optional" is null, not false
        JacksonInject vanillaAnn =
                AnnotatedFields.class.getField("vanilla").getAnnotation(JacksonInject.class);
        JacksonInject.Value vanilla = JacksonInject.Value.from(vanillaAnn);
        assertEquals(JacksonInject.Value.construct(null, null, null), vanilla,
                "optional should be `null` by default");

        // Field with optional=TRUE -> only the optional flag is set
        JacksonInject optionalAnn = AnnotatedFields.class.getField("optionalField")
                .getAnnotation(JacksonInject.class);
        JacksonInject.Value optional = JacksonInject.Value.from(optionalAnn);
        assertEquals(JacksonInject.Value.construct(null, null, true), optional);
    }

    /**
     * Standard {@code Object} overrides: {@code toString}, a non-zero {@code hashCode},
     * and {@code equals} (reflexive, null-safe, type-safe, and sensitive to each of the
     * three fields independently).
     */
    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void testStdMethods() {
        assertEquals("JacksonInject.Value(id=null,useInput=null,optional=null)",
                EMPTY.toString());

        // hashCode has no fixed contract value, but must not collapse to 0
        assertNotEquals(0, EMPTY.hashCode());

        // equals: reflexive, but not equal to null or to an unrelated type
        assertEquals(EMPTY, EMPTY);
        assertFalse(EMPTY.equals(null));
        assertFalse(EMPTY.equals("xyz"));

        // Two values built from identical settings are equal
        JacksonInject.Value reference = JacksonInject.Value.construct("value", true, true);
        JacksonInject.Value sameSettings = JacksonInject.Value.construct("value", true, true);
        assertEquals(reference, sameSettings);

        // Differing in any single field (id / useInput / optional) breaks equality,
        // whether the difference is null vs set or a different non-null value.
        assertNotEquals(reference, JacksonInject.Value.construct(null, true, true));      // id null
        assertNotEquals(reference, JacksonInject.Value.construct("value", null, true));   // useInput null
        assertNotEquals(reference, JacksonInject.Value.construct("value", true, null));   // optional null
        assertNotEquals(reference, JacksonInject.Value.construct("not equal", true, true)); // different id
        assertNotEquals(reference, JacksonInject.Value.construct("value", false, true));  // different useInput
        assertNotEquals(reference, JacksonInject.Value.construct("value", true, false));  // different optional

        // Not equal to an arbitrary non-Value object
        assertNotEquals(reference, "string");
    }

    /**
     * The {@code withId} / {@code withUseInput} / {@code withOptional} mutant factories
     * each return a new instance when the setting changes, but return {@code this} when
     * the requested value matches the current one.
     */
    @Test
    public void testFactories() throws Exception
    {
        // withId on EMPTY produces a new, distinct value carrying the id
        JacksonInject.Value withId = EMPTY.withId("name");
        assertNotSame(EMPTY, withId);
        assertEquals("name", withId.getId());
        // re-applying the same id is a no-op and returns the same instance
        assertSame(withId, withId.withId("name"));

        // withUseInput changes a field -> new, non-equal instance
        JacksonInject.Value withUseInput = withId.withUseInput(Boolean.TRUE);
        assertNotSame(withId, withUseInput);
        assertFalse(withId.equals(withUseInput));
        assertFalse(withUseInput.equals(withId));
        assertSame(withUseInput, withUseInput.withUseInput(Boolean.TRUE));

        // withOptional likewise changes a field -> new, non-equal instance
        JacksonInject.Value withOptional = withId.withOptional(Boolean.TRUE);
        assertNotSame(withId, withOptional);
        assertFalse(withId.equals(withOptional));
        assertFalse(withOptional.equals(withId));
        assertSame(withOptional, withOptional.withOptional(Boolean.TRUE));
        assertTrue(withOptional.getOptional());

        // hashCode of a populated value must not collapse to 0
        assertNotEquals(0, withUseInput.hashCode());
    }
}
