package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.TimeZone;

import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JsonFormat.Value} and {@link JsonFormat.Features}, especially
 * merging behavior used by format overrides.
 */
public class JsonFormatTest
    extends AnnotationTestUtil
{
    private static final JsonFormat.Value EMPTY = JsonFormat.Value.empty();
    private static final JsonFormat.Features NO_FEATURES = JsonFormat.Features.empty();

    private static final String FORMAT_PATTERN = "format-string";
    private static final int BINARY_RADIX = 2;

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
        assertTrue(EMPTY.equals(EMPTY));
        assertTrue(new JsonFormat.Value().equals(new JsonFormat.Value()));

        JsonFormat.Value booleanShape = JsonFormat.Value.forShape(Shape.BOOLEAN);
        JsonFormat.Value sameBooleanShape = JsonFormat.Value.forShape(Shape.BOOLEAN);
        JsonFormat.Value scalarShape = JsonFormat.Value.forShape(Shape.SCALAR);

        assertTrue(booleanShape.equals(sameBooleanShape));
        assertTrue(sameBooleanShape.equals(booleanShape));

        assertFalse(booleanShape.equals(scalarShape));
        assertFalse(scalarShape.equals(booleanShape));
        assertFalse(sameBooleanShape.equals(scalarShape));
        assertFalse(scalarShape.equals(sameBooleanShape));

        // Not strictly guaranteed, but the existing implementation distinguishes them.
        assertFalse(booleanShape.hashCode() == scalarShape.hashCode());

        assertEquals(booleanShape, scalarShape.withShape(Shape.BOOLEAN));
        assertFalse(booleanShape.equals(booleanShape.withPattern("ZBC")));
        assertFalse(booleanShape.equals(booleanShape.withFeature(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)));
        assertFalse(booleanShape.equals(booleanShape.withoutFeature(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)));
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
        assertSame(EMPTY, JsonFormat.Value.from(null));

        JsonFormat ann = Bogus.class.getAnnotation(JsonFormat.class);
        JsonFormat.Value value = JsonFormat.Value.from(ann);

        assertEquals("xyz", value.getPattern());
        assertEquals(JsonFormat.Shape.BOOLEAN, value.getShape());
        // Invalid time zone strings should be retained as raw strings.
        assertEquals("bogus", value.timeZoneAsString());

        // [annotations#316]: verify JDK serializability.
        byte[] serialized = jdkSerialize(value);
        JsonFormat.Value deserialized = jdkDeserialize(serialized);

        assertEquals(value, deserialized);
    }

    @Test
    public void testSimpleMerge()
    {
        assertMissingFormatSettings(EMPTY);
        assertNull(EMPTY.getLocale());

        JsonFormat.Value patternOnly = JsonFormat.Value.forPattern(FORMAT_PATTERN);
        assertTrue(patternOnly.hasPattern());
        assertEquals(FORMAT_PATTERN, patternOnly.getPattern());
        assertFalse(patternOnly.hasLocale());
        assertFalse(patternOnly.hasShape());
        assertFalse(patternOnly.hasTimeZone());

        JsonFormat.Value merged = patternOnly.withOverrides(EMPTY);
        assertEquals(FORMAT_PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertFalse(merged.hasShape());
        assertFalse(merged.hasTimeZone());

        // Minor optimization: overriding with itself has no effect.
        assertSame(merged, merged.withOverrides(merged));

        merged = JsonFormat.Value.merge(EMPTY, patternOnly);
        assertEquals(FORMAT_PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertFalse(merged.hasShape());
        assertFalse(merged.hasTimeZone());

        assertSame(merged, merged.withOverrides(null));

        final JsonFormat.Shape TEST_SHAPE = JsonFormat.Shape.NUMBER;
        JsonFormat.Value shapeOnly = JsonFormat.Value.forShape(TEST_SHAPE);

        merged = patternOnly.withOverrides(shapeOnly);
        assertEquals(FORMAT_PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertEquals(TEST_SHAPE, merged.getShape());
        assertFalse(merged.hasTimeZone());

        merged = shapeOnly.withOverrides(patternOnly);
        assertEquals(FORMAT_PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertEquals(TEST_SHAPE, merged.getShape());
        assertFalse(merged.hasTimeZone());
    }

    @Test
    public void testMultiMerge()
    {
        JsonFormat.Value format2 = JsonFormat.Value.forPattern(FORMAT_PATTERN);
        JsonFormat.Value format3 = JsonFormat.Value.forLeniency(Boolean.FALSE);

        JsonFormat.Value merged = JsonFormat.Value.mergeAll(EMPTY, format2, format3);
        assertEquals(FORMAT_PATTERN, merged.getPattern());
        assertEquals(Boolean.FALSE, merged.getLenient());
    }

    @Test
    public void testLeniency() {
        JsonFormat.Value empty = JsonFormat.Value.empty();
        assertFalse(empty.hasLenient());
        assertFalse(empty.isLenient());
        assertNull(empty.getLenient());

        JsonFormat.Value lenient = empty.withLenient(Boolean.TRUE);
        assertTrue(lenient.hasLenient());
        assertTrue(lenient.isLenient());
        assertEquals(Boolean.TRUE, lenient.getLenient());
        assertTrue(lenient.equals(lenient));
        assertFalse(empty.equals(lenient));
        assertFalse(lenient.equals(empty));

        assertSame(lenient, lenient.withLenient(Boolean.TRUE));

        JsonFormat.Value strict = lenient.withLenient(Boolean.FALSE);
        assertTrue(strict.hasLenient());
        assertFalse(strict.isLenient());
        assertEquals(Boolean.FALSE, strict.getLenient());
        assertTrue(strict.equals(strict));
        assertFalse(empty.equals(strict));
        assertFalse(strict.equals(empty));
        assertFalse(lenient.equals(strict));
        assertFalse(strict.equals(lenient));

        JsonFormat.Value unspecified = lenient.withLenient(null);
        assertFalse(unspecified.hasLenient());
        assertFalse(unspecified.isLenient());
        assertNull(unspecified.getLenient());
        assertTrue(empty.equals(unspecified));
        assertTrue(unspecified.equals(empty));
        assertFalse(lenient.equals(unspecified));
        assertFalse(unspecified.equals(lenient));
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
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        JsonFormat.Features configuredFeatures = emptyFeatures.with(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .without(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);

        assertTrue(emptyFeatures.equals(emptyFeatures));
        assertFalse(emptyFeatures.equals(configuredFeatures));
        assertFalse(emptyFeatures.equals(null));
        assertFalse(emptyFeatures.equals("foo"));

        assertNull(emptyFeatures.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
        assertEquals(Boolean.TRUE, configuredFeatures.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));

        assertNull(emptyFeatures.get(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS));
        assertEquals(Boolean.FALSE, configuredFeatures.get(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS));

        JsonFormat.Features mergedFeatures = emptyFeatures.withOverrides(configuredFeatures);
        assertEquals(Boolean.TRUE, mergedFeatures.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
        assertEquals(Boolean.FALSE, mergedFeatures.get(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS));

        JsonFormat.Features reversedFeatures = JsonFormat.Features.construct(
                new Feature[] {
                        Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS
        }, new Feature[] {
                Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY
        });
        assertEquals(Boolean.FALSE, reversedFeatures.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
        assertEquals(Boolean.TRUE, reversedFeatures.get(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS));
    }

    @Test
    void testFeaturesWithClearsDisabled() {
        JsonFormat.Features f = JsonFormat.Features.empty()
                .without(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .with(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        assertEquals(Boolean.TRUE, f.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
    }

    @Test
    void testFeaturesWithoutClearsEnabled() {
        JsonFormat.Features f = JsonFormat.Features.empty()
                .with(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .without(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        assertEquals(Boolean.FALSE, f.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
    }

    @Test
    void testEqualsIgnoresTransientTimezone() {
        JsonFormat.Value v1 = valueWithRawLocaleAndTimezone("", "UTC");
        JsonFormat.Value v2 = valueWithRawLocaleAndTimezone("", "UTC");

        // Force lazy _timezone population on v1 only.
        v1.getTimeZone();
        assertEquals(v1, v2);
    }

    @Test
    void testWithTimeZonePreservesRadix() {
        JsonFormat.Value v = JsonFormat.Value.forRadix(BINARY_RADIX);
        JsonFormat.Value withTz = v.withTimeZone(TimeZone.getTimeZone("UTC"));
        assertEquals(BINARY_RADIX, withTz.getRadix());
    }

    @Test
    void testRadixInHashCode() {
        JsonFormat.Value v1 = JsonFormat.Value.forRadix(2);
        JsonFormat.Value v2 = JsonFormat.Value.forRadix(16);

        assertNotEquals(v1, v2);
        assertNotEquals(v1.hashCode(), v2.hashCode());
    }

    // [annotations#344]: Locale parsing with language, country, variant.
    @Test
    void testLocaleParsingWithCountry() {
        JsonFormat.Value v = valueWithRawLocaleAndTimezone("en", "");
        assertEquals(new Locale("en"), v.getLocale());

        v = valueWithRawLocaleAndTimezone("en_US", "");
        assertEquals(new Locale("en", "US"), v.getLocale());

        v = valueWithRawLocaleAndTimezone("en-US", "");
        assertEquals(new Locale("en", "US"), v.getLocale());

        v = valueWithRawLocaleAndTimezone("en_US_POSIX", "");
        assertEquals(new Locale("en", "US", "POSIX"), v.getLocale());

        v = valueWithRawLocaleAndTimezone("de_DE", "");
        assertEquals(new Locale("de", "DE"), v.getLocale());
    }

    @Test
    void testRadix() {
        JsonFormat.Value v = JsonFormat.Value.forRadix(BINARY_RADIX);
        JsonFormat.Value merged = EMPTY.withOverrides(v);
        assertEquals(DEFAULT_RADIX, EMPTY.getRadix());
        assertEquals(BINARY_RADIX, merged.getRadix());

        JsonFormat.Value v2 = JsonFormat.Value.forRadix(BINARY_RADIX);
        merged = v2.withOverrides(EMPTY);
        assertEquals(BINARY_RADIX, v2.getRadix());
        assertEquals(BINARY_RADIX, merged.getRadix());

        JsonFormat.Value emptyWithBinaryRadix = EMPTY.withRadix(BINARY_RADIX);
        assertEquals(BINARY_RADIX, emptyWithBinaryRadix.getRadix());

        JsonFormat.Value forBinaryRadix = JsonFormat.Value.forRadix(BINARY_RADIX);
        assertEquals(BINARY_RADIX, forBinaryRadix.getRadix());
    }

    private void assertMissingFormatSettings(JsonFormat.Value value) {
        assertFalse(value.hasLocale());
        assertFalse(value.hasPattern());
        assertFalse(value.hasShape());
        assertFalse(value.hasTimeZone());
    }

    private JsonFormat.Value valueWithRawLocaleAndTimezone(String locale, String timezone) {
        return new JsonFormat.Value("", Shape.ANY, locale, timezone,
                NO_FEATURES, null, DEFAULT_RADIX);
    }
}
