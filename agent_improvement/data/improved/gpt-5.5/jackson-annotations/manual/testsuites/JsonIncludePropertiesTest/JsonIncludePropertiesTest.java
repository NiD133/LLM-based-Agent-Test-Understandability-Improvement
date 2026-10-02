package com.fasterxml.jackson.annotation;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests to verify that it is possibly to merge {@link JsonIncludeProperties.Value}
 * instances for overrides.
 */
public class JsonIncludePropertiesTest
    extends AnnotationTestUtil
{
    @JsonIncludeProperties(value = {"foo", "bar"})
    private final static class Bogus
    {
    }

    @JsonIncludeProperties(order = OptBoolean.TRUE, value = {"id", "code", "name"})
    private final static class Ordered
    {
    }

    private final JsonIncludeProperties.Value ALL = JsonIncludeProperties.Value.all();

    @Test
    public void testAll()
    {
        assertSame(ALL, JsonIncludeProperties.Value.from(null));
        assertNull(ALL.getIncluded());
        assertNull(ALL.getOrdered());
        assertEquals(ALL, ALL);
        assertEquals("JsonIncludeProperties.Value(included=null,ordered=null)", ALL.toString());
        assertEquals(0, ALL.hashCode());
    }

    @Test
    public void testFromAnnotation()
        throws Exception
    {
        JsonIncludeProperties.Value value = valueFromBogusAnnotation();

        assertNotNull(value);
        Set<String> included = value.getIncluded();
        assertEquals(2, value.getIncluded().size());
        assertEquals(_set("foo", "bar"), included);
        assertNull(value.getOrdered());

        String asString = value.toString();
        boolean orderedAsDeclared = asString.equals(
                "JsonIncludeProperties.Value(included=[foo, bar],ordered=null)");
        boolean orderedByHashIteration = asString.equals(
                "JsonIncludeProperties.Value(included=[bar, foo],ordered=null)");
        assertTrue(orderedAsDeclared || orderedByHashIteration);

        assertEquals(value, valueFromBogusAnnotation());

        byte[] serialized = jdkSerialize(value);
        JsonIncludeProperties.Value deserialized = jdkDeserialize(serialized);

        assertEquals(value, deserialized);
    }

    @Test
    public void testWithOverridesAll()
    {
        JsonIncludeProperties.Value value = valueFromBogusAnnotation();

        value = value.withOverrides(ALL);

        Set<String> included = value.getIncluded();
        assertEquals(2, included.size());
        assertEquals(_set("foo", "bar"), included);
    }

    @Test
    public void testWithOverridesEmpty()
    {
        JsonIncludeProperties.Value value = valueFromBogusAnnotation();
        JsonIncludeProperties.Value emptyOverride = new JsonIncludeProperties.Value(
                Collections.<String>emptySet(), false);

        value = value.withOverrides(emptyOverride);

        Set<String> included = value.getIncluded();
        assertEquals(0, included.size());
    }

    @Test
    public void testWithOverridesMerge()
    {
        JsonIncludeProperties.Value value = valueFromBogusAnnotation();
        JsonIncludeProperties.Value fooOnlyOverride = new JsonIncludeProperties.Value(
                _set("foo"), false);

        value = value.withOverrides(fooOnlyOverride);

        Set<String> included = value.getIncluded();
        assertEquals(1, included.size());
        assertEquals(_set("foo"), included);
    }

    @Test
    public void testFromAnnotationOrdered()
    {
        JsonIncludeProperties.Value value = JsonIncludeProperties.Value.from(
                Ordered.class.getAnnotation(JsonIncludeProperties.class));

        assertNotNull(value);
        assertEquals(3, value.getIncluded().size());
        assertEquals(Boolean.TRUE, value.getOrdered());
    }

    @Test
    public void testHashCodeIncludesContents()
    {
        JsonIncludeProperties.Value firstValue = new JsonIncludeProperties.Value(
                _set("a", "b"), null);
        JsonIncludeProperties.Value secondValue = new JsonIncludeProperties.Value(
                _set("c", "d"), null);

        assertNotEquals(firstValue, secondValue);
        assertNotEquals(firstValue.hashCode(), secondValue.hashCode());
    }

    @Test
    public void testOrderedEquality()
    {
        JsonIncludeProperties.Value orderedTrue = new JsonIncludeProperties.Value(
                _set("a", "b"), Boolean.TRUE);
        JsonIncludeProperties.Value orderedFalse = new JsonIncludeProperties.Value(
                _set("a", "b"), Boolean.FALSE);
        JsonIncludeProperties.Value alsoOrderedTrue = new JsonIncludeProperties.Value(
                _set("a", "b"), Boolean.TRUE);
        JsonIncludeProperties.Value unordered = new JsonIncludeProperties.Value(
                _set("a", "b"), null);

        assertNotEquals(orderedTrue, orderedFalse);
        assertNotEquals(orderedTrue, unordered);
        assertNotEquals(orderedFalse, unordered);
        assertEquals(orderedTrue, alsoOrderedTrue);
    }

    private JsonIncludeProperties.Value valueFromBogusAnnotation()
    {
        return JsonIncludeProperties.Value.from(
                Bogus.class.getAnnotation(JsonIncludeProperties.class));
    }

    private Set<String> _set(String... args)
    {
        return new LinkedHashSet<String>(Arrays.asList(args));
    }
}
