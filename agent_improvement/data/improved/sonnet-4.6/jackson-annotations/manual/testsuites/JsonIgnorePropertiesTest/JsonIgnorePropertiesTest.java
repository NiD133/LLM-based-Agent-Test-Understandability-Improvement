package com.fasterxml.jackson.annotation;

import java.util.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JsonIgnoreProperties.Value} — the configuration wrapper
 * that supports merging layered annotation overrides at runtime.
 */
public class JsonIgnorePropertiesTest
    extends AnnotationTestUtil
{
    @JsonIgnoreProperties(value={ "foo", "bar" }, ignoreUnknown=true)
    private final static class Bogus {
    }

    private final JsonIgnoreProperties.Value EMPTY = JsonIgnoreProperties.Value.empty();

    @Test
    public void testEmpty() {
        // from(null) is specified to return the EMPTY singleton
        assertSame(EMPTY, JsonIgnoreProperties.Value.from(null));

        assertEquals(0, EMPTY.getIgnored().size());
        assertFalse(EMPTY.getAllowGetters());
        assertFalse(EMPTY.getAllowSetters());
    }

    @Test
    public void testEquality() {
        assertEquals(EMPTY, EMPTY);

        // EMPTY already has merge=true, so withMerge() must return the same instance
        assertSame(EMPTY, EMPTY.withMerge());

        JsonIgnoreProperties.Value withoutMerge = EMPTY.withoutMerge();
        assertEquals(withoutMerge, withoutMerge);
        assertFalse(EMPTY.equals(withoutMerge));
        assertFalse(withoutMerge.equals(EMPTY));
    }

    @Test
    public void testFromAnnotation() throws Exception
    {
        JsonIgnoreProperties.Value fromAnnotation = JsonIgnoreProperties.Value.from(
                Bogus.class.getAnnotation(JsonIgnoreProperties.class));
        assertNotNull(fromAnnotation);
        // annotations never carry a merge flag — that is a programmatic-only concept
        assertFalse(fromAnnotation.getMerge());
        assertFalse(fromAnnotation.getAllowGetters());
        assertFalse(fromAnnotation.getAllowSetters());
        Set<String> ignoredProperties = fromAnnotation.getIgnored();
        assertEquals(2, fromAnnotation.getIgnored().size());
        assertEquals(_set("foo", "bar"), ignoredProperties);

        // verify JDK serialization round-trip preserves all fields
        byte[] serialized = jdkSerialize(fromAnnotation);
        JsonIgnoreProperties.Value deserialized = jdkDeserialize(serialized);
        assertEquals(fromAnnotation, deserialized);
    }

    @Test
    public void testFactories() {
        // factory methods with "empty" inputs must return the EMPTY singleton
        assertSame(EMPTY, JsonIgnoreProperties.Value.forIgnoreUnknown(false));
        assertSame(EMPTY, JsonIgnoreProperties.Value.forIgnoredProperties());
        assertSame(EMPTY, JsonIgnoreProperties.Value.forIgnoredProperties(Collections.<String>emptySet()));

        JsonIgnoreProperties.Value withTwoProperties = JsonIgnoreProperties.Value.forIgnoredProperties("a", "b");
        assertEquals(_set("a", "b"), withTwoProperties.getIgnored());

        // allowGetters=true → getters are NOT ignored, so properties are visible for serialization
        //                      but the properties are still ignored for deserialization
        JsonIgnoreProperties.Value serializationReadable = withTwoProperties.withAllowGetters();
        assertTrue(serializationReadable.getAllowGetters());
        assertFalse(serializationReadable.getAllowSetters());
        assertEquals(_set("a", "b"), serializationReadable.getIgnored());
        assertEquals(_set("a", "b"), serializationReadable.findIgnoredForDeserialization());
        assertEquals(_set(), serializationReadable.findIgnoredForSerialization());

        // allowSetters=true → setters are NOT ignored, so properties are writable during deserialization
        //                      but the properties are still ignored for serialization
        JsonIgnoreProperties.Value deserializationWritable = withTwoProperties.withAllowSetters();
        assertFalse(deserializationWritable.getAllowGetters());
        assertTrue(deserializationWritable.getAllowSetters());
        assertEquals(_set("a", "b"), deserializationWritable.getIgnored());
        assertEquals(_set(), deserializationWritable.findIgnoredForDeserialization());
        assertEquals(_set("a", "b"), deserializationWritable.findIgnoredForSerialization());
    }

    @Test
    public void testMutantFactories()
    {
        assertEquals(2, EMPTY.withIgnored("a", "b").getIgnored().size());
        assertEquals(1, EMPTY.withIgnored(Collections.singleton("x")).getIgnored().size());
        assertEquals(0, EMPTY.withIgnored((Set<String>) null).getIgnored().size());

        assertTrue(EMPTY.withIgnoreUnknown().getIgnoreUnknown());
        assertFalse(EMPTY.withoutIgnoreUnknown().getIgnoreUnknown());

        assertTrue(EMPTY.withAllowGetters().getAllowGetters());
        assertFalse(EMPTY.withoutAllowGetters().getAllowGetters());
        assertTrue(EMPTY.withAllowSetters().getAllowSetters());
        assertFalse(EMPTY.withoutAllowSetters().getAllowSetters());

        assertTrue(EMPTY.withMerge().getMerge());
        assertFalse(EMPTY.withoutMerge().getMerge());
    }

    @Test
    public void testSimpleMerge()
    {
        JsonIgnoreProperties.Value base = EMPTY.withIgnoreUnknown().withAllowGetters();
        JsonIgnoreProperties.Value mergingOverride = EMPTY.withMerge().withIgnored("a");
        JsonIgnoreProperties.Value replacingOverride = EMPTY.withoutMerge();

        // merge=true → result is a union: ignored list, ignoreUnknown, and allowGetters are all preserved
        JsonIgnoreProperties.Value mergedResult = base.withOverrides(mergingOverride);
        assertEquals(Collections.singleton("a"), mergedResult.getIgnored());
        assertTrue(mergedResult.getIgnoreUnknown());
        assertTrue(mergedResult.getAllowGetters());
        assertFalse(mergedResult.getAllowSetters());

        // merge=false → override completely replaces the base (no union of properties)
        JsonIgnoreProperties.Value replacedResult = JsonIgnoreProperties.Value.merge(base, replacingOverride);
        assertEquals(Collections.emptySet(), replacedResult.getIgnored());
        assertFalse(replacedResult.getIgnoreUnknown());
        assertFalse(replacedResult.getAllowGetters());
        assertFalse(replacedResult.getAllowSetters());
        // and effectively really just uses overrides as is
        assertEquals(replacingOverride, replacedResult);

        // null or EMPTY overrides leave the original value unchanged
        assertSame(replacingOverride, replacingOverride.withOverrides(null));
        assertSame(replacingOverride, replacingOverride.withOverrides(EMPTY));
    }

    @Test
    public void testMergeIgnoreProperties()
    {
        JsonIgnoreProperties.Value withA = EMPTY.withIgnored("a");
        JsonIgnoreProperties.Value withB = EMPTY.withIgnored("b");
        JsonIgnoreProperties.Value withC = EMPTY.withIgnored("c");

        JsonIgnoreProperties.Value merged = JsonIgnoreProperties.Value.mergeAll(withA, withB, withC);
        Set<String> allIgnored = merged.getIgnored();
        assertEquals(3, allIgnored.size());
        assertTrue(allIgnored.contains("a"));
        assertTrue(allIgnored.contains("b"));
        assertTrue(allIgnored.contains("c"));
    }

    @Test
    public void testHashCodeIncludesIgnoredContents() {
        JsonIgnoreProperties.Value withAB = EMPTY.withIgnored("a", "b");
        JsonIgnoreProperties.Value withCD = EMPTY.withIgnored("c", "d");
        assertNotEquals(withAB, withCD);
        assertNotEquals(withAB.hashCode(), withCD.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(
                "JsonIgnoreProperties.Value(ignored=[],ignoreUnknown=false,allowGetters=false,allowSetters=true,merge=true)",
                EMPTY.withAllowSetters()
                    .withMerge()
                    .toString());
        int hash = EMPTY.hashCode();
        // no real good way to test but...
        if (hash == 0) {
            fail("Should not get 0 for hash");
        }
    }

    private Set<String> _set(String... args) {
        return new LinkedHashSet<String>(Arrays.asList(args));
    }
}
