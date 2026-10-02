package com.fasterxml.jackson.annotation;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests to verify that it is possible to merge {@link JsonIgnoreProperties.Value}
 * instances for overrides.
 */
public class JsonIgnorePropertiesTest
    extends AnnotationTestUtil
{
    private static final String FIRST_IGNORED_PROPERTY = "a";
    private static final String SECOND_IGNORED_PROPERTY = "b";

    @JsonIgnoreProperties(value={ "foo", "bar" }, ignoreUnknown=true)
    private final static class Bogus {
    }

    private final JsonIgnoreProperties.Value EMPTY = JsonIgnoreProperties.Value.empty();

    @Test
    public void testEmpty() {
        // ok to try to create from null; gives empty
        assertSame(EMPTY, JsonIgnoreProperties.Value.from(null));

        assertEquals(0, EMPTY.getIgnored().size());
        assertFalse(EMPTY.getAllowGetters());
        assertFalse(EMPTY.getAllowSetters());
    }

    @Test
    public void testEquality() {
        assertEquals(EMPTY, EMPTY);

        // empty has "merge" set to 'true' so:
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
        assertFalse(fromAnnotation.getMerge());
        assertFalse(fromAnnotation.getAllowGetters());
        assertFalse(fromAnnotation.getAllowSetters());

        Set<String> ignoredProperties = fromAnnotation.getIgnored();
        assertEquals(2, fromAnnotation.getIgnored().size());
        assertEquals(_set("foo", "bar"), ignoredProperties);

        // Let's also verify JDK serializability
        byte[] serialized = jdkSerialize(fromAnnotation);
        JsonIgnoreProperties.Value deserialized = jdkDeserialize(serialized);

        assertEquals(fromAnnotation, deserialized);
    }

    @Test
    public void testFactories() {
        assertSame(EMPTY, JsonIgnoreProperties.Value.forIgnoreUnknown(false));
        assertSame(EMPTY, JsonIgnoreProperties.Value.forIgnoredProperties());
        assertSame(EMPTY, JsonIgnoreProperties.Value.forIgnoredProperties(Collections.<String>emptySet()));

        JsonIgnoreProperties.Value ignoredProperties = JsonIgnoreProperties.Value.forIgnoredProperties(
                FIRST_IGNORED_PROPERTY, SECOND_IGNORED_PROPERTY);
        assertEquals(_set(FIRST_IGNORED_PROPERTY, SECOND_IGNORED_PROPERTY),
                ignoredProperties.getIgnored());

        JsonIgnoreProperties.Value allowGetters = ignoredProperties.withAllowGetters();
        assertTrue(allowGetters.getAllowGetters());
        assertFalse(allowGetters.getAllowSetters());
        assertEquals(_set(FIRST_IGNORED_PROPERTY, SECOND_IGNORED_PROPERTY),
                allowGetters.getIgnored());
        assertEquals(_set(FIRST_IGNORED_PROPERTY, SECOND_IGNORED_PROPERTY),
                allowGetters.findIgnoredForDeserialization());
        assertEquals(_set(), allowGetters.findIgnoredForSerialization());

        JsonIgnoreProperties.Value allowSetters = ignoredProperties.withAllowSetters();
        assertFalse(allowSetters.getAllowGetters());
        assertTrue(allowSetters.getAllowSetters());
        assertEquals(_set(FIRST_IGNORED_PROPERTY, SECOND_IGNORED_PROPERTY),
                allowSetters.getIgnored());
        assertEquals(_set(), allowSetters.findIgnoredForDeserialization());
        assertEquals(_set(FIRST_IGNORED_PROPERTY, SECOND_IGNORED_PROPERTY),
                allowSetters.findIgnoredForSerialization());
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
        JsonIgnoreProperties.Value mergingOverride = EMPTY.withMerge()
                .withIgnored("a");
        JsonIgnoreProperties.Value replacingOverride = EMPTY.withoutMerge();

        // when merging, should just have union of things
        JsonIgnoreProperties.Value merged = base.withOverrides(mergingOverride);
        assertEquals(Collections.singleton("a"), merged.getIgnored());
        assertTrue(merged.getIgnoreUnknown());
        assertTrue(merged.getAllowGetters());
        assertFalse(merged.getAllowSetters());

        // when NOT merging, simply replacing values
        JsonIgnoreProperties.Value replaced = JsonIgnoreProperties.Value.merge(base, replacingOverride);
        assertEquals(Collections.emptySet(), replaced.getIgnored());
        assertFalse(replaced.getIgnoreUnknown());
        assertFalse(replaced.getAllowGetters());
        assertFalse(replaced.getAllowSetters());

        // and effectively really just uses overrides as is
        assertEquals(replacingOverride, replaced);

        assertSame(replacingOverride, replacingOverride.withOverrides(null));
        assertSame(replacingOverride, replacingOverride.withOverrides(EMPTY));
    }

    @Test
    public void testMergeIgnoreProperties()
    {
        JsonIgnoreProperties.Value first = EMPTY.withIgnored("a");
        JsonIgnoreProperties.Value second = EMPTY.withIgnored("b");
        JsonIgnoreProperties.Value third = EMPTY.withIgnored("c");

        JsonIgnoreProperties.Value merged = JsonIgnoreProperties.Value.mergeAll(first, second, third);
        Set<String> allIgnoredProperties = merged.getIgnored();
        assertEquals(3, allIgnoredProperties.size());
        assertTrue(allIgnoredProperties.contains("a"));
        assertTrue(allIgnoredProperties.contains("b"));
        assertTrue(allIgnoredProperties.contains("c"));
    }

    @Test
    public void testHashCodeIncludesIgnoredContents() {
        JsonIgnoreProperties.Value first = EMPTY.withIgnored("a", "b");
        JsonIgnoreProperties.Value second = EMPTY.withIgnored("c", "d");
        assertNotEquals(first, second);
        assertNotEquals(first.hashCode(), second.hashCode());
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
