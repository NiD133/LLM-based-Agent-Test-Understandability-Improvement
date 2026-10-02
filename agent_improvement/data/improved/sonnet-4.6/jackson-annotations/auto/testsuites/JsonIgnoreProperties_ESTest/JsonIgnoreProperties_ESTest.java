package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.LinkedHashSet;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest extends JsonIgnoreProperties_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void mergeAll_whenArrayContainsMixedNullsAndExplicitValues_returnsCombinedFlags() throws Throwable {
      JsonIgnoreProperties.Value[] values = new JsonIgnoreProperties.Value[6];

      // Slot 4: value with all flags enabled
      Set<String> emptyIgnored = JsonIgnoreProperties.Value.empty().getIgnored();
      JsonIgnoreProperties.Value allFlagsEnabled = new JsonIgnoreProperties.Value(emptyIgnored, true, true, true, true);

      // Slot 3: value created from 8 string array (ignoreUnknown=false, merge=true, allowSetters=false)
      String[] eightNulls = new String[8];
      JsonIgnoreProperties.Value fromEightProps = JsonIgnoreProperties.Value.forIgnoredProperties(eightNulls);
      assertFalse(fromEightProps.getIgnoreUnknown());
      assertTrue(fromEightProps.getMerge());
      assertFalse(fromEightProps.getAllowSetters());

      values[3] = fromEightProps;
      values[4] = allFlagsEnabled;

      // mergeAll should combine: allFlagsEnabled propagates allowGetters, allowSetters, ignoreUnknown
      JsonIgnoreProperties.Value merged = JsonIgnoreProperties.Value.mergeAll(values);
      assertTrue(merged.getAllowGetters());
      assertTrue(merged.getAllowSetters());
      assertTrue(merged.getIgnoreUnknown());
      assertNotNull(merged);
      assertFalse(merged.equals((Object) allFlagsEnabled));
      assertNotSame(merged, allFlagsEnabled);
  }

  @Test(timeout = 4000)
  public void merge_whenOverrideHasIgnoreUnknown_resultEqualsOverride() throws Throwable {
      String[] sixNulls = new String[6];
      JsonIgnoreProperties.Value base = JsonIgnoreProperties.Value.forIgnoredProperties(sixNulls);
      JsonIgnoreProperties.Value withIgnoreUnknown = base.withIgnoreUnknown();

      JsonIgnoreProperties.Value result = JsonIgnoreProperties.Value.merge(base, withIgnoreUnknown);
      assertTrue(result.equals((Object) withIgnoreUnknown));
      assertFalse(result.getAllowGetters());
      assertTrue(result.getIgnoreUnknown());
      assertTrue(base.getMerge());
      assertFalse(result.getAllowSetters());
      assertNotSame(result, withIgnoreUnknown);
      assertFalse(base.getAllowSetters());
  }

  @Test(timeout = 4000)
  public void withIgnored_emptyStringArray_returnsSameInstance() throws Throwable {
      JsonIgnoreProperties.Value defaultValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);
      String[] emptyArray = new String[0];
      JsonIgnoreProperties.Value result = defaultValue.withIgnored(emptyArray);
      assertSame(result, defaultValue);
  }

  @Test(timeout = 4000)
  public void withOverrides_whenBaseIsDefault_resultEqualsOverride() throws Throwable {
      JsonIgnoreProperties.Value base = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);
      LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
      JsonIgnoreProperties.Value override = JsonIgnoreProperties.Value.construct(emptySet, false, true, true, true);

      JsonIgnoreProperties.Value result = base.withOverrides(override);
      assertTrue(result.getAllowGetters());
      assertNotSame(result, override);
      assertTrue(result.equals((Object) override));
      assertFalse(result.getIgnoreUnknown());
      assertTrue(override.getAllowSetters());
  }

  @Test(timeout = 4000)
  public void withIgnored_whenIgnoredSetMatchesExisting_equalsOriginal() throws Throwable {
      JsonIgnoreProperties jsonIgnoreProperties0 = mock(JsonIgnoreProperties.class, CALLS_REAL_METHODS);
      doReturn(false).when(jsonIgnoreProperties0).allowGetters();
      doReturn(false).when(jsonIgnoreProperties0).allowSetters();
      doReturn(false).when(jsonIgnoreProperties0).ignoreUnknown();
      doReturn((String[]) null).when(jsonIgnoreProperties0).value();

      JsonIgnoreProperties.Value fromAnnotation = JsonIgnoreProperties.Value.from(jsonIgnoreProperties0);
      Set<String> ignoredForSerialization = fromAnnotation.findIgnoredForSerialization();
      JsonIgnoreProperties.Value withSameIgnored = fromAnnotation.withIgnored(ignoredForSerialization);

      boolean isEqual = fromAnnotation.equals(withSameIgnored);
      assertTrue(isEqual);
      assertFalse(withSameIgnored.getMerge());
  }

  @Test(timeout = 4000)
  public void withAllowSetters_onEmptyValue_createsDistinctValueWithAllowSetters() throws Throwable {
      JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();
      JsonIgnoreProperties.Value withSetters = emptyValue.withAllowSetters();

      boolean emptyEqualsWithSetters = emptyValue.equals(withSetters);
      assertFalse(withSetters.getIgnoreUnknown());
      assertTrue(withSetters.getMerge());
      assertFalse(withSetters.getAllowGetters());
      assertFalse(withSetters.equals((Object) emptyValue));
      assertFalse(emptyEqualsWithSetters);
  }

  @Test(timeout = 4000)
  public void equals_withNullArgument_returnsFalse() throws Throwable {
      JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct((Set<String>) null, true, true, false, false);

      boolean result = value.equals((Object) null);
      assertFalse(value.getMerge());
      assertFalse(value.getAllowSetters());
      assertTrue(value.getIgnoreUnknown());
      assertFalse(result);
      assertTrue(value.getAllowGetters());
  }

  @Test(timeout = 4000)
  public void equals_withSameInstance_returnsTrue() throws Throwable {
      JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();
      boolean result = emptyValue.equals(emptyValue);
      assertTrue(result);
  }

  @Test(timeout = 4000)
  public void withoutMerge_changesMergeFlagAndMakesValueUnequalToOriginal() throws Throwable {
      LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
      JsonIgnoreProperties.Value withMerge = JsonIgnoreProperties.Value.construct(emptySet, false, false, false, true);
      JsonIgnoreProperties.Value withoutMerge = withMerge.withoutMerge();

      LinkedHashSet<Object> unusedSet = new LinkedHashSet<Object>();
      unusedSet.contains(withoutMerge);

      assertFalse(withMerge.getIgnoreUnknown());
      assertFalse(withoutMerge.getIgnoreUnknown());
      assertFalse(withoutMerge.getMerge());
      assertFalse(withoutMerge.getAllowSetters());
      assertFalse(withoutMerge.getAllowGetters());
      assertFalse(withMerge.equals((Object) withoutMerge));
  }

  @Test(timeout = 4000)
  public void withAllowSetters_whenCurrentlyFalse_createsNewValueWithAllowSetters() throws Throwable {
      LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
      JsonIgnoreProperties.Value withoutSetters = JsonIgnoreProperties.Value.construct(emptySet, false, false, false, true);

      LinkedHashSet<Object> unusedSet = new LinkedHashSet<Object>();
      JsonIgnoreProperties.Value withSetters = withoutSetters.withAllowSetters();
      unusedSet.contains(withSetters);

      assertFalse(withSetters.getIgnoreUnknown());
      assertFalse(withSetters.getAllowGetters());
      assertTrue(withSetters.getAllowSetters());
      assertTrue(withSetters.getMerge());
  }

  @Test(timeout = 4000)
  public void construct_withIgnoreUnknownAndAllowGetters_hasExpectedFlags() throws Throwable {
      LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
      JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(emptySet, true, true, false, true);
      emptySet.remove(value);

      assertTrue(value.getIgnoreUnknown());
      assertTrue(value.getMerge());
      assertTrue(value.getAllowGetters());
      assertFalse(value.getAllowSetters());
  }

  @Test(timeout = 4000)
  public void findIgnoredForDeserialization_whenAllowSettersIsTrue_returnsEmptySet() throws Throwable {
      JsonIgnoreProperties.Value[] values = new JsonIgnoreProperties.Value[6];

      Set<String> emptyIgnored = JsonIgnoreProperties.Value.empty().getIgnored();
      // allowSetters=true means "allow setters", so deserialization ignores nothing
      JsonIgnoreProperties.Value allowSettersValue = new JsonIgnoreProperties.Value(emptyIgnored, true, true, true, true);
      values[1] = allowSettersValue;

      Set<String> ignoredForDeserialization = values[1].findIgnoredForDeserialization();
      assertTrue(ignoredForDeserialization.isEmpty());
  }

  @Test(timeout = 4000)
  public void findIgnoredForDeserialization_fromNullAnnotation_returnsEmptySet() throws Throwable {
      JsonIgnoreProperties.Value defaultValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);
      Set<String> ignoredForDeserialization = defaultValue.findIgnoredForDeserialization();
      assertEquals(0, ignoredForDeserialization.size());
  }

  @Test(timeout = 4000)
  public void findIgnoredForSerialization_whenAllowGettersIsTrue_returnsEmptySet() throws Throwable {
      LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
      // allowGetters=true means serialization is not blocked, so result is empty
      JsonIgnoreProperties.Value allowGettersValue = JsonIgnoreProperties.Value.construct(emptySet, true, true, false, false);
      allowGettersValue.findIgnoredForSerialization();

      assertTrue(allowGettersValue.getAllowGetters());
      assertFalse(allowGettersValue.getMerge());
      assertTrue(allowGettersValue.getIgnoreUnknown());
      assertFalse(allowGettersValue.getAllowSetters());
  }

  @Test(timeout = 4000)
  public void readResolve_onDefaultValue_returnsNonAllowGettersInstance() throws Throwable {
      JsonIgnoreProperties.Value defaultValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);
      JsonIgnoreProperties.Value resolved = (JsonIgnoreProperties.Value) defaultValue.readResolve();
      assertFalse(resolved.getAllowGetters());
  }

  @Test(timeout = 4000)
  public void readResolve_onNonEmptyValue_preservesAllProperties() throws Throwable {
      LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
      JsonIgnoreProperties.Value original = JsonIgnoreProperties.Value.construct(emptySet, true, true, false, true);
      JsonIgnoreProperties.Value resolved = (JsonIgnoreProperties.Value) original.readResolve();

      assertTrue(resolved.getIgnoreUnknown());
      assertFalse(resolved.getAllowSetters());
      assertTrue(resolved.getMerge());
      assertTrue(resolved.getAllowGetters());
  }

  @Test(timeout = 4000)
  public void withoutMerge_calledTwiceOnValue_isIdempotent() throws Throwable {
      LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
      JsonIgnoreProperties.Value withMerge = JsonIgnoreProperties.Value.construct(emptySet, false, false, false, true);
      JsonIgnoreProperties.Value withoutMerge = withMerge.withoutMerge();
      JsonIgnoreProperties.Value withoutMergeAgain = withoutMerge.withoutMerge();

      assertFalse(withMerge.getIgnoreUnknown());
      assertFalse(withoutMergeAgain.getAllowGetters());
      assertFalse(withoutMergeAgain.getMerge());
      assertFalse(withoutMergeAgain.getAllowSetters());
      assertFalse(withoutMergeAgain.getIgnoreUnknown());
  }

  @Test(timeout = 4000)
  public void withMerge_onDefaultValue_returnsMergeEnabledValue() throws Throwable {
      JsonIgnoreProperties.Value defaultValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);
      JsonIgnoreProperties.Value withMerge = defaultValue.withMerge();
      assertTrue(withMerge.getMerge());
  }

  @Test(timeout = 4000)
  public void withMerge_whenMergeIsFalse_createsNewValueWithMerge() throws Throwable {
      JsonIgnoreProperties.Value noMergeValue = JsonIgnoreProperties.Value.construct((Set<String>) null, true, true, false, false);
      JsonIgnoreProperties.Value withMerge = noMergeValue.withMerge();

      assertNotSame(withMerge, noMergeValue);
      assertTrue(noMergeValue.getAllowGetters());
      assertFalse(withMerge.getAllowSetters());
      assertFalse(noMergeValue.getAllowSetters());
      assertTrue(noMergeValue.getIgnoreUnknown());
      assertTrue(withMerge.getMerge());
      assertTrue(withMerge.getIgnoreUnknown());
      assertTrue(withMerge.getAllowGetters());
  }

  @Test(timeout = 4000)
  public void withoutAllowSetters_whenAllowSettersAlreadyFalse_returnsSameInstance() throws Throwable {
      JsonIgnoreProperties.Value noSetters = JsonIgnoreProperties.Value.construct((Set<String>) null, true, true, false, false);
      JsonIgnoreProperties.Value result = noSetters.withoutAllowSetters();

      assertFalse(result.getMerge());
      assertSame(result, noSetters);
      assertTrue(result.getAllowGetters());
      assertTrue(result.getIgnoreUnknown());
  }

  @Test(timeout = 4000)
  public void withoutAllowSetters_whenAllowSettersIsTrue_createsNewValueWithAllowSettersFalse() throws Throwable {
      JsonIgnoreProperties.Value withSetters = JsonIgnoreProperties.Value.construct((Set<String>) null, false, false, true, false);
      JsonIgnoreProperties.Value withoutSetters = withSetters.withoutAllowSetters();

      assertNotSame(withoutSetters, withSetters);
      assertFalse(withSetters.getAllowGetters());
      assertFalse(withoutSetters.getMerge());
      assertFalse(withoutSetters.getIgnoreUnknown());
      assertFalse(withSetters.getIgnoreUnknown());
      assertFalse(withoutSetters.getAllowSetters());
  }

  @Test(timeout = 4000)
  public void withAllowSetters_calledTwice_returnsSameInstance() throws Throwable {
      JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();
      JsonIgnoreProperties.Value firstCall = emptyValue.withAllowSetters();
      JsonIgnoreProperties.Value secondCall = firstCall.withAllowSetters();

      assertFalse(secondCall.getIgnoreUnknown());
      assertTrue(secondCall.getAllowSetters());
      assertFalse(secondCall.getAllowGetters());
      assertTrue(secondCall.getMerge());
      assertSame(secondCall, firstCall);
  }

  @Test(timeout = 4000)
  public void withoutAllowGetters_whenAllowGettersAlreadyFalse_returnsSameInstance() throws Throwable {
      JsonIgnoreProperties.Value defaultValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);
      JsonIgnoreProperties.Value result = defaultValue.withoutAllowGetters();
      assertFalse(result.getAllowGetters());
  }

  @Test(timeout = 4000)
  public void withoutAllowGetters_whenAllowGettersIsTrue_createsNewValueWithAllowGettersFalse() throws Throwable {
      JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();
      Set<String> emptyIgnoredSet = emptyValue.findIgnoredForSerialization();
      JsonIgnoreProperties.Value withGetters = JsonIgnoreProperties.Value.construct(emptyIgnoredSet, true, true, false, false);
      JsonIgnoreProperties.Value withoutGetters = withGetters.withoutAllowGetters();

      assertNotSame(withoutGetters, withGetters);
      assertFalse(withGetters.getMerge());
      assertTrue(withoutGetters.getIgnoreUnknown());
      assertFalse(withGetters.getAllowSetters());
      assertTrue(withGetters.getIgnoreUnknown());
      assertFalse(withoutGetters.getAllowGetters());
      assertFalse(withoutGetters.getAllowSetters());
      assertFalse(withoutGetters.getMerge());
  }

  @Test(timeout = 4000)
  public void withAllowGetters_whenAllowGettersAlreadyTrue_returnsSameInstance() throws Throwable {
      JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();
      Set<String> emptyIgnoredSet = emptyValue.findIgnoredForSerialization();
      JsonIgnoreProperties.Value withGetters = JsonIgnoreProperties.Value.construct(emptyIgnoredSet, true, true, false, false);
      JsonIgnoreProperties.Value result = withGetters.withAllowGetters();

      assertFalse(result.getMerge());
      assertFalse(result.getAllowSetters());
      assertSame(result, withGetters);
      assertTrue(result.getIgnoreUnknown());
  }

  @Test(timeout = 4000)
  public void withAllowGetters_whenAllowGettersIsFalse_createsNewValueWithAllowGetters() throws Throwable {
      LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
      JsonIgnoreProperties.Value withoutGetters = JsonIgnoreProperties.Value.construct(emptySet, false, false, false, true);
      JsonIgnoreProperties.Value withGetters = withoutGetters.withAllowGetters();

      assertFalse(withGetters.getAllowSetters());
      assertTrue(withGetters.getMerge());
      assertFalse(withoutGetters.getIgnoreUnknown());
      assertFalse(withGetters.getIgnoreUnknown());
      assertTrue(withGetters.getAllowGetters());
  }

  @Test(timeout = 4000)
  public void withoutIgnoreUnknown_whenIgnoreUnknownIsTrue_createsNewValueWithIgnoreUnknownFalse() throws Throwable {
      JsonIgnoreProperties.Value ignoreUnknown = JsonIgnoreProperties.Value.forIgnoreUnknown(true);
      JsonIgnoreProperties.Value withoutIgnoreUnknown = ignoreUnknown.withoutIgnoreUnknown();

      assertTrue(withoutIgnoreUnknown.getMerge());
      assertFalse(withoutIgnoreUnknown.getIgnoreUnknown());
      assertTrue(ignoreUnknown.getIgnoreUnknown());
      assertTrue(ignoreUnknown.getMerge());
      assertFalse(ignoreUnknown.getAllowSetters());
      assertFalse(withoutIgnoreUnknown.getAllowGetters());
      assertFalse(ignoreUnknown.getAllowGetters());
      assertFalse(withoutIgnoreUnknown.getAllowSetters());
  }

  @Test(timeout = 4000)
  public void withIgnoreUnknown_whenIgnoreUnknownAlreadyTrue_returnsSameInstance() throws Throwable {
      JsonIgnoreProperties.Value ignoreUnknownValue = JsonIgnoreProperties.Value.construct((Set<String>) null, true, true, false, false);
      JsonIgnoreProperties.Value result = ignoreUnknownValue.withIgnoreUnknown();

      assertFalse(result.getMerge());
      assertSame(result, ignoreUnknownValue);
      assertTrue(result.getAllowGetters());
      assertFalse(result.getAllowSetters());
  }

  @Test(timeout = 4000)
  public void mergeAll_withPartialNullArray_combinesNonNullValues() throws Throwable {
      JsonIgnoreProperties.Value[] values = new JsonIgnoreProperties.Value[6];

      // Slot 1: value with all flags enabled
      Set<String> emptyIgnored = JsonIgnoreProperties.Value.empty().getIgnored();
      JsonIgnoreProperties.Value allFlagsEnabled = new JsonIgnoreProperties.Value(emptyIgnored, true, true, true, true);
      values[1] = allFlagsEnabled;

      // Slot 3: value created from 8-element string array (defaults: ignoreUnknown=false, allowGetters=false, allowSetters=false)
      String[] eightNulls = new String[8];
      JsonIgnoreProperties.Value fromEightProps = JsonIgnoreProperties.Value.forIgnoredProperties(eightNulls);
      assertFalse(fromEightProps.getIgnoreUnknown());
      assertFalse(fromEightProps.getAllowGetters());
      assertFalse(fromEightProps.getAllowSetters());

      values[3] = fromEightProps;

      // mergeAll propagates flags from allFlagsEnabled
      JsonIgnoreProperties.Value merged = JsonIgnoreProperties.Value.mergeAll(values);
      assertTrue(merged.getAllowSetters());
      assertNotNull(merged);
      assertNotSame(merged, allFlagsEnabled);
      assertFalse(merged.equals((Object) allFlagsEnabled));
      assertTrue(merged.getAllowGetters());
      assertTrue(merged.getIgnoreUnknown());
  }

  @Test(timeout = 4000)
  public void withOverrides_withNonEmptyIgnoredSet_resultEqualsOverride() throws Throwable {
      JsonIgnoreProperties.Value base = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);
      LinkedHashSet<String> ignoredSet = new LinkedHashSet<String>();
      ignoredSet.add("");
      JsonIgnoreProperties.Value override = JsonIgnoreProperties.Value.construct(ignoredSet, false, false, false, true);

      JsonIgnoreProperties.Value result = base.withOverrides(override);
      assertNotSame(result, override);
      assertFalse(override.getAllowSetters());
      assertFalse(base.equals((Object) override));
      assertTrue(result.equals((Object) override));
  }

  @Test(timeout = 4000)
  public void mergeAll_combinesAllFlagsFromMultipleValues() throws Throwable {
      // First value: ignoreUnknown=true, allowGetters=true, merge=false
      JsonIgnoreProperties.Value firstValue = JsonIgnoreProperties.Value.construct((Set<String>) null, true, true, false, false);
      assertTrue(firstValue.getIgnoreUnknown());
      assertTrue(firstValue.getAllowGetters());
      assertFalse(firstValue.getAllowSetters());
      assertFalse(firstValue.getMerge());

      JsonIgnoreProperties.Value[] values = new JsonIgnoreProperties.Value[7];
      values[0] = firstValue;

      // Last value: all flags enabled
      JsonIgnoreProperties.Value allFlags = JsonIgnoreProperties.Value.construct((Set<String>) null, true, true, true, true);
      values[6] = allFlags;

      JsonIgnoreProperties.Value merged = JsonIgnoreProperties.Value.mergeAll(values);
      assertTrue(merged.equals((Object) allFlags));
      assertNotNull(merged);
      assertTrue(merged.getAllowSetters());
      assertTrue(merged.getMerge());
  }

  @Test(timeout = 4000)
  public void merge_withNullOverride_returnsBaseValue() throws Throwable {
      JsonIgnoreProperties.Value base = JsonIgnoreProperties.Value.empty();
      JsonIgnoreProperties.Value result = JsonIgnoreProperties.Value.merge(base, (JsonIgnoreProperties.Value) null);
      assertSame(base, result);
  }

  @Test(timeout = 4000)
  public void merge_withSameInstance_returnsSameInstance() throws Throwable {
      JsonIgnoreProperties.Value ignoreUnknown = JsonIgnoreProperties.Value.forIgnoreUnknown(true);
      JsonIgnoreProperties.Value result = JsonIgnoreProperties.Value.merge(ignoreUnknown, ignoreUnknown);

      assertFalse(result.getAllowSetters());
      assertTrue(result.getIgnoreUnknown());
      assertSame(result, ignoreUnknown);
      assertTrue(result.getMerge());
      assertFalse(result.getAllowGetters());
  }

  @Test(timeout = 4000)
  public void forIgnoreUnknown_withFalse_createsValueWithIgnoreUnknownFalse() throws Throwable {
      JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.forIgnoreUnknown(false);
      assertFalse(value.getIgnoreUnknown());
  }

  @Test(timeout = 4000)
  public void forIgnoredProperties_withEmptyArray_returnsValueWithDefaultFlagsAndNoGetters() throws Throwable {
      String[] emptyArray = new String[0];
      JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.forIgnoredProperties(emptyArray);
      assertFalse(value.getAllowGetters());
  }

  @Test(timeout = 4000)
  public void merge_bothNull_returnsNull() throws Throwable {
      JsonIgnoreProperties.Value result = JsonIgnoreProperties.Value.merge((JsonIgnoreProperties.Value) null, (JsonIgnoreProperties.Value) null);
      assertNull(result);
  }

  @Test(timeout = 4000)
  public void merge_withSelf_returnsValueWithMergeTrue() throws Throwable {
      JsonIgnoreProperties.Value defaultValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);
      JsonIgnoreProperties.Value result = JsonIgnoreProperties.Value.merge(defaultValue, defaultValue);
      assertTrue(result.getMerge());
  }

  @Test(timeout = 4000)
  public void equals_withDifferentType_returnsFalse() throws Throwable {
      JsonIgnoreProperties jsonIgnoreProperties0 = mock(JsonIgnoreProperties.class, CALLS_REAL_METHODS);
      doReturn(false).when(jsonIgnoreProperties0).allowGetters();
      doReturn(false).when(jsonIgnoreProperties0).allowSetters();
      doReturn(false).when(jsonIgnoreProperties0).ignoreUnknown();
      doReturn((String[]) null).when(jsonIgnoreProperties0).value();

      JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.from(jsonIgnoreProperties0);
      Object unrelated = new Object();
      boolean result = value.equals(unrelated);

      assertFalse(result);
      assertFalse(value.getMerge());
      assertFalse(value.getIgnoreUnknown());
      assertFalse(value.getAllowGetters());
      assertFalse(value.getAllowSetters());
  }

  @Test(timeout = 4000)
  public void mergeAll_withDuplicateValues_retainsTheirProperties() throws Throwable {
      JsonIgnoreProperties.Value sharedValue = JsonIgnoreProperties.Value.construct((Set<String>) null, true, true, false, false);
      JsonIgnoreProperties.Value[] values = new JsonIgnoreProperties.Value[7];
      values[0] = sharedValue;
      values[1] = sharedValue;

      JsonIgnoreProperties.Value merged = JsonIgnoreProperties.Value.mergeAll(values);
      assertTrue(merged.getIgnoreUnknown());
      assertFalse(merged.getAllowSetters());
      assertFalse(merged.getMerge());
      assertNotNull(merged);
      assertTrue(merged.getAllowGetters());
  }

  @Test(timeout = 4000)
  public void valueFor_returnsJsonIgnorePropertiesClass() throws Throwable {
      LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
      JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(emptySet, false, false, false, true);
      value.valueFor();
      assertFalse(value.getIgnoreUnknown());
  }

  @Test(timeout = 4000)
  public void from_withNullAnnotation_returnsValueWithMergeTrue() throws Throwable {
      JsonIgnoreProperties.Value defaultValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);
      boolean merge = defaultValue.getMerge();
      assertTrue(merge);
  }

  @Test(timeout = 4000)
  public void toString_hasExpectedFormat() throws Throwable {
      LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
      JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(emptySet, false, false, false, true);
      String result = value.toString();
      assertEquals("JsonIgnoreProperties.Value(ignored=[],ignoreUnknown=false,allowGetters=false,allowSetters=false,merge=true)", result);
  }

  @Test(timeout = 4000)
  public void getAllowGetters_whenFalse_returnsFalse() throws Throwable {
      LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
      JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(emptySet, false, false, false, true);
      boolean allowGetters = value.getAllowGetters();
      assertFalse(value.getIgnoreUnknown());
      assertFalse(allowGetters);
  }

  @Test(timeout = 4000)
  public void forIgnoredProperties_withEmptySet_returnsValueWithDefaultFlags() throws Throwable {
      LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
      JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.forIgnoredProperties((Set<String>) emptySet);
      assertFalse(value.getAllowSetters());
      assertFalse(value.getIgnoreUnknown());
      assertFalse(value.getAllowGetters());
      assertTrue(value.getMerge());
  }

  @Test(timeout = 4000)
  public void getIgnoreUnknown_whenFalse_returnsFalse() throws Throwable {
      LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
      JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(emptySet, false, false, false, true);
      boolean ignoreUnknown = value.getIgnoreUnknown();
      assertFalse(ignoreUnknown);
  }

  @Test(timeout = 4000)
  public void withoutIgnored_onEmptyValue_returnsSameInstance() throws Throwable {
      JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();
      JsonIgnoreProperties.Value result = emptyValue.withoutIgnored();
      assertSame(result, emptyValue);
  }

  @Test(timeout = 4000)
  public void getAllowSetters_onEmptyValue_returnsFalse() throws Throwable {
      JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();
      boolean allowSetters = emptyValue.getAllowSetters();
      assertFalse(allowSetters);
  }
}
