package com.fasterxml.jackson.annotation;

import java.util.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JsonIncludeProperties.Value}: how it is built from the
 * {@link JsonIncludeProperties} annotation, how instances are merged via
 * {@link JsonIncludeProperties.Value#withOverrides}, and how equality,
 * hashing and serialization behave.
 */
public class JsonIncludePropertiesTest
    extends AnnotationTestUtil
{
    // Sample annotated type: includes "foo" and "bar", order left at default.
    @JsonIncludeProperties(value = {"foo", "bar"})
    private final static class Bogus
    {
    }

    // Sample annotated type that also requests ordered inclusion.
    @JsonIncludeProperties(order = OptBoolean.TRUE, value = {"id", "code", "name"})
    private final static class Ordered
    {
    }

    // Shared "include everything" value (null included set, null ordered flag).
    private final JsonIncludeProperties.Value ALL = JsonIncludeProperties.Value.all();

    @Test
    public void testAll()
    {
        // from(null) must hand back the shared ALL singleton
        assertSame(ALL, JsonIncludeProperties.Value.from(null));

        // ALL means "no explicit included set" and "no ordering preference"
        assertNull(ALL.getIncluded());
        assertNull(ALL.getOrdered());

        assertEquals(ALL, ALL);
        assertEquals("JsonIncludeProperties.Value(included=null,ordered=null)", ALL.toString());
        assertEquals(0, ALL.hashCode());
    }

    @Test
    public void testFromAnnotation()
    {
        JsonIncludeProperties.Value value = valueFromAnnotation(Bogus.class);
        assertNotNull(value);

        // The annotation's value() {"foo", "bar"} becomes the included set; no ordering flag
        Set<String> included = value.getIncluded();
        assertEquals(2, included.size());
        assertEquals(setOf("foo", "bar"), included);
        assertNull(value.getOrdered());

        // toString contains the set, whose iteration order is not guaranteed
        String description = value.toString();
        boolean fooFirst = description.equals("JsonIncludeProperties.Value(included=[foo, bar],ordered=null)");
        boolean barFirst = description.equals("JsonIncludeProperties.Value(included=[bar, foo],ordered=null)");
        assertTrue(fooFirst || barFirst,
                "Unexpected toString(): " + description);

        // Two values built from the same annotation are equal
        assertEquals(value, valueFromAnnotation(Bogus.class));

        // Value should survive a JDK serialization round-trip unchanged
        byte[] serialized = jdkSerialize(value);
        JsonIncludeProperties.Value deserialized = jdkDeserialize(serialized);
        assertEquals(value, deserialized);
    }

    @Test
    public void testWithOverridesAll() {
        JsonIncludeProperties.Value value = valueFromAnnotation(Bogus.class);

        // Overriding with ALL (undefined included set) leaves the original untouched
        value = value.withOverrides(ALL);

        Set<String> included = value.getIncluded();
        assertEquals(2, included.size());
        assertEquals(setOf("foo", "bar"), included);
    }

    @Test
    public void testWithOverridesEmpty() {
        JsonIncludeProperties.Value value = valueFromAnnotation(Bogus.class);

        // Overriding with an empty set keeps only the intersection: nothing
        value = value.withOverrides(new JsonIncludeProperties.Value(Collections.<String>emptySet(), false));

        assertEquals(0, value.getIncluded().size());
    }

    @Test
    public void testWithOverridesMerge() {
        JsonIncludeProperties.Value value = valueFromAnnotation(Bogus.class);

        // Override of {"foo"} intersects with {"foo", "bar"} to leave just {"foo"}
        value = value.withOverrides(new JsonIncludeProperties.Value(setOf("foo"), false));

        Set<String> included = value.getIncluded();
        assertEquals(1, included.size());
        assertEquals(setOf("foo"), included);
    }

    @Test
    public void testFromAnnotationOrdered()
    {
        JsonIncludeProperties.Value value = valueFromAnnotation(Ordered.class);
        assertNotNull(value);

        // order = TRUE on the annotation surfaces as getOrdered() == Boolean.TRUE
        assertEquals(3, value.getIncluded().size());
        assertEquals(Boolean.TRUE, value.getOrdered());
    }

    @Test
    public void testHashCodeIncludesContents() {
        // Different included sets must produce different values and hash codes
        JsonIncludeProperties.Value abValue = new JsonIncludeProperties.Value(setOf("a", "b"), null);
        JsonIncludeProperties.Value cdValue = new JsonIncludeProperties.Value(setOf("c", "d"), null);

        assertNotEquals(abValue, cdValue);
        assertNotEquals(abValue.hashCode(), cdValue.hashCode());
    }

    @Test
    public void testOrderedEquality()
    {
        // Same included set, varying ordered flag (TRUE / FALSE / TRUE / null)
        JsonIncludeProperties.Value orderedTrue = new JsonIncludeProperties.Value(setOf("a", "b"), Boolean.TRUE);
        JsonIncludeProperties.Value orderedFalse = new JsonIncludeProperties.Value(setOf("a", "b"), Boolean.FALSE);
        JsonIncludeProperties.Value orderedTrueAgain = new JsonIncludeProperties.Value(setOf("a", "b"), Boolean.TRUE);
        JsonIncludeProperties.Value orderedNull = new JsonIncludeProperties.Value(setOf("a", "b"), null);

        // The ordered flag participates in equality
        assertNotEquals(orderedTrue, orderedFalse);
        assertNotEquals(orderedTrue, orderedNull);
        assertNotEquals(orderedFalse, orderedNull);

        // Identical included set and ordered flag are equal
        assertEquals(orderedTrue, orderedTrueAgain);
    }

    // Builds a Value from the @JsonIncludeProperties annotation declared on the given type.
    private JsonIncludeProperties.Value valueFromAnnotation(Class<?> annotatedType)
    {
        return JsonIncludeProperties.Value.from(
                annotatedType.getAnnotation(JsonIncludeProperties.class));
    }

    // Order-preserving set, used so expected/actual comparisons are easy to read.
    private Set<String> setOf(String... names)
    {
        return new LinkedHashSet<String>(Arrays.asList(names));
    }
}
