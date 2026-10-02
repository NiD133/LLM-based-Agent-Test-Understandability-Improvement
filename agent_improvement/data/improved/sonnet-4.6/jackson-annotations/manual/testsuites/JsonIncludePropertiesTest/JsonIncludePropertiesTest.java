package com.fasterxml.jackson.annotation;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JsonIncludeProperties.Value} to verify annotation parsing,
 * override merging, equality, ordering, and JDK serialization.
 */
public class JsonIncludePropertiesTest
    extends AnnotationTestUtil
{
    // --- Annotated helper classes ---

    @JsonIncludeProperties(value = {"foo", "bar"})
    private static final class Bogus {}

    @JsonIncludeProperties(order = OptBoolean.TRUE, value = {"id", "code", "name"})
    private static final class Ordered {}

    // --- Shared constants ---

    private static final JsonIncludeProperties.Value ALL = JsonIncludeProperties.Value.all();

    // --- Shared fixture ---

    /**
     * Value built from the {@link Bogus} annotation, reused across multiple tests.
     * Represents exactly the set {"foo", "bar"} with no ordering defined.
     */
    private JsonIncludeProperties.Value bogusValue;

    @BeforeEach
    void setUp() {
        bogusValue = JsonIncludeProperties.Value.from(
                Bogus.class.getAnnotation(JsonIncludeProperties.class));
    }

    // --- Tests ---

    /**
     * The "all" singleton represents "include everything": its included-set and ordering
     * are both null, its hash code is zero, and it has a fixed toString representation.
     * {@code from(null)} must return this same singleton instance.
     */
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

    /**
     * Parsing the {@link Bogus} annotation should produce a Value containing exactly
     * the two listed properties with no ordering override. Parsing the same annotation
     * twice must yield equal Values. The Value must also survive a JDK serialization
     * round-trip unchanged.
     */
    @Test
    public void testFromAnnotation()
    {
        assertNotNull(bogusValue);
        assertEquals(2, bogusValue.getIncluded().size());
        assertEquals(_set("foo", "bar"), bogusValue.getIncluded());
        assertNull(bogusValue.getOrdered());

        // toString iteration order is unspecified (HashSet), so accept either ordering
        String valueAsString = bogusValue.toString();
        boolean fooFirst = valueAsString.equals("JsonIncludeProperties.Value(included=[foo, bar],ordered=null)");
        boolean barFirst = valueAsString.equals("JsonIncludeProperties.Value(included=[bar, foo],ordered=null)");
        assertTrue(fooFirst || barFirst);

        // Parsing the same annotation a second time must yield an equal Value
        JsonIncludeProperties.Value reparsed = JsonIncludeProperties.Value.from(
                Bogus.class.getAnnotation(JsonIncludeProperties.class));
        assertEquals(bogusValue, reparsed);

        // Verify JDK serialization round-trip preserves equality
        byte[] serialized = jdkSerialize(bogusValue);
        JsonIncludeProperties.Value deserialized = jdkDeserialize(serialized);
        assertEquals(bogusValue, deserialized);
    }

    /**
     * Overriding with ALL (included=null) must leave the original Value unchanged,
     * because an "undefined" override is ignored by the merging contract.
     */
    @Test
    public void testWithOverridesAll() {
        JsonIncludeProperties.Value result = bogusValue.withOverrides(ALL);
        Set<String> included = result.getIncluded();
        assertEquals(2, included.size());
        assertEquals(_set("foo", "bar"), included);
    }

    /**
     * Overriding with an empty included-set produces an empty intersection,
     * meaning no properties survive the merge.
     */
    @Test
    public void testWithOverridesEmpty() {
        JsonIncludeProperties.Value emptyOverride = new JsonIncludeProperties.Value(
                Collections.<String>emptySet(), false);
        JsonIncludeProperties.Value result = bogusValue.withOverrides(emptyOverride);
        Set<String> included = result.getIncluded();
        assertEquals(0, included.size());
    }

    /**
     * Overriding with a strict subset retains only properties present in both sets
     * (intersection semantics): {"foo","bar"} merged with {"foo"} yields {"foo"}.
     */
    @Test
    public void testWithOverridesMerge() {
        JsonIncludeProperties.Value partialOverride = new JsonIncludeProperties.Value(_set("foo"), false);
        JsonIncludeProperties.Value result = bogusValue.withOverrides(partialOverride);
        Set<String> included = result.getIncluded();
        assertEquals(1, included.size());
        assertEquals(_set("foo"), included);
    }

    /**
     * The {@link Ordered} annotation sets {@code order=OptBoolean.TRUE}; the parsed
     * Value must reflect that flag and contain all three declared properties.
     */
    @Test
    public void testFromAnnotationOrdered()
    {
        JsonIncludeProperties.Value orderedValue = JsonIncludeProperties.Value.from(
                Ordered.class.getAnnotation(JsonIncludeProperties.class));
        assertNotNull(orderedValue);
        assertEquals(3, orderedValue.getIncluded().size());
        assertEquals(Boolean.TRUE, orderedValue.getOrdered());
    }

    /**
     * Values with distinct included-sets must not be equal, and their hash codes
     * must also differ (different Set contents produce different hash values).
     */
    @Test
    public void testHashCodeIncludesContents() {
        JsonIncludeProperties.Value valueAB = new JsonIncludeProperties.Value(_set("a", "b"), null);
        JsonIncludeProperties.Value valueCD = new JsonIncludeProperties.Value(_set("c", "d"), null);
        assertNotEquals(valueAB, valueCD);
        assertNotEquals(valueAB.hashCode(), valueCD.hashCode());
    }

    /**
     * Equality is sensitive to the {@code ordered} flag: two Values with identical
     * included-sets but different ordering flags must not be equal, while two Values
     * with the same set and the same flag must be equal.
     */
    @Test
    public void testOrderedEquality()
    {
        JsonIncludeProperties.Value orderedTrue  = new JsonIncludeProperties.Value(_set("a", "b"), Boolean.TRUE);
        JsonIncludeProperties.Value orderedFalse = new JsonIncludeProperties.Value(_set("a", "b"), Boolean.FALSE);
        JsonIncludeProperties.Value orderedTrue2 = new JsonIncludeProperties.Value(_set("a", "b"), Boolean.TRUE);
        JsonIncludeProperties.Value orderedNull  = new JsonIncludeProperties.Value(_set("a", "b"), null);
        assertNotEquals(orderedTrue, orderedFalse);
        assertNotEquals(orderedTrue, orderedNull);
        assertNotEquals(orderedFalse, orderedNull);
        assertEquals(orderedTrue, orderedTrue2);
    }

    private Set<String> _set(String... args)
    {
        return new LinkedHashSet<String>(Arrays.asList(args));
    }
}
