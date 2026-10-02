package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Features;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonFormat.Value;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.TimeZone;

import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JsonFormat.Value}: the immutable, copy-on-write holder that
 * captures the configuration of a {@link JsonFormat} annotation. The tests
 * cover construction, "with*" copy methods, merging/overriding of two values,
 * equality/hashCode, and the individual properties (shape, leniency, locale,
 * timezone, radix and feature flags).
 */
public class JsonFormatTest
    extends AnnotationTestUtil
{
    /** Shared "no settings configured" value; many tests merge against this. */
    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    /**
     * A type annotated with a fully-populated {@link JsonFormat}, used to verify
     * that a {@link JsonFormat.Value} can be built from a real annotation.
     * The timezone is intentionally invalid ("bogus") to confirm the value is
     * stored verbatim without being validated.
     */
    @JsonFormat(shape = Shape.BOOLEAN, pattern = "xyz", timezone = "bogus")
    private final static class FullyConfigured { }

    /*
    /**********************************************************
    /* Construction / defaults
    /**********************************************************
     */

    @Test
    public void testEmptyInstanceDefaults() {
        JsonFormat.Value empty = JsonFormat.Value.empty();

        // An empty value carries no configuration for any feature...
        for (Feature feature : Feature.values()) {
            assertNull(empty.getFeature(feature));
        }
        // ...nor for any of the optional properties.
        assertFalse(empty.hasLocale());
        assertFalse(empty.hasPattern());
        assertFalse(empty.hasShape());
        assertFalse(empty.hasTimeZone());
        assertFalse(empty.hasLenient());
        assertFalse(empty.hasNonDefaultRadix());

        // "not configured" means leniency reads as false.
        assertFalse(empty.isLenient());
    }

    /*
    /**********************************************************
    /* Equality / hashCode / toString
    /**********************************************************
     */

    @Test
    public void testEquality() {
        // Reflexive equality, and two independently-built empty values match.
        assertTrue(EMPTY.equals(EMPTY));
        assertTrue(new JsonFormat.Value().equals(new JsonFormat.Value()));

        JsonFormat.Value booleanShape      = JsonFormat.Value.forShape(Shape.BOOLEAN);
        JsonFormat.Value sameBooleanShape  = JsonFormat.Value.forShape(Shape.BOOLEAN);
        JsonFormat.Value scalarShape       = JsonFormat.Value.forShape(Shape.SCALAR);

        // Same shape -> equal (symmetrically).
        assertTrue(booleanShape.equals(sameBooleanShape));
        assertTrue(sameBooleanShape.equals(booleanShape));

        // Different shape -> not equal (symmetrically).
        assertFalse(booleanShape.equals(scalarShape));
        assertFalse(scalarShape.equals(booleanShape));
        assertFalse(sameBooleanShape.equals(scalarShape));
        assertFalse(scalarShape.equals(sameBooleanShape));

        // Differing values are expected (though not strictly guaranteed) to
        // have differing hash codes.
        assertFalse(booleanShape.hashCode() == scalarShape.hashCode());

        // Changing the scalar value's shape to BOOLEAN makes it equal again.
        assertEquals(booleanShape, scalarShape.withShape(Shape.BOOLEAN));

        // Any other change to a value breaks equality with the original.
        assertFalse(booleanShape.equals(booleanShape.withPattern("ZBC")));
        assertFalse(booleanShape.equals(booleanShape.withFeature(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)));
        assertFalse(booleanShape.equals(booleanShape.withoutFeature(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)));
    }

    @Test
    public void testToString() {
        assertEquals(
                "JsonFormat.Value(pattern=,shape=STRING,lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1)",
                JsonFormat.Value.forShape(Shape.STRING).toString());
        assertEquals(
                "JsonFormat.Value(pattern=[.],shape=ANY,lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1)",
                JsonFormat.Value.forPattern("[.]").toString());
    }

    /*
    /**********************************************************
    /* Building from an annotation (+ JDK serialization)
    /**********************************************************
     */

    @Test
    public void testFromAnnotation()
    {
        // A null annotation maps to the shared EMPTY singleton.
        assertSame(EMPTY, JsonFormat.Value.from(null));

        // A real annotation maps to a value carrying its settings verbatim.
        JsonFormat annotation = FullyConfigured.class.getAnnotation(JsonFormat.class);
        JsonFormat.Value value = JsonFormat.Value.from(annotation);
        assertEquals("xyz", value.getPattern());
        assertEquals(Shape.BOOLEAN, value.getShape());
        // Timezone is kept as the raw string; the invalid "bogus" id is not validated.
        assertEquals("bogus", value.timeZoneAsString());

        // [annotations#316]: a Value round-trips through JDK serialization unchanged.
        byte[] serialized = jdkSerialize(value);
        JsonFormat.Value deserialized = jdkDeserialize(serialized);
        assertEquals(value, deserialized);
    }

    /*
    /**********************************************************
    /* Merging / overriding values
    /**********************************************************
     */

    @Test
    public void testSimpleMerge()
    {
        // Baseline: the empty value really is empty.
        assertFalse(EMPTY.hasLocale());
        assertFalse(EMPTY.hasPattern());
        assertFalse(EMPTY.hasShape());
        assertFalse(EMPTY.hasTimeZone());
        assertNull(EMPTY.getLocale());

        // A pattern-only value: only the pattern is set.
        final String PATTERN = "format-string"; // not parsed here, usage varies
        JsonFormat.Value patternValue = JsonFormat.Value.forPattern(PATTERN);
        assertTrue(patternValue.hasPattern());
        assertEquals(PATTERN, patternValue.getPattern());
        assertFalse(patternValue.hasLocale());
        assertFalse(patternValue.hasShape());
        assertFalse(patternValue.hasTimeZone());

        // Overriding with EMPTY changes nothing.
        JsonFormat.Value merged = patternValue.withOverrides(EMPTY);
        assertEquals(PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertFalse(merged.hasShape());
        assertFalse(merged.hasTimeZone());

        // Optimization: overriding a value with itself returns the same instance.
        assertSame(merged, merged.withOverrides(merged));

        // Merging EMPTY (base) with the pattern value (overrides) yields the pattern.
        merged = JsonFormat.Value.merge(EMPTY, patternValue);
        assertEquals(PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertFalse(merged.hasShape());
        assertFalse(merged.hasTimeZone());

        // Optimization: overriding with null returns the same instance.
        assertSame(merged, merged.withOverrides(null));

        // Merging pattern + shape combines both, regardless of which side is base.
        final JsonFormat.Shape SHAPE = Shape.NUMBER;
        JsonFormat.Value shapeValue = JsonFormat.Value.forShape(SHAPE);

        merged = patternValue.withOverrides(shapeValue);
        assertEquals(PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertEquals(SHAPE, merged.getShape());
        assertFalse(merged.hasTimeZone());

        merged = shapeValue.withOverrides(patternValue);
        assertEquals(PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertEquals(SHAPE, merged.getShape());
        assertFalse(merged.hasTimeZone());
    }

    @Test
    public void testMultiMerge()
    {
        final String PATTERN = "format-string"; // not parsed here, usage varies
        JsonFormat.Value patternValue = JsonFormat.Value.forPattern(PATTERN);
        JsonFormat.Value strictValue  = JsonFormat.Value.forLeniency(Boolean.FALSE);

        // mergeAll layers the values left-to-right; each contributes its own setting.
        JsonFormat.Value merged = JsonFormat.Value.mergeAll(EMPTY, patternValue, strictValue);
        assertEquals(PATTERN, merged.getPattern());
        assertEquals(Boolean.FALSE, merged.getLenient());
    }

    /*
    /**********************************************************
    /* Individual value properties
    /**********************************************************
     */

    @Test
    public void testLeniency() {
        JsonFormat.Value empty = JsonFormat.Value.empty();
        // Unset leniency: not present, reads false, getter returns null.
        assertFalse(empty.hasLenient());
        assertFalse(empty.isLenient());
        assertNull(empty.getLenient());

        // Enabling leniency.
        JsonFormat.Value lenient = empty.withLenient(Boolean.TRUE);
        assertTrue(lenient.hasLenient());
        assertTrue(lenient.isLenient());
        assertEquals(Boolean.TRUE, lenient.getLenient());
        assertTrue(lenient.equals(lenient));
        assertFalse(empty.equals(lenient));
        assertFalse(lenient.equals(empty));

        // Setting the same value again returns the same instance (no needless copy).
        assertSame(lenient, lenient.withLenient(Boolean.TRUE));

        // Disabling leniency.
        JsonFormat.Value strict = lenient.withLenient(Boolean.FALSE);
        assertTrue(strict.hasLenient());
        assertFalse(strict.isLenient());
        assertEquals(Boolean.FALSE, strict.getLenient());
        assertTrue(strict.equals(strict));
        assertFalse(empty.equals(strict));
        assertFalse(strict.equals(empty));
        assertFalse(lenient.equals(strict));
        assertFalse(strict.equals(lenient));

        // Clearing leniency (null) makes the value equal to empty again.
        JsonFormat.Value cleared = lenient.withLenient(null);
        assertFalse(cleared.hasLenient());
        assertFalse(cleared.isLenient());
        assertNull(cleared.getLenient());
        assertTrue(empty.equals(cleared));
        assertTrue(cleared.equals(empty));
        assertFalse(lenient.equals(cleared));
        assertFalse(cleared.equals(lenient));
    }

    @Test
    public void testCaseInsensitiveValues() {
        JsonFormat.Value empty = JsonFormat.Value.empty();
        // Feature is unset by default.
        assertNull(empty.getFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES));

        // Explicitly enabling and disabling the feature.
        JsonFormat.Value insensitive = empty.withFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES);
        assertTrue(insensitive.getFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES));

        JsonFormat.Value sensitive = empty.withoutFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES);
        assertFalse(sensitive.getFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES));
    }

    @Test
    public void testShape() {
        // STRING is neither numeric nor structured.
        assertFalse(Shape.STRING.isNumeric());
        assertFalse(Shape.STRING.isStructured());

        // The numeric shapes.
        assertTrue(Shape.NUMBER_INT.isNumeric());
        assertTrue(Shape.NUMBER_FLOAT.isNumeric());
        assertTrue(Shape.NUMBER.isNumeric());

        // The structured shapes.
        assertTrue(Shape.ARRAY.isStructured());
        assertTrue(Shape.OBJECT.isStructured());
    }

    /*
    /**********************************************************
    /* Feature set (JsonFormat.Features)
    /**********************************************************
     */

    @Test
    public void testFeatures() {
        JsonFormat.Features empty = JsonFormat.Features.empty();
        // Enable one feature and disable another.
        JsonFormat.Features configured = empty
                .with(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .without(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);

        // Equality basics.
        assertTrue(empty.equals(empty));
        assertFalse(empty.equals(configured));
        assertFalse(empty.equals(null));
        assertFalse(empty.equals("foo"));

        // Empty has no opinion; configured reflects the explicit settings.
        assertNull(empty.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
        assertEquals(Boolean.TRUE, configured.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));

        assertNull(empty.get(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS));
        assertEquals(Boolean.FALSE, configured.get(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS));

        // Overriding empty with the configured set adopts its settings.
        JsonFormat.Features overridden = empty.withOverrides(configured);
        assertEquals(Boolean.TRUE, overridden.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
        assertEquals(Boolean.FALSE, overridden.get(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS));

        // construct(enabled[], disabled[]) sets the two features the opposite way.
        JsonFormat.Features swapped = JsonFormat.Features.construct(
                new Feature[] { Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS }, // enabled
                new Feature[] { Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY });        // disabled
        assertEquals(Boolean.FALSE, swapped.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
        assertEquals(Boolean.TRUE, swapped.get(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS));
    }

    @Test
    void testFeaturesWithClearsDisabled() {
        // with() after without() on the same feature leaves it enabled.
        JsonFormat.Features features = JsonFormat.Features.empty()
                .without(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .with(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        assertEquals(Boolean.TRUE, features.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
    }

    @Test
    void testFeaturesWithoutClearsEnabled() {
        // without() after with() on the same feature leaves it disabled.
        JsonFormat.Features features = JsonFormat.Features.empty()
                .with(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .without(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        assertEquals(Boolean.FALSE, features.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
    }

    /*
    /**********************************************************
    /* Timezone, radix and locale specifics
    /**********************************************************
     */

    @Test
    void testEqualsIgnoresTransientTimezone() {
        // Two values built from the same timezone string must stay equal even
        // after one of them lazily resolves its transient TimeZone instance.
        JsonFormat.Value resolved = new JsonFormat.Value("", Shape.ANY, "", "UTC",
                JsonFormat.Features.empty(), null, DEFAULT_RADIX);
        JsonFormat.Value unresolved = new JsonFormat.Value("", Shape.ANY, "", "UTC",
                JsonFormat.Features.empty(), null, DEFAULT_RADIX);

        // Force lazy _timezone population on one value only.
        resolved.getTimeZone();
        assertEquals(resolved, unresolved);
    }

    @Test
    void testWithTimeZonePreservesRadix() {
        final int BINARY_RADIX = 2;
        JsonFormat.Value value = JsonFormat.Value.forRadix(BINARY_RADIX);

        // Setting a timezone must not reset the previously configured radix.
        JsonFormat.Value withTimeZone = value.withTimeZone(TimeZone.getTimeZone("UTC"));
        assertEquals(BINARY_RADIX, withTimeZone.getRadix());
    }

    @Test
    void testRadixInHashCode() {
        JsonFormat.Value binary = JsonFormat.Value.forRadix(2);
        JsonFormat.Value hex    = JsonFormat.Value.forRadix(16);

        // Different radices -> unequal values and (very likely) unequal hashes.
        assertNotEquals(binary, hex);
        assertNotEquals(binary.hashCode(), hex.hashCode());
    }

    // [annotations#344]: Locale parsing with language, country and variant.
    @Test
    void testLocaleParsingWithCountry() {
        // Language only.
        assertEquals(new Locale("en"), localeValue("en").getLocale());

        // Language + country, accepting either underscore or hyphen as separator.
        assertEquals(new Locale("en", "US"), localeValue("en_US").getLocale());
        assertEquals(new Locale("en", "US"), localeValue("en-US").getLocale());

        // Language + country + variant.
        assertEquals(new Locale("en", "US", "POSIX"), localeValue("en_US_POSIX").getLocale());

        // Another language + country pair.
        assertEquals(new Locale("de", "DE"), localeValue("de_DE").getLocale());
    }

    @Test
    void testRadix() {
        final int BINARY_RADIX = 2;

        // A non-default radix overrides the (default-radix) empty base.
        final JsonFormat.Value binary = JsonFormat.Value.forRadix(BINARY_RADIX);
        JsonFormat.Value merged = EMPTY.withOverrides(binary);
        assertEquals(DEFAULT_RADIX, EMPTY.getRadix());
        assertEquals(BINARY_RADIX, merged.getRadix());

        // Overriding a non-default radix with the default leaves it unchanged.
        final JsonFormat.Value binaryBase = JsonFormat.Value.forRadix(BINARY_RADIX);
        merged = binaryBase.withOverrides(EMPTY);
        assertEquals(BINARY_RADIX, binaryBase.getRadix());
        assertEquals(BINARY_RADIX, merged.getRadix());

        // Both withRadix(...) and forRadix(...) record the requested radix.
        assertEquals(BINARY_RADIX, EMPTY.withRadix(BINARY_RADIX).getRadix());
        assertEquals(BINARY_RADIX, JsonFormat.Value.forRadix(BINARY_RADIX).getRadix());
    }

    /*
    /**********************************************************
    /* Helper methods
    /**********************************************************
     */

    /** Builds a {@link JsonFormat.Value} whose only setting is the given locale string. */
    private static JsonFormat.Value localeValue(String localeStr) {
        return new JsonFormat.Value("", Shape.ANY, localeStr, "",
                JsonFormat.Features.empty(), null, DEFAULT_RADIX);
    }
}
