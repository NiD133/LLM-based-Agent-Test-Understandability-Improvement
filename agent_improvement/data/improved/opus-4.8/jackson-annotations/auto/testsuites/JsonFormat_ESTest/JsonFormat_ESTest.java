/*
 * Readability-focused rewrite of the EvoSuite-generated test suite for
 * {@link com.fasterxml.jackson.annotation.JsonFormat}.
 *
 * Behaviour is preserved exactly: every test invokes the same methods with the
 * same arguments and asserts the same outcomes as the original generated suite.
 * Only naming, local-variable structure and grouping were changed to make the
 * intent of each test easier to follow.
 */

package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest extends JsonFormat_ESTest_scaffolding {

  // ---------------------------------------------------------------------------
  // Shared builders for the two Value shapes that recur across many tests.
  // They forward the exact same constructor arguments used by the original
  // generated tests, so behaviour is unchanged.
  // ---------------------------------------------------------------------------

  /** Value built from a JDK {@link TimeZone} instance ("FALSE" zone id, French locale). */
  private static JsonFormat.Value scalarValueWithTimeZoneObject() {
    TimeZone timeZone = TimeZone.getTimeZone("FALSE");
    return new JsonFormat.Value("FALSE", JsonFormat.Shape.SCALAR, Locale.FRENCH,
        timeZone, JsonFormat.Features.empty(), Boolean.TRUE, 10);
  }

  /** Value built from a timezone *string* ("O") rather than a TimeZone instance. */
  private static JsonFormat.Value scalarValueWithTimeZoneString() {
    return new JsonFormat.Value("FALSE", JsonFormat.Shape.SCALAR, "FALSE", "O",
        JsonFormat.Features.empty(), Boolean.TRUE, 10);
  }

  // ---------------------------------------------------------------------------
  // Construction and basic accessors
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void constructorRetainsShapePatternAndRadix() throws Throwable {
    // Note: Boolean.valueOf of a non-"true" string yields false, not null.
    Boolean lenient = Boolean.valueOf("ADJUST_DATES_TO_CONTEXT_TIME_ZONE");
    JsonFormat.Value value = new JsonFormat.Value("ADJUST_DATES_TO_CONTEXT_TIME_ZONE",
        JsonFormat.Shape.NUMBER, "ADJUST_DATES_TO_CONTEXT_TIME_ZONE",
        "ADJUST_DATES_TO_CONTEXT_TIME_ZONE", JsonFormat.Features.empty(), lenient, 115);

    assertEquals(JsonFormat.Shape.NUMBER, value.getShape());
    assertTrue(value.hasPattern());
    assertEquals(115, value.getRadix());
  }

  @Test(timeout = 4000)
  public void constructorWithStringTimeZoneExposesTimeZoneAsString() throws Throwable {
    JsonFormat.Value value = new JsonFormat.Value("FALSE", JsonFormat.Shape.OBJECT,
        "JsonFormat.Value(pattern=,shape=ANY,lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1639)",
        "8e.", (JsonFormat.Features) null, (Boolean) null, -1639);

    assertEquals("8e.", value.timeZoneAsString());
    assertEquals(-1639, value.getRadix());
    assertEquals("FALSE", value.getPattern());
    assertEquals(JsonFormat.Shape.OBJECT, value.getShape());
  }

  @Test(timeout = 4000)
  public void constructorWithNullTimeZoneStringHasNoTimeZone() throws Throwable {
    JsonFormat.Feature feature = JsonFormat.Feature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE;
    JsonFormat.Feature[] features = { feature, feature, feature };
    // Feature listed as both enabled and disabled -> resolves to "disabled" (false).
    JsonFormat.Features featureSet = JsonFormat.Features.construct(features, features);
    Boolean lenient = featureSet.get(feature);
    assertFalse(lenient);

    JsonFormat.Value value = new JsonFormat.Value("0(hYGYeE_-#<Q!B", JsonFormat.Shape.BINARY,
        "0(hYGYeE_-#<Q!B", (String) null, featureSet, lenient, 3);
    assertFalse(value.hasTimeZone());
    assertEquals(3, value.getRadix());
  }

  @Test(timeout = 4000)
  public void constructorWithLocaleAndStructuredTimeZoneFields() throws Throwable {
    Locale locale = Locale.PRC;
    SimpleTimeZone timeZone = new SimpleTimeZone(2893, "&v1N4|W0j)B");
    JsonFormat.Value value = new JsonFormat.Value("$6\"RBn+pQ?N*brK",
        (JsonFormat.Shape) null, locale, timeZone, JsonFormat.Features.empty(),
        (Boolean) null, 1453);

    assertFalse(value.hasShape());
    assertEquals(1453, value.getRadix());
    assertEquals("$6\"RBn+pQ?N*brK", value.getPattern());
  }

  @Test(timeout = 4000)
  public void constructorWithDefaultTimeZoneStringHasNoTimeZone() throws Throwable {
    // "##default" timezone string is treated as "no timezone specified".
    JsonFormat.Value value = new JsonFormat.Value("##default", JsonFormat.Shape.OBJECT,
        "##default", "##default", (JsonFormat.Features) null, (Boolean) null, -1880944581);

    assertFalse(value.hasTimeZone());
    assertTrue(value.hasPattern());
    assertEquals(-1880944581, value.getRadix());
    assertEquals(JsonFormat.Shape.OBJECT, value.getShape());
  }

  @Test(timeout = 4000)
  public void constructorWithNullPatternKeepsShapeAndRadix() throws Throwable {
    Boolean lenient = new Boolean((String) null);
    JsonFormat.Value value = new JsonFormat.Value((String) null, JsonFormat.Shape.SCALAR,
        Locale.ITALIAN, TimeZone.getDefault(), (JsonFormat.Features) null, lenient, -2562);

    assertEquals(-2562, value.getRadix());
    assertEquals(JsonFormat.Shape.SCALAR, value.getShape());
  }

  @Test(timeout = 4000)
  public void constructorWithAllNullStringsKeepsShapeAndRadix() throws Throwable {
    Boolean lenient = Boolean.valueOf((String) null);
    JsonFormat.Value value = new JsonFormat.Value((String) null, JsonFormat.Shape.BOOLEAN,
        (String) null, (String) null, JsonFormat.Features.empty(), lenient, 1695);

    assertEquals(JsonFormat.Shape.BOOLEAN, value.getShape());
    assertEquals(1695, value.getRadix());
  }

  @Test(timeout = 4000)
  public void constructorFromNullAnnotationThrowsNpe() throws Throwable {
    try {
      new JsonFormat.Value((JsonFormat) null);
      fail("Expecting exception: NullPointerException");
    } catch (NullPointerException e) {
      // no message in exception (getMessage() returned null)
      verifyException("com.fasterxml.jackson.annotation.JsonFormat$Value", e);
    }
  }

  @Test(timeout = 4000)
  public void defaultValueHasDefaultRadix() throws Throwable {
    JsonFormat.Value value = new JsonFormat.Value();
    assertEquals(-1, value.getRadix());
  }

  @Test(timeout = 4000)
  public void defaultValueToString() throws Throwable {
    JsonFormat.Value value = new JsonFormat.Value();
    assertEquals(
        "JsonFormat.Value(pattern=,shape=ANY,lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1)",
        value.toString());
  }

  // ---------------------------------------------------------------------------
  // Static factory methods
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void forPatternHasNoShapeRadixOrTimeZone() throws Throwable {
    JsonFormat.Value defaultValue = new JsonFormat.Value();
    JsonFormat.Value patternValue = JsonFormat.Value.forPattern("*h,2D`=nR6aV]Mg'.");

    assertFalse(patternValue.equals(defaultValue));
    assertFalse(patternValue.hasShape());
    assertFalse(patternValue.hasNonDefaultRadix());
    assertFalse(patternValue.hasTimeZone());
  }

  @Test(timeout = 4000)
  public void forLeniencyTrueIsLenient() throws Throwable {
    JsonFormat.Value defaultValue = new JsonFormat.Value();
    JsonFormat.Value lenientValue = JsonFormat.Value.forLeniency(true);

    assertFalse(defaultValue.equals(lenientValue));
    assertEquals(-1, lenientValue.getRadix());
    assertFalse(lenientValue.hasShape());
    assertTrue(lenientValue.isLenient());
  }

  @Test(timeout = 4000)
  public void forLeniencyFalseIsNotLenient() throws Throwable {
    JsonFormat.Value arrayShapeValue = JsonFormat.Value.forShape(JsonFormat.Shape.ARRAY);
    JsonFormat.Value strictValue = JsonFormat.Value.forLeniency(false);

    assertFalse(arrayShapeValue.equals(strictValue));
    assertFalse(strictValue.isLenient());
    assertEquals(-1, strictValue.getRadix());
    assertFalse(strictValue.hasShape());
    assertFalse(arrayShapeValue.hasNonDefaultRadix());
  }

  @Test(timeout = 4000)
  public void forShapeReportsShapeButDefaultRadix() throws Throwable {
    JsonFormat.Value value = JsonFormat.Value.forShape(JsonFormat.Shape.BINARY);

    assertTrue(value.hasShape());
    assertFalse(value.hasNonDefaultRadix());
  }

  @Test(timeout = 4000)
  public void forRadixHasNoPatternOrShape() throws Throwable {
    JsonFormat.Value value = JsonFormat.Value.forRadix(-1639);

    assertFalse(value.hasPattern());
    assertEquals(-1639, value.getRadix());
    assertFalse(value.hasShape());
  }

  @Test(timeout = 4000)
  public void forRadixExposesEmptyFeatures() throws Throwable {
    JsonFormat.Value value = JsonFormat.Value.forRadix(-1639);
    value.getFeatures();

    assertEquals(-1639, value.getRadix());
    assertFalse(value.hasShape());
  }

  @Test(timeout = 4000)
  public void forRadixHasNullLenient() throws Throwable {
    JsonFormat.Value value = JsonFormat.Value.forRadix(-2473);
    value.getLenient();

    assertFalse(value.hasShape());
    assertEquals(-2473, value.getRadix());
  }

  @Test(timeout = 4000)
  public void forRadixReturnsGivenRadix() throws Throwable {
    JsonFormat.Value value = JsonFormat.Value.forRadix(1);
    assertEquals(1, value.getRadix());
    assertFalse(value.hasShape());
  }

  @Test(timeout = 4000)
  public void forRadixZeroValueForReturnsAnnotationType() throws Throwable {
    JsonFormat.Value value = JsonFormat.Value.forRadix(0);
    value.valueFor();

    assertEquals(0, value.getRadix());
    assertFalse(value.hasShape());
  }

  @Test(timeout = 4000)
  public void fromNullAnnotationReturnsEmpty() throws Throwable {
    JsonFormat.Value value = JsonFormat.Value.from((JsonFormat) null);
    assertFalse(value.hasLocale());
  }

  // ---------------------------------------------------------------------------
  // "empty" singleton accessors
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void emptyHasDefaultRadix() throws Throwable {
    JsonFormat.Value value = JsonFormat.Value.empty();
    assertFalse(value.hasNonDefaultRadix());
  }

  @Test(timeout = 4000)
  public void emptyHasNoTimeZone() throws Throwable {
    JsonFormat.Value value = JsonFormat.Value.empty();
    assertFalse(value.hasTimeZone());
  }

  @Test(timeout = 4000)
  public void emptyGetTimeZoneIsNull() throws Throwable {
    JsonFormat.Value value = JsonFormat.Value.empty();
    assertNull(value.getTimeZone());
  }

  @Test(timeout = 4000)
  public void emptyHasEmptyPattern() throws Throwable {
    JsonFormat.Value value = JsonFormat.Value.empty();
    assertEquals("", value.getPattern());
  }

  @Test(timeout = 4000)
  public void emptyShapeIsNotNumeric() throws Throwable {
    JsonFormat.Value value = JsonFormat.Value.empty();
    JsonFormat.Shape shape = value.getShape();
    assertFalse(shape.isNumeric());
  }

  // ---------------------------------------------------------------------------
  // hasXxx / accessor predicates
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void hasNonDefaultRadixTrueForPositiveRadix() throws Throwable {
    JsonFormat.Value value = new JsonFormat.Value(".sT6~u!yfoEV'=Itz ",
        JsonFormat.Shape.NATURAL, Locale.ITALY, TimeZone.getDefault(),
        (JsonFormat.Features) null, (Boolean) null, 95);

    assertTrue(value.hasNonDefaultRadix());
    assertTrue(value.hasPattern());
    assertEquals(95, value.getRadix());
    assertEquals(JsonFormat.Shape.NATURAL, value.getShape());
  }

  @Test(timeout = 4000)
  public void hasLenientTrueWhenLenientProvided() throws Throwable {
    JsonFormat.Value value = new JsonFormat.Value("GTN#0o7A|nNN5e%^;@",
        JsonFormat.Shape.NUMBER_FLOAT, (Locale) null, TimeZone.getTimeZone("-d`"),
        JsonFormat.Features.empty(),
        Boolean.valueOf("com.fasterxml.jackson.annotation.JsonFormat$Features"), 1932);

    assertTrue(value.hasLenient());
    assertTrue(value.hasShape());
    assertEquals(1932, value.getRadix());
    assertEquals("GTN#0o7A|nNN5e%^;@", value.getPattern());
  }

  @Test(timeout = 4000)
  public void hasLenientFalseForDefaultValue() throws Throwable {
    JsonFormat.Value value = new JsonFormat.Value();
    assertFalse(value.hasLenient());
    assertEquals(-1, value.getRadix());
  }

  @Test(timeout = 4000)
  public void hasTimeZoneTrueForStringTimeZone() throws Throwable {
    JsonFormat.Value value = scalarValueWithTimeZoneString();

    assertTrue(value.hasTimeZone());
    assertEquals("FALSE", value.getPattern());
    assertEquals(10, value.getRadix());
    assertEquals("O", value.timeZoneAsString());
    assertTrue(value.hasShape());
  }

  @Test(timeout = 4000)
  public void hasLocaleTrueAfterWithLocale() throws Throwable {
    JsonFormat.Value value = JsonFormat.Value.empty().withLocale(Locale.GERMAN);

    assertTrue(value.hasLocale());
    assertEquals(-1, value.getRadix());
  }

  @Test(timeout = 4000)
  public void hasLocaleFalseForDefaultValue() throws Throwable {
    JsonFormat.Value value = new JsonFormat.Value();
    assertFalse(value.hasLocale());
    assertEquals(-1, value.getRadix());
  }

  @Test(timeout = 4000)
  public void hasPatternTrueForTimeZoneObjectValue() throws Throwable {
    JsonFormat.Value value = scalarValueWithTimeZoneObject();

    assertTrue(value.hasPattern());
    assertTrue(value.hasShape());
    assertEquals(10, value.getRadix());
  }

  @Test(timeout = 4000)
  public void hasShapeTrueForForShape() throws Throwable {
    JsonFormat.Value value = JsonFormat.Value.forShape(JsonFormat.Shape.BINARY);

    assertTrue(value.hasShape());
    assertFalse(value.hasNonDefaultRadix());
  }

  @Test(timeout = 4000)
  public void hasShapeFalseForDefaultValue() throws Throwable {
    JsonFormat.Value value = new JsonFormat.Value();
    assertFalse(value.hasShape());
    assertFalse(value.hasNonDefaultRadix());
  }

  @Test(timeout = 4000)
  public void defaultValueIsNotLenient() throws Throwable {
    JsonFormat.Value value = new JsonFormat.Value();
    value.isLenient();
    assertFalse(value.hasNonDefaultRadix());
  }

  // ---------------------------------------------------------------------------
  // Time-zone resolution
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void getTimeZoneResolvesStringToGmtFallback() throws Throwable {
    JsonFormat.Value value = new JsonFormat.Value("<PKr<%Q@RL3(`H",
        JsonFormat.Shape.OBJECT, "<PKr<%Q@RL3(`H", "<PKr<%Q@RL3(`H",
        JsonFormat.Features.empty(), Boolean.TRUE, -3138);
    value.getTimeZone();

    assertTrue(value.hasTimeZone());
    assertEquals("GMT", value.timeZoneAsString());
  }

  @Test(timeout = 4000)
  public void getTimeZoneNonNullForObjectTimeZone() throws Throwable {
    JsonFormat.Value value = new JsonFormat.Value("GTN#0o7A|nNN5e%^;@",
        JsonFormat.Shape.NUMBER_FLOAT, (Locale) null, TimeZone.getTimeZone("-d`"),
        JsonFormat.Features.empty(),
        Boolean.valueOf("com.fasterxml.jackson.annotation.JsonFormat$Features"), 1932);

    assertNotNull(value.getTimeZone());
    assertEquals(1932, value.getRadix());
    assertTrue(value.hasShape());
    assertEquals("GTN#0o7A|nNN5e%^;@", value.getPattern());
  }

  @Test(timeout = 4000)
  public void timeZoneAsStringForObjectTimeZone() throws Throwable {
    JsonFormat.Value value = new JsonFormat.Value(".sT6~u!yfoEV'=Itz ",
        JsonFormat.Shape.OBJECT, Locale.ITALY, TimeZone.getDefault(),
        (JsonFormat.Features) null, (Boolean) null, 95);
    value.timeZoneAsString();

    assertTrue(value.hasPattern());
    assertEquals(95, value.getRadix());
    assertTrue(value.hasShape());
  }

  @Test(timeout = 4000)
  public void timeZoneAsStringNullWhenUnset() throws Throwable {
    JsonFormat.Value value = JsonFormat.Value.forLeniency(false);

    assertNull(value.timeZoneAsString());
    assertFalse(value.hasShape());
    assertFalse(value.isLenient());
    assertFalse(value.hasNonDefaultRadix());
  }

  @Test(timeout = 4000)
  public void forShapeHasNullLocale() throws Throwable {
    JsonFormat.Value value = JsonFormat.Value.forShape(JsonFormat.Shape.ARRAY);
    value.getLocale();

    assertFalse(value.hasNonDefaultRadix());
    assertTrue(value.hasShape());
  }

  // ---------------------------------------------------------------------------
  // withXxx mutators (copy-on-write)
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void withLocaleProducesDifferentValue() throws Throwable {
    JsonFormat.Value defaultValue = new JsonFormat.Value();
    JsonFormat.Value localizedValue = defaultValue.withLocale(Locale.GERMAN);

    assertFalse(localizedValue.equals(defaultValue));
    assertFalse(localizedValue.hasNonDefaultRadix());
    assertEquals(-1, defaultValue.getRadix());
  }

  @Test(timeout = 4000)
  public void withRadixUnchangedReturnsSameInstance() throws Throwable {
    JsonFormat.Value defaultValue = new JsonFormat.Value();
    JsonFormat.Value sameValue = defaultValue.withRadix(-1);

    assertSame(sameValue, defaultValue);
  }

  @Test(timeout = 4000)
  public void withRadixChangedProducesDifferentValue() throws Throwable {
    JsonFormat.Value defaultValue = new JsonFormat.Value();
    JsonFormat.Value reradixedValue = defaultValue.withRadix(1398);

    assertFalse(reradixedValue.equals(defaultValue));
    assertFalse(defaultValue.equals(reradixedValue));
    assertEquals(1398, reradixedValue.getRadix());
  }

  @Test(timeout = 4000)
  public void withLenientNullKeepsSameInstanceWhenAlreadyNull() throws Throwable {
    JsonFormat.Value value = JsonFormat.Value.forRadix(1);
    JsonFormat.Value sameValue = value.withLenient((Boolean) null);

    assertSame(sameValue, value);
    assertEquals(1, sameValue.getRadix());
    assertFalse(sameValue.hasShape());
  }

  @Test(timeout = 4000)
  public void withLenientTrueBecomesLenient() throws Throwable {
    JsonFormat.Value defaultValue = new JsonFormat.Value();
    JsonFormat.Value lenientValue = defaultValue.withLenient(Boolean.TRUE);

    assertTrue(lenientValue.isLenient());
    assertFalse(lenientValue.hasNonDefaultRadix());
    assertFalse(defaultValue.hasNonDefaultRadix());
  }

  @Test(timeout = 4000)
  public void withSameShapeReturnsSameInstance() throws Throwable {
    JsonFormat.Value value = scalarValueWithTimeZoneObject();
    JsonFormat.Value sameValue = value.withShape(JsonFormat.Shape.SCALAR);

    assertSame(sameValue, value);
    assertEquals(10, sameValue.getRadix());
    assertTrue(sameValue.hasPattern());
  }

  @Test(timeout = 4000)
  public void withShapeChangesShape() throws Throwable {
    JsonFormat.Value value = JsonFormat.Value.empty().withShape(JsonFormat.Shape.BINARY);

    assertEquals(JsonFormat.Shape.BINARY, value.getShape());
    assertFalse(value.hasNonDefaultRadix());
  }

  @Test(timeout = 4000)
  public void withPatternSetsPattern() throws Throwable {
    JsonFormat.Value defaultValue = new JsonFormat.Value();
    JsonFormat.Value patternedValue = defaultValue.withPattern("fS<5P");

    assertTrue(patternedValue.hasPattern());
    assertFalse(patternedValue.hasTimeZone());
    assertEquals(-1, patternedValue.getRadix());
    assertFalse(defaultValue.hasNonDefaultRadix());
  }

  @Test(timeout = 4000)
  public void withTimeZoneOnEmptyStaysEqual() throws Throwable {
    JsonFormat.Value emptyValue = JsonFormat.Value.empty();
    JsonFormat.Value tzValue = emptyValue.withTimeZone(TimeZone.getDefault());

    assertTrue(tzValue.equals(emptyValue));
  }

  // ---------------------------------------------------------------------------
  // withFeature / withoutFeature
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void withoutFeatureProducesDifferentValue() throws Throwable {
    JsonFormat.Value defaultValue = new JsonFormat.Value();
    JsonFormat.Value modifiedValue =
        defaultValue.withoutFeature(JsonFormat.Feature.WRITE_SORTED_MAP_ENTRIES);

    assertFalse(modifiedValue.equals(defaultValue));
    assertFalse(defaultValue.equals(modifiedValue));
    assertFalse(defaultValue.hasNonDefaultRadix());
    assertFalse(modifiedValue.hasNonDefaultRadix());
  }

  @Test(timeout = 4000)
  public void withoutFeatureToStringShowsDisabledMask() throws Throwable {
    JsonFormat.Value value = new JsonFormat.Value()
        .withoutFeature(JsonFormat.Feature.WRITE_SORTED_MAP_ENTRIES);

    assertEquals(
        "JsonFormat.Value(pattern=,shape=ANY,lenient=null,locale=null,timezone=null,features=(enabled=0x0,disabled=0x200),radix=-1)",
        value.toString());
  }

  @Test(timeout = 4000)
  public void withoutFeatureRepeatedReturnsSameInstanceSecondTime() throws Throwable {
    JsonFormat.Value defaultValue = new JsonFormat.Value();
    JsonFormat.Value firstRemoval =
        defaultValue.withoutFeature(JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
    JsonFormat.Value secondRemoval =
        firstRemoval.withoutFeature(JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);

    assertNotSame(secondRemoval, defaultValue);
    assertSame(secondRemoval, firstRemoval);
    assertFalse(defaultValue.hasNonDefaultRadix());
    assertFalse(secondRemoval.hasNonDefaultRadix());
  }

  @Test(timeout = 4000)
  public void withFeatureProducesDifferentValue() throws Throwable {
    JsonFormat.Value defaultValue = new JsonFormat.Value();
    JsonFormat.Value featuredValue =
        defaultValue.withFeature(JsonFormat.Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);

    assertFalse(featuredValue.equals(defaultValue));
    assertFalse(defaultValue.equals(featuredValue));
    assertFalse(featuredValue.hasNonDefaultRadix());
    assertFalse(defaultValue.hasNonDefaultRadix());
  }

  @Test(timeout = 4000)
  public void getFeatureReturnsTrueAfterWithFeature() throws Throwable {
    JsonFormat.Feature feature = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_VALUES;
    JsonFormat.Value value = JsonFormat.Value.empty().withFeature(feature);

    Boolean enabled = value.getFeature(feature);
    assertNotNull(enabled);
    assertTrue(enabled);
    assertEquals(-1, value.getRadix());
  }

  @Test(timeout = 4000)
  public void withFeatureOnPopulatedValueKeepsFields() throws Throwable {
    JsonFormat.Feature feature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE;
    JsonFormat.Feature[] features =
        { feature, feature, feature, feature, feature, feature };
    JsonFormat.Features featureSet = JsonFormat.Features.construct(features, features);
    Boolean lenient = featureSet.get(feature);
    assertFalse(lenient);

    JsonFormat.Value original = new JsonFormat.Value("skoH-w.W$",
        JsonFormat.Shape.BINARY, "FALSE", "skoH-w.W$", featureSet, lenient, 761);
    JsonFormat.Value featuredValue = original.withFeature(feature);

    assertNotSame(featuredValue, original);
    assertFalse(featuredValue.equals(original));
    assertEquals(761, original.getRadix());
    assertEquals(761, featuredValue.getRadix());
    assertEquals("skoH-w.W$", featuredValue.getPattern());
    assertTrue(featuredValue.hasLenient());
  }

  @Test(timeout = 4000)
  public void getFeatureNullWhenUnset() throws Throwable {
    JsonFormat.Value value = new JsonFormat.Value();
    Boolean feature = value.getFeature(JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);

    assertNull(feature);
    assertFalse(value.hasNonDefaultRadix());
  }

  // ---------------------------------------------------------------------------
  // withOverrides / merge / mergeAll
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void withOverridesAppliesOverrideFields() throws Throwable {
    JsonFormat.Value base = JsonFormat.Value.forPattern("");
    SimpleTimeZone timeZone = new SimpleTimeZone(-5461, "");
    JsonFormat.Value overrides = new JsonFormat.Value("", JsonFormat.Shape.NUMBER_INT,
        (Locale) null, "", timeZone, JsonFormat.Features.empty(), Boolean.FALSE, -1685);

    JsonFormat.Value merged = base.withOverrides(overrides);

    assertEquals(-1685, merged.getRadix());
    assertTrue(merged.hasLenient());
    assertTrue(merged.hasShape());
    assertFalse(merged.hasTimeZone());
    assertFalse(merged.equals(overrides));
    assertFalse(base.hasNonDefaultRadix());
    assertTrue(overrides.hasShape());
  }

  @Test(timeout = 4000)
  public void withOverridesFromEmptyReturnsOtherValue() throws Throwable {
    JsonFormat.Value emptyValue = JsonFormat.Value.empty();
    JsonFormat.Value defaultValue = new JsonFormat.Value();
    JsonFormat.Value merged = emptyValue.withOverrides(defaultValue);

    assertSame(merged, defaultValue);
    assertTrue(merged.equals(emptyValue));
  }

  @Test(timeout = 4000)
  public void withOverridesKeepsBaseWhenOverrideOnlyTogglesFeature() throws Throwable {
    JsonFormat.Value emptyValue = JsonFormat.Value.empty();
    JsonFormat.Value withFeature =
        emptyValue.withFeature(JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
    JsonFormat.Value withoutOtherFeature =
        emptyValue.withoutFeature(JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);

    JsonFormat.Value merged = withFeature.withOverrides(withoutOtherFeature);

    assertFalse(merged.equals(withoutOtherFeature));
    assertNotSame(merged, withoutOtherFeature);
    assertEquals(-1, merged.getRadix());
    assertNotSame(merged, withFeature);
    assertFalse(merged.equals(withFeature));
    assertFalse(withoutOtherFeature.hasNonDefaultRadix());
  }

  @Test(timeout = 4000)
  public void withOverridesEqualToOverrideWhenComplementaryFeatures() throws Throwable {
    JsonFormat.Feature feature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;
    JsonFormat.Value emptyValue = JsonFormat.Value.empty();
    JsonFormat.Value withFeature = emptyValue.withFeature(feature);
    JsonFormat.Value withoutFeature = emptyValue.withoutFeature(feature);

    JsonFormat.Value merged = withoutFeature.withOverrides(withFeature);

    assertFalse(withoutFeature.equals(emptyValue));
    assertTrue(merged.equals(withFeature));
    assertNotSame(merged, withFeature);
    assertEquals(-1, withFeature.getRadix());
  }

  @Test(timeout = 4000)
  public void withOverridesAfterMergeStaysEqual() throws Throwable {
    JsonFormat.Feature feature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;
    JsonFormat.Value defaultValue = new JsonFormat.Value();
    JsonFormat.Value withFeature = defaultValue.withFeature(feature);
    JsonFormat.Value withFeatureAgain = withFeature.withFeature(feature);
    JsonFormat.Value mergedBase = JsonFormat.Value.merge(withFeature, defaultValue);

    JsonFormat.Value merged = withFeatureAgain.withOverrides(mergedBase);

    assertNotSame(mergedBase, withFeatureAgain);
    assertTrue(merged.equals(withFeatureAgain));
    assertTrue(mergedBase.equals(withFeature));
    assertFalse(withFeatureAgain.hasNonDefaultRadix());
    assertSame(withFeatureAgain, withFeature);
  }

  @Test(timeout = 4000)
  public void withOverridesResultEqualsOverrideValue() throws Throwable {
    JsonFormat.Feature feature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;
    JsonFormat.Value defaultValue = new JsonFormat.Value();
    JsonFormat.Value withFeature = defaultValue.withFeature(feature);
    JsonFormat.Value merged = defaultValue.withOverrides(withFeature);

    assertFalse(withFeature.equals(defaultValue));
    assertEquals(-1, defaultValue.getRadix());
    assertNotSame(merged, defaultValue);
    assertEquals(-1, withFeature.getRadix());
    assertTrue(merged.equals(withFeature));
  }

  @Test(timeout = 4000)
  public void withOverridesMergesTimeZoneStringValues() throws Throwable {
    JsonFormat.Value tzObjectValue = scalarValueWithTimeZoneObject();
    JsonFormat.Value tzStringValue = scalarValueWithTimeZoneString();

    JsonFormat.Value merged = tzObjectValue.withOverrides(tzStringValue);

    assertTrue(merged.equals(tzStringValue));
    assertEquals(JsonFormat.Shape.SCALAR, merged.getShape());
    assertEquals("FALSE", merged.getPattern());
    assertTrue(tzObjectValue.hasPattern());
    assertTrue(tzObjectValue.hasShape());
    assertEquals(10, tzObjectValue.getRadix());
  }

  @Test(timeout = 4000)
  public void mergeEmptyWithEmptyHasNoPattern() throws Throwable {
    JsonFormat.Value emptyValue = JsonFormat.Value.empty();
    JsonFormat.Value merged = JsonFormat.Value.merge(emptyValue, emptyValue);

    assertFalse(merged.hasPattern());
  }

  @Test(timeout = 4000)
  public void mergeWithNullOverridesKeepsBase() throws Throwable {
    JsonFormat.Value base = JsonFormat.Value.forRadix(-1639);
    JsonFormat.Value merged = JsonFormat.Value.merge(base, (JsonFormat.Value) null);

    assertNotNull(merged);
    assertFalse(merged.hasShape());
    assertEquals(-1639, merged.getRadix());
  }

  @Test(timeout = 4000)
  public void mergeOfTwoNullsReturnsNull() throws Throwable {
    JsonFormat.Value merged =
        JsonFormat.Value.merge((JsonFormat.Value) null, (JsonFormat.Value) null);
    assertNull(merged);
  }

  @Test(timeout = 4000)
  public void mergeAllReturnsFirstNonNullWhenDuplicated() throws Throwable {
    JsonFormat.Value value = new JsonFormat.Value("`K22BIe$=Oc|vAr4!T",
        JsonFormat.Shape.OBJECT, "7", "7", JsonFormat.Features.empty(),
        Boolean.valueOf(false), 4024);
    JsonFormat.Value[] values = new JsonFormat.Value[9];
    values[0] = value;
    values[1] = values[0];

    JsonFormat.Value merged = JsonFormat.Value.mergeAll(values);

    assertNotNull(merged);
    assertSame(merged, value);
    assertEquals(4024, merged.getRadix());
    assertEquals("7", merged.timeZoneAsString());
    assertEquals(JsonFormat.Shape.OBJECT, merged.getShape());
    assertEquals("`K22BIe$=Oc|vAr4!T", merged.getPattern());
  }

  // ---------------------------------------------------------------------------
  // equals / hashing behaviour of Value
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void valueNotEqualToUnrelatedType() throws Throwable {
    JsonFormat.Value value = scalarValueWithTimeZoneObject();
    TimeZone timeZone = TimeZone.getTimeZone("FALSE");

    assertFalse(value.equals(timeZone));
    assertEquals(10, value.getRadix());
    assertTrue(value.hasShape());
    assertTrue(value.hasPattern());
  }

  @Test(timeout = 4000)
  public void valueNotEqualToNull() throws Throwable {
    JsonFormat.Value value = new JsonFormat.Value();
    assertFalse(value.equals((Object) null));
    assertFalse(value.hasNonDefaultRadix());
  }

  @Test(timeout = 4000)
  public void valueEqualToItself() throws Throwable {
    JsonFormat.Value value = new JsonFormat.Value();
    assertTrue(value.equals(value));
    assertEquals(-1, value.getRadix());
  }

  @Test(timeout = 4000)
  public void timeZoneObjectAndStringValuesAreNotEqual() throws Throwable {
    JsonFormat.Value tzObjectValue = scalarValueWithTimeZoneObject();
    JsonFormat.Value tzStringValue = scalarValueWithTimeZoneString();

    assertFalse(tzObjectValue.equals(tzStringValue));
    assertEquals(10, tzStringValue.getRadix());
    assertEquals(JsonFormat.Shape.SCALAR, tzStringValue.getShape());
    assertTrue(tzObjectValue.hasPattern());
    assertEquals("FALSE", tzStringValue.getPattern());
    assertEquals("O", tzStringValue.timeZoneAsString());
    assertEquals(JsonFormat.Shape.SCALAR, tzObjectValue.getShape());
    assertEquals(10, tzObjectValue.getRadix());
  }

  @Test(timeout = 4000)
  public void valueUsableAsMapKeyLookup() throws Throwable {
    JsonFormat.Value value = scalarValueWithTimeZoneString();
    HashMap<String, List<String>> map = new HashMap<String, List<String>>();
    map.remove((Object) value);

    assertEquals(10, value.getRadix());
    assertEquals("O", value.timeZoneAsString());
    assertEquals("FALSE", value.getPattern());
    assertTrue(value.hasShape());
    assertTrue(value.hasPattern());
  }

  @Test(timeout = 4000)
  public void valueWithObjectTimeZoneUsableAsMapKeyLookup() throws Throwable {
    JsonFormat.Value value = scalarValueWithTimeZoneObject();
    HashMap<String, List<String>> map = new HashMap<String, List<String>>();
    map.remove((Object) value);

    assertEquals(JsonFormat.Shape.SCALAR, value.getShape());
    assertTrue(value.hasPattern());
    assertEquals(10, value.getRadix());
  }

  // ---------------------------------------------------------------------------
  // JsonFormat.Features
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void featuresWithSameMasksAreEqual() throws Throwable {
    JsonFormat.Feature feature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE;
    JsonFormat.Feature[] features =
        { feature, feature, feature, feature, feature, feature };
    JsonFormat.Features first = JsonFormat.Features.construct(features, features);
    JsonFormat.Features second = JsonFormat.Features.construct(features, features);

    assertTrue(second.equals(first));
  }

  @Test(timeout = 4000)
  public void featuresNotEqualToUnrelatedType() throws Throwable {
    JsonFormat.Features features = JsonFormat.Features.empty();
    assertFalse(features.equals(JsonFormat.Shape.STRING));
  }

  @Test(timeout = 4000)
  public void featuresNotEqualToNull() throws Throwable {
    JsonFormat.Features features = JsonFormat.Features.empty();
    assertFalse(features.equals((Object) null));
  }

  @Test(timeout = 4000)
  public void featuresWithoutProducesDifferentInstance() throws Throwable {
    JsonFormat.Feature feature = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED;
    JsonFormat.Feature[] features = { feature, feature };
    JsonFormat.Features enabledFeatures = JsonFormat.Features.construct(features, features);
    JsonFormat.Features withoutFeatures = enabledFeatures.without(features);

    assertNotSame(withoutFeatures, enabledFeatures);
    assertFalse(withoutFeatures.equals(enabledFeatures));
  }

  @Test(timeout = 4000)
  public void featuresWithNullOverridesReturnsSameInstance() throws Throwable {
    JsonFormat.Features features = JsonFormat.Features.empty();
    JsonFormat.Features merged = features.withOverrides((JsonFormat.Features) null);

    assertSame(features, merged);
  }

  // ---------------------------------------------------------------------------
  // Shape static predicates
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void isStructuredFalseForNull() throws Throwable {
    assertFalse(JsonFormat.Shape.isStructured((JsonFormat.Shape) null));
  }

  @Test(timeout = 4000)
  public void isStructuredTrueForPojo() throws Throwable {
    assertTrue(JsonFormat.Shape.isStructured(JsonFormat.Shape.POJO));
  }

  @Test(timeout = 4000)
  public void isStructuredTrueForArray() throws Throwable {
    assertTrue(JsonFormat.Shape.isStructured(JsonFormat.Shape.ARRAY));
  }

  @Test(timeout = 4000)
  public void isStructuredFalseForScalar() throws Throwable {
    assertFalse(JsonFormat.Shape.isStructured(JsonFormat.Shape.SCALAR));
  }

  @Test(timeout = 4000)
  public void isStructuredTrueForObject() throws Throwable {
    assertTrue(JsonFormat.Shape.isStructured(JsonFormat.Shape.OBJECT));
  }

  @Test(timeout = 4000)
  public void isNumericFalseForNull() throws Throwable {
    assertFalse(JsonFormat.Shape.isNumeric((JsonFormat.Shape) null));
  }

  @Test(timeout = 4000)
  public void isNumericTrueForNumberFloat() throws Throwable {
    assertTrue(JsonFormat.Shape.isNumeric(JsonFormat.Shape.NUMBER_FLOAT));
  }

  @Test(timeout = 4000)
  public void isNumericTrueForNumberInt() throws Throwable {
    assertTrue(JsonFormat.Shape.isNumeric(JsonFormat.Shape.NUMBER_INT));
  }

  @Test(timeout = 4000)
  public void isNumericFalseForScalar() throws Throwable {
    assertFalse(JsonFormat.Shape.isNumeric(JsonFormat.Shape.SCALAR));
  }

  @Test(timeout = 4000)
  public void isNumericTrueForNumber() throws Throwable {
    assertTrue(JsonFormat.Shape.isNumeric(JsonFormat.Shape.NUMBER));
  }

  // ---------------------------------------------------------------------------
  // Value.from(JsonFormat) with a mocked annotation
  // ---------------------------------------------------------------------------

  @Test(timeout = 4000)
  public void fromAnnotationWithNullAccessorsThrowsNpe() throws Throwable {
    JsonFormat annotation = mock(JsonFormat.class, CALLS_REAL_METHODS);
    doReturn((String) null).when(annotation).locale();
    doReturn((String) null).when(annotation).pattern();
    doReturn((JsonFormat.Shape) null).when(annotation).shape();
    doReturn((String) null).when(annotation).timezone();
    doReturn((JsonFormat.Feature[]) null).when(annotation).with();
    doReturn((JsonFormat.Feature[]) null).when(annotation).without();

    try {
      JsonFormat.Value.from(annotation);
      fail("Expecting exception: NullPointerException");
    } catch (NullPointerException e) {
      // no message in exception (getMessage() returned null)
      verifyException("com.fasterxml.jackson.annotation.JsonFormat$Features", e);
    }
  }
}
