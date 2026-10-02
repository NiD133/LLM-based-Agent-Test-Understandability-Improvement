package com.fasterxml.jackson.annotation;

import java.util.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JsonIgnoreProperties.Value}: construction from annotations,
 * the {@code withXxx()}/{@code withoutXxx()} mutant factories, equality/hashing,
 * and the merging of layered (base + override) configurations.
 */
public class JsonIgnorePropertiesTest
    extends AnnotationTestUtil
{
    /** Sample type carrying a fully-specified annotation, used to test {@code Value.from(...)}. */
    @JsonIgnoreProperties(value={ "foo", "bar" }, ignoreUnknown=true)
    private final static class Bogus {
    }

    /** Shared default instance: nothing ignored, no special flags, but merging enabled. */
    private final JsonIgnoreProperties.Value EMPTY = JsonIgnoreProperties.Value.empty();

    @Test
    public void testEmpty() {
        // Building from a null annotation is allowed and yields the shared empty instance
        assertSame(EMPTY, JsonIgnoreProperties.Value.from(null));

        // The empty value ignores nothing and allows neither getters nor setters
        assertEquals(0, EMPTY.getIgnored().size());
        assertFalse(EMPTY.getAllowGetters());
        assertFalse(EMPTY.getAllowSetters());
    }

    @Test
    public void testEquality() {
        // A value always equals itself
        assertEquals(EMPTY, EMPTY);

        // EMPTY already has merge=true, so withMerge() is a no-op returning the same instance
        assertSame(EMPTY, EMPTY.withMerge());

        // Flipping merge off produces a value that no longer equals EMPTY (in either direction)
        JsonIgnoreProperties.Value withoutMerge = EMPTY.withoutMerge();
        assertEquals(withoutMerge, withoutMerge);
        assertFalse(EMPTY.equals(withoutMerge));
        assertFalse(withoutMerge.equals(EMPTY));
    }

    @Test
    public void testFromAnnotation() throws Exception
    {
        JsonIgnoreProperties.Value fromBogus = JsonIgnoreProperties.Value.from(
                Bogus.class.getAnnotation(JsonIgnoreProperties.class));
        assertNotNull(fromBogus);

        // Annotation-derived values never enable merging or allow getters/setters
        assertFalse(fromBogus.getMerge());
        assertFalse(fromBogus.getAllowGetters());
        assertFalse(fromBogus.getAllowSetters());

        // The "foo"/"bar" listed on the annotation become the ignored set
        Set<String> ignored = fromBogus.getIgnored();
        assertEquals(2, ignored.size());
        assertEquals(setOf("foo", "bar"), ignored);

        // A Value survives a JDK serialize/deserialize round-trip unchanged
        byte[] serialized = jdkSerialize(fromBogus);
        JsonIgnoreProperties.Value deserialized = jdkDeserialize(serialized);
        assertEquals(fromBogus, deserialized);
    }

    @Test
    public void testFactories() {
        // Factories that describe "no configuration" all collapse to the shared empty instance
        assertSame(EMPTY, JsonIgnoreProperties.Value.forIgnoreUnknown(false));
        assertSame(EMPTY, JsonIgnoreProperties.Value.forIgnoredProperties());
        assertSame(EMPTY, JsonIgnoreProperties.Value.forIgnoredProperties(Collections.<String>emptySet()));

        // Listing properties to ignore records them as the ignored set
        JsonIgnoreProperties.Value ignoringAB = JsonIgnoreProperties.Value.forIgnoredProperties("a", "b");
        assertEquals(setOf("a", "b"), ignoringAB.getIgnored());

        // allowGetters=true: getters are no longer ignored, so nothing is ignored for serialization
        JsonIgnoreProperties.Value allowingGetters = ignoringAB.withAllowGetters();
        assertTrue(allowingGetters.getAllowGetters());
        assertFalse(allowingGetters.getAllowSetters());
        assertEquals(setOf("a", "b"), allowingGetters.getIgnored());
        assertEquals(setOf("a", "b"), allowingGetters.findIgnoredForDeserialization());
        assertEquals(setOf(), allowingGetters.findIgnoredForSerialization());

        // allowSetters=true: setters are no longer ignored, so nothing is ignored for deserialization
        JsonIgnoreProperties.Value allowingSetters = ignoringAB.withAllowSetters();
        assertFalse(allowingSetters.getAllowGetters());
        assertTrue(allowingSetters.getAllowSetters());
        assertEquals(setOf("a", "b"), allowingSetters.getIgnored());
        assertEquals(setOf(), allowingSetters.findIgnoredForDeserialization());
        assertEquals(setOf("a", "b"), allowingSetters.findIgnoredForSerialization());
    }

    @Test
    public void testMutantFactories()
    {
        // withIgnored(...) replaces the ignored set; null clears it
        assertEquals(2, EMPTY.withIgnored("a", "b").getIgnored().size());
        assertEquals(1, EMPTY.withIgnored(Collections.singleton("x")).getIgnored().size());
        assertEquals(0, EMPTY.withIgnored((Set<String>) null).getIgnored().size());

        // Each withXxx()/withoutXxx() pair toggles exactly its own flag
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

        // Override has merge=true: the result is the union of both sides' settings
        JsonIgnoreProperties.Value merged = base.withOverrides(mergingOverride);
        assertEquals(Collections.singleton("a"), merged.getIgnored());
        assertTrue(merged.getIgnoreUnknown());
        assertTrue(merged.getAllowGetters());
        assertFalse(merged.getAllowSetters());

        // Override has merge=false: it replaces the base wholesale
        JsonIgnoreProperties.Value replaced = JsonIgnoreProperties.Value.merge(base, replacingOverride);
        assertEquals(Collections.emptySet(), replaced.getIgnored());
        assertFalse(replaced.getIgnoreUnknown());
        assertFalse(replaced.getAllowGetters());
        assertFalse(replaced.getAllowSetters());

        // ...so the replacing override is effectively used as-is
        assertEquals(replacingOverride, replaced);

        // Overriding with null or EMPTY changes nothing: same instance is returned
        assertSame(replacingOverride, replacingOverride.withOverrides(null));
        assertSame(replacingOverride, replacingOverride.withOverrides(EMPTY));
    }

    @Test
    public void testMergeIgnoreProperties()
    {
        JsonIgnoreProperties.Value ignoringA = EMPTY.withIgnored("a");
        JsonIgnoreProperties.Value ignoringB = EMPTY.withIgnored("b");
        JsonIgnoreProperties.Value ignoringC = EMPTY.withIgnored("c");

        // mergeAll unions the ignored sets across all three values
        JsonIgnoreProperties.Value merged = JsonIgnoreProperties.Value.mergeAll(ignoringA, ignoringB, ignoringC);
        Set<String> allIgnored = merged.getIgnored();
        assertEquals(3, allIgnored.size());
        assertTrue(allIgnored.contains("a"));
        assertTrue(allIgnored.contains("b"));
        assertTrue(allIgnored.contains("c"));
    }

    @Test
    public void testHashCodeIncludesIgnoredContents() {
        // Different ignored sets must yield unequal values and (here) unequal hash codes
        JsonIgnoreProperties.Value ignoringAB = EMPTY.withIgnored("a", "b");
        JsonIgnoreProperties.Value ignoringCD = EMPTY.withIgnored("c", "d");
        assertNotEquals(ignoringAB, ignoringCD);
        assertNotEquals(ignoringAB.hashCode(), ignoringCD.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(
                "JsonIgnoreProperties.Value(ignored=[],ignoreUnknown=false,allowGetters=false,allowSetters=true,merge=true)",
                EMPTY.withAllowSetters()
                    .withMerge()
                    .toString());

        // hashCode of EMPTY: no precise expectation, but it should not be 0
        int hash = EMPTY.hashCode();
        if (hash == 0) {
            fail("Should not get 0 for hash");
        }
    }

    /** Builds an insertion-ordered set, mirroring how the production code stores ignored names. */
    private Set<String> setOf(String... names) {
        return new LinkedHashSet<String>(Arrays.asList(names));
    }
}
