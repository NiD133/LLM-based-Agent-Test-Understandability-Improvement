package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import org.junit.jupiter.api.Test;

import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests to verify that it is possibly to merge {@link JsonFormat.Value}
 * instances for overrides.
 */
public class JsonFormatTest
    extends AnnotationTestUtil
{
    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @JsonFormat(shape=JsonFormat.Shape.BOOLEAN, pattern="xyz", timezone="bogus")
    private final static class Bogus { }

    @Test
    public void testEmptyInstanceDefaults() {
        JsonFormat.Value empty = JsonFormat.Value.empty();
        for (Feature f : Feature.values()) {
            assertNull(empty.getFeature(f));
        }
        assertFalse(empty.hasLocale());
        assertFalse(empty.hasPattern());
        assertFalse(empty.hasShape());
        assertFalse(empty.hasTimeZone());
        assertFalse(empty.hasLenient());
        assertFalse(empty.hasNonDefaultRadix());

        assertFalse(empty.isLenient());
    }

    @Test
    public void testEquality() {
        // Identity and structural equality for EMPTY/default instances
        assertTrue(EMPTY.equals(EMPTY));
        assertTrue(new JsonFormat.Value().equals(new JsonFormat.Value()));

        // Shape-based equality: same shape → equal, different shape → not equal
        JsonFormat.Value v1 = JsonFormat.Value.forShape(Shape.BOOLEAN);
        JsonFormat.Value v2 = JsonFormat.Value.forShape(Shape.BOOLEAN);
        JsonFormat.Value v3 = JsonFormat.Value.forShape(Shape.SCALAR);

        assertTrue(v1.equals(v2));
        assertTrue(v2.equals(v1));

        assertFalse(v1.equals(v3));
        assertFalse(v3.equals(v1));
        assertFalse(v2.equals(v3));
        assertFalse(v3.equals(v2));

        // not strictly guaranteed but...
        assertFalse(v1.hashCode() == v3.hashCode());

        // Changing shape or adding pattern/feature breaks equality
        assertEquals(v1, v3.withShape(Shape.BOOLEAN));
        assertFalse(v1.equals(v1.withPattern("ZBC")));
        assertFalse(v1.equals(v1.withFeature(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)));
        assertFalse(v1.equals(v1.withoutFeature(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)));
    }

    @Test
    public void testToString() {
        assertEquals("JsonFormat.Value(pattern=,shape=STRING,lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1)",
                JsonFormat.Value.forShape(JsonFormat.Shape.STRING).toString());
        assertEquals("JsonFormat.Value(pattern=[.],shape=ANY,lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1)",
                JsonFormat.Value.forPattern("[.]").toString());
    }

    @Test
    public void testFromAnnotation()
    {
        // Trivial case first:
        assertSame(EMPTY, JsonFormat.Value.from(null));

        // then real one
        JsonFormat ann = Bogus.class.getAnnotation(JsonFormat.class);
        JsonFormat.Value v = JsonFormat.Value.from(ann);
        assertEquals("xyz", v.getPattern());
        assertEquals(JsonFormat.Shape.BOOLEAN, v.getShape());
        // note: since it's not valid, should not try access as real thing
        assertEquals("bogus", v.timeZoneAsString());

        // [annotations#316]: let's also verify JDK serializability
        byte[] b = jdkSerialize(v);
        JsonFormat.Value v2 = jdkDeserialize(b);

        assertEquals(v, v2);
    }

    @Test
    public void testSimpleMerge()
    {
        // Confirm EMPTY has no properties set
        assertFalse(EMPTY.hasLocale());
        assertFalse(EMPTY.hasPattern());
        assertFalse(EMPTY.hasShape());
        assertFalse(EMPTY.hasTimeZone());

        assertNull(EMPTY.getLocale());

        // A Value with only a pattern set
        final String TEST_PATTERN = "format-string"; // not parsed, usage varies

        JsonFormat.Value v = JsonFormat.Value.forPattern(TEST_PATTERN);
        assertTrue(v.hasPattern());
        assertEquals(TEST_PATTERN, v.getPattern());
        assertFalse(v.hasLocale());
        assertFalse(v.hasShape());
        assertFalse(v.hasTimeZone());

        // Overriding with EMPTY preserves the original pattern
        JsonFormat.Value merged = v.withOverrides(EMPTY);
        assertEquals(TEST_PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertFalse(merged.hasShape());
        assertFalse(merged.hasTimeZone());

        // minor optimization: overriding with itself has no effect
        assertSame(merged, merged.withOverrides(merged));

        // EMPTY is overridden by a non-empty Value
        merged = JsonFormat.Value.merge(EMPTY, v);
        assertEquals(TEST_PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertFalse(merged.hasShape());
        assertFalse(merged.hasTimeZone());

        // also some shortcuts:
        assertSame(merged, merged.withOverrides(null));

        // Merging pattern-only and shape-only Values: both are preserved
        final JsonFormat.Shape TEST_SHAPE = JsonFormat.Shape.NUMBER;
        JsonFormat.Value v2 = JsonFormat.Value.forShape(TEST_SHAPE);

        merged = v.withOverrides(v2);
        assertEquals(TEST_PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertEquals(TEST_SHAPE, merged.getShape());
        assertFalse(merged.hasTimeZone());

        merged = v2.withOverrides(v);
        assertEquals(TEST_PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertEquals(TEST_SHAPE, merged.getShape());
        assertFalse(merged.hasTimeZone());
    }

    @Test
    public void testMultiMerge()
    {
        final String TEST_PATTERN = "format-string"; // not parsed, usage varies
        JsonFormat.Value format2 = JsonFormat.Value.forPattern(TEST_PATTERN);
        JsonFormat.Value format3 = JsonFormat.Value.forLeniency(Boolean.FALSE);

        JsonFormat.Value merged = JsonFormat.Value.mergeAll(EMPTY, format2, format3);
        assertEquals(TEST_PATTERN, merged.getPattern());
        assertEquals(Boolean.FALSE, merged.getLenient());
    }

    /*
    /**********************************************************
    /* Test specific value properties
    /**********************************************************
     */

    @Test
    public void testLeniency() {
        // Default empty Value has no leniency set
        JsonFormat.Value empty = JsonFormat.Value.empty();
        assertFalse(empty.hasLenient());
        assertFalse(empty.isLenient());
        assertNull(empty.getLenient());

        // Explicitly enabling leniency
        JsonFormat.Value lenient = empty.withLenient(Boolean.TRUE);
        assertTrue(lenient.hasLenient());
        assertTrue(lenient.isLenient());
        assertEquals(Boolean.TRUE, lenient.getLenient());
        assertTrue(lenient.equals(lenient));
        assertFalse(empty.equals(lenient));
        assertFalse(lenient.equals(empty));

        // Setting the same value should return the same instance (no unnecessary copy)
        assertSame(lenient, lenient.withLenient(Boolean.TRUE));

        // Switching to strict (explicitly false leniency)
        JsonFormat.Value strict = lenient.withLenient(Boolean.FALSE);
        assertTrue(strict.hasLenient());
        assertFalse(strict.isLenient());
        assertEquals(Boolean.FALSE, strict.getLenient());
        assertTrue(strict.equals(strict));
        assertFalse(empty.equals(strict));
        assertFalse(strict.equals(empty));
        assertFalse(lenient.equals(strict));
        assertFalse(strict.equals(lenient));

        // Clearing the leniency setting (null) produces an equivalent to empty
        JsonFormat.Value dunno = lenient.withLenient(null);
        assertFalse(dunno.hasLenient());
        assertFalse(dunno.isLenient());
        assertNull(dunno.getLenient());
        assertTrue(empty.equals(dunno));
        assertTrue(dunno.equals(empty));
        assertFalse(lenient.equals(dunno));
        assertFalse(dunno.equals(lenient));
    }

    @Test
    public void testCaseInsensitiveValues() {
        JsonFormat.Value empty = JsonFormat.Value.empty();
        assertNull(empty.getFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES));

        JsonFormat.Value insensitive = empty.withFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES);
        assertTrue(insensitive.getFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES));

        JsonFormat.Value sensitive = empty.withoutFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES);
        assertFalse(sensitive.getFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES));
    }

    @Test
    public void testShape() {
        assertFalse(JsonFormat.Shape.STRING.isNumeric());
        assertFalse(JsonFormat.Shape.STRING.isStructured());

        assertTrue(JsonFormat.Shape.NUMBER_INT.isNumeric());
        assertTrue(JsonFormat.Shape.NUMBER_FLOAT.isNumeric());
        assertTrue(JsonFormat.Shape.NUMBER.isNumeric());

        assertTrue(JsonFormat.Shape.ARRAY.isStructured());
        assertTrue(JsonFormat.Shape.OBJECT.isStructured());
    }

    @Test
    public void testFeatures() {
        // f1 is empty; f2 enables ACCEPT_SINGLE_VALUE_AS_ARRAY and disables WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS
        JsonFormat.Features f1 = JsonFormat.Features.empty();
        JsonFormat.Features f2 = f1.with(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .without(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);

        // Equality checks
        assertTrue(f1.equals(f1));
        assertFalse(f1.equals(f2));
        assertFalse(f1.equals(null));
        assertFalse(f1.equals("foo"));

        // f1 has neither feature set; f2 has both explicitly set
        assertNull(f1.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
        assertEquals(Boolean.TRUE, f2.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));

        assertNull(f1.get(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS));
        assertEquals(Boolean.FALSE, f2.get(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS));

        // Applying f2 as overrides onto f1 propagates all explicit settings
        JsonFormat.Features f3 = f1.withOverrides(f2);
        assertEquals(Boolean.TRUE, f3.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
        assertEquals(Boolean.FALSE, f3.get(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS));

        // f4 inverts the roles: enables WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, disables ACCEPT_SINGLE_VALUE_AS_ARRAY
        JsonFormat.Features f4 = JsonFormat.Features.construct(
                new Feature[] { // enabled:
                        Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS
        }, new Feature[] { // disabled:
                Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY
        });
        assertEquals(Boolean.FALSE, f4.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
        assertEquals(Boolean.TRUE, f4.get(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS));
    }

    @Test
    public void testFeaturesWithClearsDisabled() {
        // with() after without() on same feature should result in enabled
        JsonFormat.Features f = JsonFormat.Features.empty()
                .without(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .with(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        assertEquals(Boolean.TRUE, f.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
    }

    @Test
    public void testFeaturesWithoutClearsEnabled() {
        // without() after with() on same feature should result in disabled
        JsonFormat.Features f = JsonFormat.Features.empty()
                .with(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .without(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        assertEquals(Boolean.FALSE, f.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
    }

    @Test
    public void testEqualsIgnoresTransientTimezone() {
        // Two Values created from same timezone string should be equal
        // regardless of whether getTimeZone() has been called
        JsonFormat.Value v1 = new JsonFormat.Value("", Shape.ANY, "", "UTC",
                JsonFormat.Features.empty(), null, DEFAULT_RADIX);
        JsonFormat.Value v2 = new JsonFormat.Value("", Shape.ANY, "", "UTC",
                JsonFormat.Features.empty(), null, DEFAULT_RADIX);
        // Force lazy _timezone population on v1 only
        v1.getTimeZone();
        assertEquals(v1, v2);
    }

    @Test
    public void testWithTimeZonePreservesRadix() {
        int binaryRadix = 2;
        JsonFormat.Value v = JsonFormat.Value.forRadix(binaryRadix);
        JsonFormat.Value withTz = v.withTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        assertEquals(binaryRadix, withTz.getRadix());
    }

    @Test
    public void testRadixInHashCode() {
        JsonFormat.Value v1 = JsonFormat.Value.forRadix(2);
        JsonFormat.Value v2 = JsonFormat.Value.forRadix(16);
        // Not equal, so hashCodes should (very likely) differ
        assertNotEquals(v1, v2);
        assertNotEquals(v1.hashCode(), v2.hashCode());
    }

    // [annotations#344]: Locale parsing with language, country, variant
    @Test
    public void testLocaleParsingWithCountry() {
        // Simple language-only locale
        JsonFormat.Value languageOnly = new JsonFormat.Value("", Shape.ANY, "en", "",
                JsonFormat.Features.empty(), null, DEFAULT_RADIX);
        assertEquals(new java.util.Locale("en"), languageOnly.getLocale());

        // Language + country separated by underscore
        JsonFormat.Value underscoreSeparated = new JsonFormat.Value("", Shape.ANY, "en_US", "",
                JsonFormat.Features.empty(), null, DEFAULT_RADIX);
        assertEquals(new java.util.Locale("en", "US"), underscoreSeparated.getLocale());

        // Language + country separated by hyphen
        JsonFormat.Value hyphenSeparated = new JsonFormat.Value("", Shape.ANY, "en-US", "",
                JsonFormat.Features.empty(), null, DEFAULT_RADIX);
        assertEquals(new java.util.Locale("en", "US"), hyphenSeparated.getLocale());

        // Language + country + variant
        JsonFormat.Value withVariant = new JsonFormat.Value("", Shape.ANY, "en_US_POSIX", "",
                JsonFormat.Features.empty(), null, DEFAULT_RADIX);
        assertEquals(new java.util.Locale("en", "US", "POSIX"), withVariant.getLocale());

        // German locale
        JsonFormat.Value germanLocale = new JsonFormat.Value("", Shape.ANY, "de_DE", "",
                JsonFormat.Features.empty(), null, DEFAULT_RADIX);
        assertEquals(new java.util.Locale("de", "DE"), germanLocale.getLocale());
    }

    @Test
    public void testRadix() {
        // Non-default radix overrides the default when merged
        int binaryRadix = 2;
        final JsonFormat.Value v = JsonFormat.Value.forRadix(binaryRadix);
        JsonFormat.Value merged = EMPTY.withOverrides(v);
        assertEquals(DEFAULT_RADIX, EMPTY.getRadix());
        assertEquals(binaryRadix, merged.getRadix());

        // Default radix does not override an explicitly set radix
        final JsonFormat.Value v2 = JsonFormat.Value.forRadix(binaryRadix);
        merged = v2.withOverrides(EMPTY);
        assertEquals(binaryRadix, v2.getRadix());
        assertEquals(binaryRadix, merged.getRadix());

        // withRadix() and forRadix() both store the radix correctly
        JsonFormat.Value emptyWithBinaryRadix = EMPTY.withRadix(binaryRadix);
        assertEquals(binaryRadix, emptyWithBinaryRadix.getRadix());

        JsonFormat.Value forBinaryRadix = JsonFormat.Value.forRadix(binaryRadix);
        assertEquals(binaryRadix, forBinaryRadix.getRadix());
    }
}
