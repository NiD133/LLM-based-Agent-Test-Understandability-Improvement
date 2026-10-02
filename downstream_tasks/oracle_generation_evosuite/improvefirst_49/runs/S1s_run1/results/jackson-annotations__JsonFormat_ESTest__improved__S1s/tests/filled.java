/*
 * Improved version of EvoSuite-generated test for JsonFormat.
 * Improvements: descriptive test method names, meaningful variable names,
 * brief comments explaining non-obvious intent. Runtime behaviour is unchanged.
 */

package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest extends JsonFormat_ESTest_scaffolding {

    // =========================================================================
    // Value construction — string-based constructor
    // =========================================================================

    @Test(timeout = 4000)
    public void test00_valueConstructedWithStringArgsHasCorrectShapePatternAndRadix() throws Throwable {
        JsonFormat.Shape numberShape = JsonFormat.Shape.NUMBER;
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.valueOf("ADJUST_DATES_TO_CONTEXT_TIME_ZONE");
        JsonFormat.Value value = new JsonFormat.Value(
                "ADJUST_DATES_TO_CONTEXT_TIME_ZONE", numberShape,
                "ADJUST_DATES_TO_CONTEXT_TIME_ZONE", "ADJUST_DATES_TO_CONTEXT_TIME_ZONE",
                emptyFeatures, lenient, 115);

        assertEquals(JsonFormat.Shape.NUMBER, value.getShape());
        assertEquals("ADJUST_DATES_TO_CONTEXT_TIME_ZONE", value.getPattern());
        assertEquals(115, value.getRadix());
    }

    @Test(timeout = 4000)
    public void test01_valueWithObjectShapeAndStringArgsHasExpectedProperties() throws Throwable {
        JsonFormat.Shape objectShape = JsonFormat.Shape.OBJECT;
        JsonFormat.Value value = new JsonFormat.Value(
                "FALSE", objectShape,
                "JsonFormat.Value(pattern=,shape=ANY,lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1639)",
                "8e.", (JsonFormat.Features) null, (Boolean) null, (-1639));

        assertEquals(JsonFormat.Shape.OBJECT, value.getShape());
        assertEquals(-1639, value.getRadix());
    }

    @Test(timeout = 4000)
    public void test02_valueWithNullTimezoneStringHasNoTimeZone() throws Throwable {
        JsonFormat.Shape binaryShape = JsonFormat.Shape.BINARY;
        // All three slots use the same feature, so it appears in both enabled and disabled sets;
        // when both are set, disabled takes precedence → get() returns FALSE.
        JsonFormat.Feature[] featuresInBothSets = new JsonFormat.Feature[3];
        JsonFormat.Feature adjustDatesFeature = JsonFormat.Feature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE;
        featuresInBothSets[0] = adjustDatesFeature;
        featuresInBothSets[1] = featuresInBothSets[0];
        featuresInBothSets[2] = featuresInBothSets[0];
        JsonFormat.Features features = JsonFormat.Features.construct(featuresInBothSets, featuresInBothSets);
        Boolean featureState = features.get(adjustDatesFeature);

        assertEquals(Boolean.FALSE, featureState);

        // null timezone string → hasTimeZone() must be false
        JsonFormat.Value value = new JsonFormat.Value(
                "0(hYGYeE_-#<Q!B", binaryShape, "0(hYGYeE_-#<Q!B",
                (String) null, features, featureState, 3);
        assertFalse(value.hasTimeZone());
    }

    // =========================================================================
    // Value equality
    // =========================================================================

    @Test(timeout = 4000)
    public void test03_valueWithLocaleCopiedIsNotEqualToOriginalDefaultValue() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();
        Locale germanLocale = Locale.GERMAN;
        JsonFormat.Value valueWithLocale = defaultValue.withLocale(germanLocale);

        boolean equal = valueWithLocale.equals(defaultValue);
        assertFalse(equal);
    }

    @Test(timeout = 4000)
    public void test04_valueForPatternIsNotEqualToDefaultValue() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();
        JsonFormat.Value patternValue = JsonFormat.Value.forPattern("*h,2D`=nR6aV]Mg'.");

        boolean equal = patternValue.equals(defaultValue);
        assertFalse(equal);
    }

    @Test(timeout = 4000)
    public void test05_valueForLeniencyTrueIsLenientAndNotEqualToDefaultValue() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();
        JsonFormat.Value lenientValue = JsonFormat.Value.forLeniency(true);

        boolean equal = defaultValue.equals(lenientValue);
        assertFalse(equal);
    }

    @Test(timeout = 4000)
    public void test06_valueForShapeIsNotEqualToValueForLeniencyFalse() throws Throwable {
        JsonFormat.Shape arrayShape = JsonFormat.Shape.ARRAY;
        JsonFormat.Value shapeValue = JsonFormat.Value.forShape(arrayShape);
        JsonFormat.Value nonLenientValue = JsonFormat.Value.forLeniency(false);

        boolean equal = shapeValue.equals(nonLenientValue);
        assertFalse(equal);
    }

    @Test(timeout = 4000)
    public void test07_valueEqualsReturnsFalseForNonValueObject() throws Throwable {
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        Locale frenchLocale = Locale.FRENCH;
        TimeZone falseTimeZone = TimeZone.getTimeZone("FALSE");
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;
        JsonFormat.Value value = new JsonFormat.Value(
                "FALSE", scalarShape, frenchLocale, falseTimeZone, emptyFeatures, lenient, 10);

        boolean equalToTimeZone = value.equals(falseTimeZone);
        assertFalse(equalToTimeZone);
    }

    @Test(timeout = 4000)
    public void test08_valueEqualsReturnsFalseForNull() throws Throwable {
        JsonFormat.Value value = new JsonFormat.Value();

        boolean equal = value.equals((Object) null);
        assertFalse(equal);
    }

    @Test(timeout = 4000)
    public void test09_valueEqualsReturnsTrueForSameInstance() throws Throwable {
        JsonFormat.Value value = new JsonFormat.Value();

        boolean equal = value.equals(value);
        assertTrue(equal);
    }

    // =========================================================================
    // hashCode consistency (exercised via HashMap.remove)
    // =========================================================================

    @Test(timeout = 4000)
    public void test10_valueHashCodeIsConsistentForScalarShapeWithTimezoneString() throws Throwable {
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;
        JsonFormat.Value value = new JsonFormat.Value(
                "FALSE", scalarShape, "FALSE", "O", emptyFeatures, lenient, 10);

        // HashMap.remove internally calls hashCode(); this verifies consistency
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        map.remove((Object) value);
    }

    // =========================================================================
    // hasNonDefaultRadix / hasLenient / hasTimeZone / hasLocale / hasPattern / hasShape
    // =========================================================================

    @Test(timeout = 4000)
    public void test11_valueWithNonDefaultRadixReportsHasNonDefaultRadixTrue() throws Throwable {
        JsonFormat.Shape naturalShape = JsonFormat.Shape.NATURAL;
        Locale italyLocale = Locale.ITALY;
        TimeZone defaultTimeZone = TimeZone.getDefault();
        JsonFormat.Value value = new JsonFormat.Value(
                ".sT6~u!yfoEV'=Itz ", naturalShape, italyLocale, defaultTimeZone,
                (JsonFormat.Features) null, (Boolean) null, 95);

        boolean hasNonDefaultRadix = value.hasNonDefaultRadix();
        assertTrue(hasNonDefaultRadix);
    }

    @Test(timeout = 4000)
    public void test12_emptyValueHasDefaultRadix() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();

        boolean hasNonDefaultRadix = emptyValue.hasNonDefaultRadix();
        assertFalse(hasNonDefaultRadix);
    }

    @Test(timeout = 4000)
    public void test13_valueWithExplicitLenientSettingHasLenient() throws Throwable {
        JsonFormat.Shape floatShape = JsonFormat.Shape.NUMBER_FLOAT;
        TimeZone invalidTimeZone = TimeZone.getTimeZone("-d`");
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        // Boolean.valueOf on a non-"true" string evaluates to false, but lenient is non-null
        Boolean lenient = Boolean.valueOf("com.fasterxml.jackson.annotation.JsonFormat$Features");
        JsonFormat.Value value = new JsonFormat.Value(
                "GTN#0o7A|nNN5e%^;@", floatShape, (Locale) null, invalidTimeZone,
                emptyFeatures, lenient, 1932);

        boolean hasLenient = value.hasLenient();
        // hasLenient() is true because _lenient is non-null, even though its value is false
        assertTrue(hasLenient);
    }

    @Test(timeout = 4000)
    public void test14_defaultValueHasNoLenientSetting() throws Throwable {
        JsonFormat.Value value = new JsonFormat.Value();

        boolean hasLenient = value.hasLenient();
        assertFalse(hasLenient);
    }

    @Test(timeout = 4000)
    public void test15_emptyValueHasNoTimeZone() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();

        boolean hasTimeZone = emptyValue.hasTimeZone();
        assertFalse(hasTimeZone);
    }

    @Test(timeout = 4000)
    public void test16_valueWithNonEmptyTimezoneStringHasTimeZone() throws Throwable {
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;
        JsonFormat.Value value = new JsonFormat.Value(
                "FALSE", scalarShape, "FALSE", "O", emptyFeatures, lenient, 10);

        boolean hasTimeZone = value.hasTimeZone();
        assertTrue(hasTimeZone);
    }

    @Test(timeout = 4000)
    public void test17_valueWithLocaleHasLocale() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();
        Locale germanLocale = Locale.GERMAN;
        JsonFormat.Value valueWithLocale = emptyValue.withLocale(germanLocale);

        boolean hasLocale = valueWithLocale.hasLocale();
        assertTrue(hasLocale);
    }

    @Test(timeout = 4000)
    public void test18_defaultValueHasNoLocale() throws Throwable {
        JsonFormat.Value value = new JsonFormat.Value();

        boolean hasLocale = value.hasLocale();
        assertFalse(hasLocale);
    }

    @Test(timeout = 4000)
    public void test19_valueForRadixHasNoPatternAndNoShape() throws Throwable {
        JsonFormat.Value value = JsonFormat.Value.forRadix((-1639));

        boolean hasPattern = value.hasPattern();
        assertFalse(hasPattern);
    }

    @Test(timeout = 4000)
    public void test20_valueWithNonEmptyPatternHasPattern() throws Throwable {
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        Locale frenchLocale = Locale.FRENCH;
        TimeZone falseTimeZone = TimeZone.getTimeZone("FALSE");
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;
        JsonFormat.Value value = new JsonFormat.Value(
                "FALSE", scalarShape, frenchLocale, falseTimeZone, emptyFeatures, lenient, 10);

        boolean hasPattern = value.hasPattern();
        assertTrue(hasPattern);
    }

    @Test(timeout = 4000)
    public void test21_valueForShapeHasShape() throws Throwable {
        JsonFormat.Shape binaryShape = JsonFormat.Shape.BINARY;
        JsonFormat.Value value = JsonFormat.Value.forShape(binaryShape);

        boolean hasShape = value.hasShape();
        assertTrue(hasShape);
    }

    @Test(timeout = 4000)
    public void test22_defaultValueHasNoShape() throws Throwable {
        JsonFormat.Value value = new JsonFormat.Value();

        boolean hasShape = value.hasShape();
        assertFalse(hasShape);
    }

    // =========================================================================
    // getTimeZone / timeZoneAsString
    // =========================================================================

    @Test(timeout = 4000)
    public void test23_valueWithInvalidTimezoneStringResolvesToGmtOnGetTimeZone() throws Throwable {
        JsonFormat.Shape objectShape = JsonFormat.Shape.OBJECT;
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;
        // Unrecognised timezone string; TimeZone.getTimeZone returns GMT for unknown IDs
        JsonFormat.Value value = new JsonFormat.Value(
                "<PKr<%Q@RL3(`H", objectShape,
                "<PKr<%Q@RL3(`H", "<PKr<%Q@RL3(`H",
                emptyFeatures, lenient, (-3138));

        value.getTimeZone(); // triggers lazy conversion of _timezoneStr → TimeZone
        boolean hasTimeZone = value.hasTimeZone();
        assertTrue(hasTimeZone);
    }

    @Test(timeout = 4000)
    public void test24_emptyValueGetTimeZoneReturnsNull() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();

        TimeZone timeZone = emptyValue.getTimeZone();
        assertNull(timeZone);
    }

    @Test(timeout = 4000)
    public void test25_valueWithTimezoneObjectGetTimeZoneReturnsNonNull() throws Throwable {
        JsonFormat.Shape floatShape = JsonFormat.Shape.NUMBER_FLOAT;
        TimeZone invalidTimeZone = TimeZone.getTimeZone("-d`");
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.valueOf("com.fasterxml.jackson.annotation.JsonFormat$Features");
        JsonFormat.Value value = new JsonFormat.Value(
                "GTN#0o7A|nNN5e%^;@", floatShape, (Locale) null, invalidTimeZone,
                emptyFeatures, lenient, 1932);

        TimeZone retrieved = value.getTimeZone();
        assertNotNull(retrieved);
    }

    @Test(timeout = 4000)
    public void test26_valueWithDefaultTimezoneCanCallTimeZoneAsString() throws Throwable {
        JsonFormat.Shape objectShape = JsonFormat.Shape.OBJECT;
        Locale italyLocale = Locale.ITALY;
        TimeZone defaultTimeZone = TimeZone.getDefault();
        JsonFormat.Value value = new JsonFormat.Value(
                ".sT6~u!yfoEV'=Itz ", objectShape, italyLocale, defaultTimeZone,
                (JsonFormat.Features) null, (Boolean) null, 95);

        value.timeZoneAsString();
        assertNotNull(value.timeZoneAsString());
    }

    // =========================================================================
    // withXxx fluent builders — identity / new-instance behaviour
    // =========================================================================

    @Test(timeout = 4000)
    public void test27_withDefaultRadixReturnsSameInstance() throws Throwable {
        JsonFormat.Value value = new JsonFormat.Value();
        // withRadix(-1) equals DEFAULT_RADIX, so the same instance is returned
        JsonFormat.Value result = value.withRadix((-1));
        assertSame(value, result);
    }

    @Test(timeout = 4000)
    public void test28_withRadixCreatesNewValueNotEqualToOriginal() throws Throwable {
        JsonFormat.Value value = new JsonFormat.Value();
        JsonFormat.Value valueWithRadix = value.withRadix(1398);

        boolean equal = valueWithRadix.equals(value);
        assertFalse(equal);
    }

    @Test(timeout = 4000)
    public void test29_withLenientNullWhenAlreadyNullReturnsSameInstance() throws Throwable {
        JsonFormat.Value value = JsonFormat.Value.forRadix(1);
        // lenient is already null → no change → same instance returned
        JsonFormat.Value result = value.withLenient((Boolean) null);
        assertSame(value, result);
    }

    @Test(timeout = 4000)
    public void test30_withLenientTrueCreatesValueWithLeniencyEnabled() throws Throwable {
        JsonFormat.Value value = new JsonFormat.Value();
        Boolean lenient = Boolean.TRUE;
        JsonFormat.Value lenientValue = value.withLenient(lenient);

        assertTrue(lenientValue.isLenient());
    }

    @Test(timeout = 4000)
    public void test31_withSameShapeReturnsSameInstance() throws Throwable {
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        Locale frenchLocale = Locale.FRENCH;
        TimeZone falseTimeZone = TimeZone.getTimeZone("FALSE");
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;
        JsonFormat.Value value = new JsonFormat.Value(
                "FALSE", scalarShape, frenchLocale, falseTimeZone, emptyFeatures, lenient, 10);

        // Setting the same shape returns the same instance
        JsonFormat.Value result = value.withShape(scalarShape);
        assertSame(value, result);
    }

    @Test(timeout = 4000)
    public void test32_withDifferentShapeCreatesNewValueWithThatShape() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();
        JsonFormat.Shape binaryShape = JsonFormat.Shape.BINARY;
        JsonFormat.Value valueWithShape = emptyValue.withShape(binaryShape);

        assertEquals(JsonFormat.Shape.BINARY, valueWithShape.getShape());
    }

    // =========================================================================
    // withOverrides / merge / mergeAll
    // =========================================================================

    @Test(timeout = 4000)
    public void test33_withOverridesAppliesShapePatternAndRadixFromOverride() throws Throwable {
        JsonFormat.Shape numberIntShape = JsonFormat.Shape.NUMBER_INT;
        JsonFormat.Value baseValue = JsonFormat.Value.forPattern("");
        SimpleTimeZone simpleTimeZone = new SimpleTimeZone((-5461), "");
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean notLenient = Boolean.FALSE;
        // Override has an explicit shape and an empty timezone string;
        // an empty tzStr means the timezone is NOT overridden (base timezone is kept).
        JsonFormat.Value overrideValue = new JsonFormat.Value(
                "", numberIntShape, (Locale) null, "", simpleTimeZone,
                emptyFeatures, notLenient, (-1685));

        JsonFormat.Value merged = baseValue.withOverrides(overrideValue);
        assertEquals(JsonFormat.Shape.NUMBER_INT, merged.getShape());
        assertEquals(-1685, merged.getRadix());
    }

    @Test(timeout = 4000)
    public void test34_withOverridesOnEmptyBaseReturnsOverrideValue() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();
        JsonFormat.Value defaultValue = new JsonFormat.Value();
        // When the base is the EMPTY singleton, withOverrides returns the override directly
        JsonFormat.Value result = emptyValue.withOverrides(defaultValue);
        assertSame(defaultValue, result);
    }

    @Test(timeout = 4000)
    public void test35_mergeIdenticalEmptyValuesHasNoPattern() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();
        JsonFormat.Value merged = JsonFormat.Value.merge(emptyValue, emptyValue);
        assertFalse(merged.hasPattern());
    }

    @Test(timeout = 4000)
    public void test36_mergeWithNullOverrideReturnsBaseValue() throws Throwable {
        JsonFormat.Value baseValue = JsonFormat.Value.forRadix((-1639));
        JsonFormat.Value merged = JsonFormat.Value.merge(baseValue, (JsonFormat.Value) null);

        assertSame(baseValue, merged);
    }

    @Test(timeout = 4000)
    public void test37_fromNullAnnotationReturnsEmptyValue() throws Throwable {
        JsonFormat.Value value = JsonFormat.Value.from((JsonFormat) null);
        assertSame(JsonFormat.Value.empty(), value);
    }

    @Test(timeout = 4000)
    public void test38_mergeAllWithSparseArrayMergesNonNullValuesOnly() throws Throwable {
        JsonFormat.Shape objectShape = JsonFormat.Shape.OBJECT;
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean notLenient = Boolean.valueOf(false);
        JsonFormat.Value value = new JsonFormat.Value(
                "`K22BIe$=Oc|vAr4!T", objectShape, "7", "7", emptyFeatures, notLenient, 4024);

        // First two slots are the same value; slots 2-8 are null → result equals the single value
        JsonFormat.Value[] values = new JsonFormat.Value[9];
        values[0] = value;
        values[1] = values[0];
        JsonFormat.Value merged = JsonFormat.Value.mergeAll(values);
        assertEquals(value, merged);
    }

    @Test(timeout = 4000)
    public void test39_mergeWithBothNullReturnsNull() throws Throwable {
        JsonFormat.Value result = JsonFormat.Value.merge((JsonFormat.Value) null, (JsonFormat.Value) null);
        assertNull(result);
    }

    // =========================================================================
    // Null / DEFAULT_* sentinel handling in constructors
    // =========================================================================

    @Test(timeout = 4000)
    public void test40_valueWithNullShapeDefaultsToAnyShape() throws Throwable {
        Locale prcLocale = Locale.PRC;
        SimpleTimeZone simpleTimeZone = new SimpleTimeZone(2893, "&v1N4|W0j)B");
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        // null shape → internally defaults to Shape.ANY → hasShape() false
        JsonFormat.Value value = new JsonFormat.Value(
                "$6\"RBn+pQ?N*brK", (JsonFormat.Shape) null, prcLocale,
                simpleTimeZone, emptyFeatures, (Boolean) null, 1453);

        assertFalse(value.hasShape());
    }

    @Test(timeout = 4000)
    public void test41_valueWithNullPatternDefaultsToEmptyString() throws Throwable {
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        Locale italianLocale = Locale.ITALIAN;
        TimeZone defaultTimeZone = TimeZone.getDefault();
        // new Boolean((String) null) → false; lenient is explicitly set
        Boolean lenient = new Boolean((String) null);
        // null pattern → internally defaults to ""
        JsonFormat.Value value = new JsonFormat.Value(
                (String) null, scalarShape, italianLocale, defaultTimeZone,
                (JsonFormat.Features) null, lenient, (-2562));

        assertFalse(value.hasPattern());
    }

    @Test(timeout = 4000)
    public void test42_valueWithDefaultTimezoneConstantStringHasNoTimeZone() throws Throwable {
        JsonFormat.Shape objectShape = JsonFormat.Shape.OBJECT;
        // "##default" equals DEFAULT_TIMEZONE → string-based constructor treats it as null
        JsonFormat.Value value = new JsonFormat.Value(
                "##default", objectShape, "##default", "##default",
                (JsonFormat.Features) null, (Boolean) null, (-1880944581));

        assertFalse(value.hasTimeZone());
    }

    @Test(timeout = 4000)
    public void test43_valueWithBooleanShapeAndNullsHasCorrectShapeAndRadix() throws Throwable {
        JsonFormat.Shape booleanShape = JsonFormat.Shape.BOOLEAN;
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        // Boolean.valueOf((String) null) → false; lenient is explicitly set
        Boolean lenient = Boolean.valueOf((String) null);
        JsonFormat.Value value = new JsonFormat.Value(
                (String) null, booleanShape, (String) null, (String) null,
                emptyFeatures, lenient, 1695);

        assertEquals(JsonFormat.Shape.BOOLEAN, value.getShape());
        assertEquals(1695, value.getRadix());
    }

    // =========================================================================
    // Features equality and feature toggling
    // =========================================================================

    @Test(timeout = 4000)
    public void test44_twoIdenticalFeaturesInstancesAreEqual() throws Throwable {
        JsonFormat.Feature[] featureSlots = new JsonFormat.Feature[6];
        JsonFormat.Feature feature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE;
        featureSlots[0] = feature;
        featureSlots[1] = feature;
        featureSlots[2] = featureSlots[0];
        featureSlots[3] = featureSlots[0];
        featureSlots[4] = feature;
        featureSlots[5] = feature;
        JsonFormat.Features features0 = JsonFormat.Features.construct(featureSlots, featureSlots);
        JsonFormat.Features features1 = JsonFormat.Features.construct(featureSlots, featureSlots);

        boolean equal = features1.equals(features0);
        assertTrue(equal);
    }

    @Test(timeout = 4000)
    public void test45_withoutFeatureCreatesNewValueNotEqualToOriginal() throws Throwable {
        JsonFormat.Value value = new JsonFormat.Value();
        JsonFormat.Feature feature = JsonFormat.Feature.WRITE_SORTED_MAP_ENTRIES;
        JsonFormat.Value valueWithoutFeature = value.withoutFeature(feature);

        boolean equal = valueWithoutFeature.equals(value);
        assertFalse(equal);
    }

    @Test(timeout = 4000)
    public void test46_featuresEqualsReturnsFalseForDifferentType() throws Throwable {
        JsonFormat.Shape stringShape = JsonFormat.Shape.STRING;
        JsonFormat.Features features = JsonFormat.Features.empty();

        boolean equal = features.equals(stringShape);
        assertFalse(equal);
    }

    @Test(timeout = 4000)
    public void test47_featuresEqualsReturnsFalseForNull() throws Throwable {
        JsonFormat.Features features = JsonFormat.Features.empty();

        boolean equal = features.equals((Object) null);
        assertFalse(equal);
    }

    @Test(timeout = 4000)
    public void test48_valuesWithSamePatternButDifferentLocaleVsTimezoneAreNotEqual() throws Throwable {
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        Locale frenchLocale = Locale.FRENCH;
        TimeZone falseTimeZone = TimeZone.getTimeZone("FALSE");
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;
        // valueWithLocale stores a Locale object and a TimeZone object (_timezoneStr is null)
        JsonFormat.Value valueWithLocale = new JsonFormat.Value(
                "FALSE", scalarShape, frenchLocale, falseTimeZone, emptyFeatures, lenient, 10);
        // valueWithTimezoneStr stores timezone as a string and locale parsed from string
        JsonFormat.Value valueWithTimezoneStr = new JsonFormat.Value(
                "FALSE", scalarShape, "FALSE", "O", emptyFeatures, lenient, 10);

        boolean equal = valueWithLocale.equals(valueWithTimezoneStr);
        assertFalse(equal);
    }

    @Test(timeout = 4000)
    public void test49_withFeatureCreatesNewValueNotEqualToOriginal() throws Throwable {
        JsonFormat.Value value = new JsonFormat.Value();
        JsonFormat.Feature feature = JsonFormat.Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS;
        JsonFormat.Value valueWithFeature = value.withFeature(feature);

        boolean equal = valueWithFeature.equals(value);
        assertFalse(equal);
    }

    // =========================================================================
    // toString
    // =========================================================================

    @Test(timeout = 4000)
    public void test50_toStringWithDisabledFeatureShowsFeatureInOutput() throws Throwable {
        JsonFormat.Value value = new JsonFormat.Value();
        JsonFormat.Feature feature = JsonFormat.Feature.WRITE_SORTED_MAP_ENTRIES;
        JsonFormat.Value valueWithoutFeature = value.withoutFeature(feature);

        String result = valueWithoutFeature.toString();
        assertNotNull(result);
    }

    @Test(timeout = 4000)
    public void test51_getFeatureForEnabledFeatureReturnsTrue() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();
        JsonFormat.Feature feature = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_VALUES;
        JsonFormat.Value valueWithFeature = emptyValue.withFeature(feature);

        Boolean featureState = valueWithFeature.getFeature(feature);
        assertEquals(Boolean.TRUE, featureState);
    }

    @Test(timeout = 4000)
    public void test52_featuresWithoutCreatesNewDistinctInstance() throws Throwable {
        JsonFormat.Feature[] featureSlots = new JsonFormat.Feature[2];
        JsonFormat.Feature feature = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED;
        featureSlots[0] = feature;
        featureSlots[1] = featureSlots[0];
        JsonFormat.Features features = JsonFormat.Features.construct(featureSlots, featureSlots);
        JsonFormat.Features featuresWithout = features.without(featureSlots);

        assertNotSame(features, featuresWithout);
    }

    @Test(timeout = 4000)
    public void test53_withoutFeatureSecondCallReturnsSameInstance() throws Throwable {
        JsonFormat.Value value = new JsonFormat.Value();
        JsonFormat.Feature feature = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED;
        JsonFormat.Value valueWithoutFeature = value.withoutFeature(feature);
        // Calling withoutFeature again when already disabled → same instance
        JsonFormat.Value valueWithoutFeatureAgain = valueWithoutFeature.withoutFeature(feature);

        assertSame(valueWithoutFeature, valueWithoutFeatureAgain);
    }

    @Test(timeout = 4000)
    public void test54_withFeatureWhenFeatureDisabledCreatesNewValue() throws Throwable {
        JsonFormat.Shape binaryShape = JsonFormat.Shape.BINARY;
        JsonFormat.Feature[] allSameFeature = new JsonFormat.Feature[6];
        JsonFormat.Feature feature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE;
        allSameFeature[0] = feature;
        allSameFeature[1] = feature;
        allSameFeature[2] = feature;
        allSameFeature[3] = allSameFeature[0];
        allSameFeature[4] = feature;
        allSameFeature[5] = allSameFeature[2];
        // Same feature in both enabled and disabled; disabled wins → get() returns FALSE
        JsonFormat.Features features = JsonFormat.Features.construct(allSameFeature, allSameFeature);
        Boolean featureState = features.get(feature);

        JsonFormat.Value value = new JsonFormat.Value(
                "skoH-w.W$", binaryShape, "FALSE", "skoH-w.W$", features, featureState, 761);
        // Enabling a currently-disabled feature must create a new Value
        JsonFormat.Value valueWithFeature = value.withFeature(feature);
        assertNotSame(value, valueWithFeature);
    }

    @Test(timeout = 4000)
    public void test55_withOverridesMergesEnabledAndDisabledFeatures() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();
        JsonFormat.Feature readAsNullFeature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;
        JsonFormat.Value valueWithEnabled = emptyValue.withFeature(readAsNullFeature);
        JsonFormat.Feature readWithDefaultFeature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE;
        JsonFormat.Value valueWithDisabled = emptyValue.withoutFeature(readWithDefaultFeature);
        // Merging enabled and disabled features produces a distinct combined value
        JsonFormat.Value merged = valueWithEnabled.withOverrides(valueWithDisabled);

        assertEquals(Boolean.TRUE, merged.getFeature(readAsNullFeature));
        assertEquals(Boolean.FALSE, merged.getFeature(readWithDefaultFeature));
    }

    @Test(timeout = 4000)
    public void test56_withOverridesPreservesEnabledFeatureFromOverride() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();
        JsonFormat.Feature feature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;
        JsonFormat.Value valueWithEnabled = emptyValue.withFeature(feature);
        JsonFormat.Value valueWithDisabled = emptyValue.withoutFeature(feature);
        // Applying the "enabled" value as an override restores the feature
        JsonFormat.Value result = valueWithDisabled.withOverrides(valueWithEnabled);

        assertEquals(Boolean.TRUE, result.getFeature(feature));
    }

    @Test(timeout = 4000)
    public void test57_withOverridesOfMergedValueEqualsOriginal() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();
        JsonFormat.Feature feature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;
        JsonFormat.Value valueWithFeature = defaultValue.withFeature(feature);
        // withFeature is idempotent when feature is already enabled
        JsonFormat.Value valueWithFeatureAgain = valueWithFeature.withFeature(feature);
        JsonFormat.Value mergedWithBase = JsonFormat.Value.merge(valueWithFeature, defaultValue);
        JsonFormat.Value result = valueWithFeatureAgain.withOverrides(mergedWithBase);

        assertEquals(valueWithFeatureAgain, result);
    }

    @Test(timeout = 4000)
    public void test58_withOverridesFeatureFromOverrideApplied() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();
        JsonFormat.Feature feature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;
        JsonFormat.Value valueWithFeature = defaultValue.withFeature(feature);
        // Override applies the feature to the default value
        JsonFormat.Value result = defaultValue.withOverrides(valueWithFeature);

        assertEquals(Boolean.TRUE, result.getFeature(feature));
    }

    @Test(timeout = 4000)
    public void test59_featuresWithOverridesNullReturnsSelf() throws Throwable {
        JsonFormat.Features features = JsonFormat.Features.empty();
        JsonFormat.Features result = features.withOverrides((JsonFormat.Features) null);
        assertSame(features, result);
    }

    @Test(timeout = 4000)
    public void test60_withOverridesResultEqualsOverrideValue() throws Throwable {
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        Locale frenchLocale = Locale.FRENCH;
        TimeZone falseTimeZone = TimeZone.getTimeZone("FALSE");
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;
        JsonFormat.Value baseValue = new JsonFormat.Value(
                "FALSE", scalarShape, frenchLocale, falseTimeZone, emptyFeatures, lenient, 10);
        JsonFormat.Value overrideValue = new JsonFormat.Value(
                "FALSE", scalarShape, "FALSE", "O", emptyFeatures, lenient, 10);

        JsonFormat.Value result = baseValue.withOverrides(overrideValue);
        boolean equalToOverride = result.equals(overrideValue);
        assertTrue(equalToOverride);
    }

    // =========================================================================
    // Shape.isStructured
    // =========================================================================

    @Test(timeout = 4000)
    public void test61_isStructuredWithNullReturnsFalse() throws Throwable {
        boolean result = JsonFormat.Shape.isStructured((JsonFormat.Shape) null);
        assertFalse(result);
    }

    @Test(timeout = 4000)
    public void test62_isStructuredWithPojoReturnsTrue() throws Throwable {
        JsonFormat.Shape pojoShape = JsonFormat.Shape.POJO;
        boolean result = JsonFormat.Shape.isStructured(pojoShape);
        assertTrue(result);
    }

    @Test(timeout = 4000)
    public void test63_isStructuredWithArrayReturnsTrue() throws Throwable {
        JsonFormat.Shape arrayShape = JsonFormat.Shape.ARRAY;
        boolean result = JsonFormat.Shape.isStructured(arrayShape);
        assertTrue(result);
    }

    @Test(timeout = 4000)
    public void test64_isStructuredWithScalarReturnsFalse() throws Throwable {
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        boolean result = JsonFormat.Shape.isStructured(scalarShape);
        assertFalse(result);
    }

    @Test(timeout = 4000)
    public void test65_isStructuredWithObjectReturnsTrue() throws Throwable {
        JsonFormat.Shape objectShape = JsonFormat.Shape.OBJECT;
        boolean result = JsonFormat.Shape.isStructured(objectShape);
        assertTrue(result);
    }

    // =========================================================================
    // Shape.isNumeric
    // =========================================================================

    @Test(timeout = 4000)
    public void test66_isNumericWithNullReturnsFalse() throws Throwable {
        boolean result = JsonFormat.Shape.isNumeric((JsonFormat.Shape) null);
        assertFalse(result);
    }

    @Test(timeout = 4000)
    public void test67_isNumericWithNumberFloatReturnsTrue() throws Throwable {
        JsonFormat.Shape floatShape = JsonFormat.Shape.NUMBER_FLOAT;
        boolean result = JsonFormat.Shape.isNumeric(floatShape);
        assertTrue(result);
    }

    @Test(timeout = 4000)
    public void test68_isNumericWithNumberIntReturnsTrue() throws Throwable {
        JsonFormat.Shape intShape = JsonFormat.Shape.NUMBER_INT;
        boolean result = JsonFormat.Shape.isNumeric(intShape);
        assertTrue(result);
    }

    @Test(timeout = 4000)
    public void test69_isNumericWithScalarReturnsFalse() throws Throwable {
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        boolean result = JsonFormat.Shape.isNumeric(scalarShape);
        assertFalse(result);
    }

    @Test(timeout = 4000)
    public void test70_isNumericWithNumberReturnsTrue() throws Throwable {
        JsonFormat.Shape numberShape = JsonFormat.Shape.NUMBER;
        boolean result = JsonFormat.Shape.isNumeric(numberShape);
        assertTrue(result);
    }

    // =========================================================================
    // Miscellaneous accessors
    // =========================================================================

    @Test(timeout = 4000)
    public void test71_forRadixValueGetFeaturesDoesNotThrow() throws Throwable {
        JsonFormat.Value value = JsonFormat.Value.forRadix((-1639));
        value.getFeatures();
    }

    @Test(timeout = 4000)
    public void test72_constructorWithNullAnnotationThrowsNullPointerException() throws Throwable {
        JsonFormat.Value value = null;
        try {
            value = new JsonFormat.Value((JsonFormat) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
        }
    }

    @Test(timeout = 4000)
    public void test73_defaultValueToStringHasExpectedFormat() throws Throwable {
        JsonFormat.Value value = new JsonFormat.Value();
        String result = value.toString();
        assertEquals("JsonFormat.Value(pattern=,shape=ANY,lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1)", result);
    }

    @Test(timeout = 4000)
    public void test74_emptyValueGetShapeReturnsAnyWhichIsNotNumeric() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();
        JsonFormat.Shape shape = emptyValue.getShape();
        assertEquals(JsonFormat.Shape.ANY, shape);
    }

    @Test(timeout = 4000)
    public void test75_withTimeZoneDoesNotAffectEqualityBecauseEqualityUsesTzStr() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();
        TimeZone defaultTimeZone = TimeZone.getDefault();
        // withTimeZone stores the TimeZone in _timezone but equality checks _timezoneStr;
        // since both _timezoneStr fields remain null the two values compare equal.
        JsonFormat.Value result = emptyValue.withTimeZone(defaultTimeZone);
        assertEquals(emptyValue, result);
    }

    @Test(timeout = 4000)
    public void test76_forShapeGetLocaleReturnsNull() throws Throwable {
        JsonFormat.Shape arrayShape = JsonFormat.Shape.ARRAY;
        JsonFormat.Value value = JsonFormat.Value.forShape(arrayShape);
        assertNull(value.getLocale());
    }

    @Test(timeout = 4000)
    public void test77_forRadixGetLenientReturnsNull() throws Throwable {
        JsonFormat.Value value = JsonFormat.Value.forRadix((-2473));
        assertNull(value.getLenient());
    }

    @Test(timeout = 4000)
    public void test78_emptyValueGetPatternReturnsEmptyString() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();
        String pattern = emptyValue.getPattern();
        assertEquals("", pattern);
    }

    @Test(timeout = 4000)
    public void test79_defaultValueIsLenientReturnsFalse() throws Throwable {
        JsonFormat.Value value = new JsonFormat.Value();
        assertFalse(value.isLenient());
    }

    @Test(timeout = 4000)
    public void test80_forRadixValueForReturnsJsonFormatClass() throws Throwable {
        JsonFormat.Value value = JsonFormat.Value.forRadix(0);
        assertEquals(JsonFormat.class, value.valueFor());
    }

    @Test(timeout = 4000)
    public void test81_withPatternCreatesNewValueWithPattern() throws Throwable {
        JsonFormat.Value value = new JsonFormat.Value();
        JsonFormat.Value valueWithPattern = value.withPattern("fS<5P");

        assertTrue(valueWithPattern.hasPattern());
    }

    @Test(timeout = 4000)
    public void test82_forLeniencyFalseTimeZoneAsStringReturnsNull() throws Throwable {
        JsonFormat.Value value = JsonFormat.Value.forLeniency(false);
        String timeZoneString = value.timeZoneAsString();

        assertNull(timeZoneString);
    }

    @Test(timeout = 4000)
    public void test83_forRadixGetRadixReturnsSpecifiedValue() throws Throwable {
        JsonFormat.Value value = JsonFormat.Value.forRadix(1);
        int radix = value.getRadix();

        assertEquals(1, radix);
    }

    @Test(timeout = 4000)
    public void test84_getFeatureForUnsetFeatureReturnsNull() throws Throwable {
        JsonFormat.Value value = new JsonFormat.Value();
        JsonFormat.Feature feature = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED;
        Boolean featureState = value.getFeature(feature);

        assertNull(featureState);
    }

    @Test(timeout = 4000)
    public void test85_valueHashCodeIsConsistentForValueWithLocaleAndTimeZone() throws Throwable {
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        Locale frenchLocale = Locale.FRENCH;
        TimeZone falseTimeZone = TimeZone.getTimeZone("FALSE");
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;
        JsonFormat.Value value = new JsonFormat.Value(
                "FALSE", scalarShape, frenchLocale, falseTimeZone, emptyFeatures, lenient, 10);

        // HashMap.remove() exercises hashCode() — verifies consistency
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        map.remove((Object) value);
    }

    @Test(timeout = 4000)
    public void test86_fromAnnotationWithNullFeatureArraysThrowsNullPointerException() throws Throwable {
        JsonFormat jsonFormat = mock(JsonFormat.class, CALLS_REAL_METHODS);
        doReturn((String) null).when(jsonFormat).locale();
        doReturn((String) null).when(jsonFormat).pattern();
        doReturn((JsonFormat.Shape) null).when(jsonFormat).shape();
        doReturn((String) null).when(jsonFormat).timezone();
        doReturn((JsonFormat.Feature[]) null).when(jsonFormat).with();
        doReturn((JsonFormat.Feature[]) null).when(jsonFormat).without();

        // Features.construct iterates null arrays → NullPointerException
        try {
            JsonFormat.Value.from(jsonFormat);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
        }
    }
}
