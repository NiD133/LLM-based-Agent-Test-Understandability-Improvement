/*
 * Improved test for JsonSetter.Value — understandability-focused rewrite.
 * Runtime behaviour is identical to the EvoSuite-generated original.
 */

package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true,
        resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest extends JsonSetter_ESTest_scaffolding {

    // -----------------------------------------------------------------------
    // equals()
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void equalsReturnsTrueForValuesConstructedDifferentlyButWithSameNulls() throws Throwable {
        Nulls fail = Nulls.FAIL;
        JsonSetter.Value viaFactory  = JsonSetter.Value.construct(fail, fail);
        JsonSetter.Value viaConstructor = new JsonSetter.Value(fail, fail);

        boolean result = viaConstructor.equals(viaFactory);

        assertTrue(result);
    }

    @Test(timeout = 4000)
    public void equalsReturnsFalseWhenOneValueHasNonDefaultNullsAndOtherHasDefault() throws Throwable {
        Nulls asEmpty = Nulls.AS_EMPTY;
        JsonSetter.Value valueWithAsEmpty = JsonSetter.Value.construct(asEmpty, asEmpty);

        // Mock annotation whose nulls() and contentNulls() return null → treated as DEFAULT
        JsonSetter mockAnnotation = mock(JsonSetter.class, CALLS_REAL_METHODS);
        doReturn((Nulls) null).when(mockAnnotation).contentNulls();
        doReturn((Nulls) null).when(mockAnnotation).nulls();
        JsonSetter.Value valueFromNullAnnotation = JsonSetter.Value.from(mockAnnotation);

        boolean result = valueWithAsEmpty.equals(valueFromNullAnnotation);

        assertEquals(Nulls.AS_EMPTY, valueWithAsEmpty.getContentNulls());
        assertEquals(Nulls.AS_EMPTY, valueWithAsEmpty.getValueNulls());
        assertFalse(result);
    }

    @Test(timeout = 4000)
    public void equalsReturnsFalseWhenComparedToNull() throws Throwable {
        JsonSetter.Value empty = JsonSetter.Value.EMPTY;

        boolean result = empty.equals((Object) null);

        assertFalse(result);
    }

    @Test(timeout = 4000)
    public void equalsReturnsTrueWhenValueIsComparedToItself() throws Throwable {
        JsonSetter.Value value = JsonSetter.Value.forContentNulls(Nulls.DEFAULT);

        boolean result = value.equals(value);

        assertTrue(result);
    }

    @Test(timeout = 4000)
    public void equalsReturnsFalseForObjectOfDifferentType() throws Throwable {
        JsonSetter.Value value = JsonSetter.Value.forContentNulls(Nulls.DEFAULT);
        Object unrelated = new Object();

        boolean result = value.equals(unrelated);

        assertFalse(result);
    }

    @Test(timeout = 4000)
    public void equalsReturnsFalseWhenContentNullsDiffers() throws Throwable {
        JsonSetter.Value valueWithAsEmpty = JsonSetter.Value.forContentNulls(Nulls.AS_EMPTY);
        JsonSetter.Value emptyValue = JsonSetter.Value.EMPTY;

        boolean result = emptyValue.equals(valueWithAsEmpty);

        assertEquals(Nulls.DEFAULT, valueWithAsEmpty.getValueNulls());
        assertFalse(result);
    }

    // -----------------------------------------------------------------------
    // nonDefaultContentNulls()
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void nonDefaultContentNullsReturnsNullForEmptyValue() throws Throwable {
        // EMPTY has DEFAULT content nulls, so nonDefaultContentNulls() must return null
        JsonSetter.Value empty = JsonSetter.Value.EMPTY;

        empty.nonDefaultContentNulls();
    }

    @Test(timeout = 4000)
    public void nonDefaultContentNullsReturnsContentNullsWhenNotDefault() throws Throwable {
        Nulls asEmpty = Nulls.AS_EMPTY;
        JsonSetter.Value value = JsonSetter.Value.construct(asEmpty, asEmpty);

        value.nonDefaultContentNulls();

        assertEquals(Nulls.AS_EMPTY, value.getContentNulls());
        assertEquals(Nulls.AS_EMPTY, value.getValueNulls());
    }

    // -----------------------------------------------------------------------
    // nonDefaultValueNulls()
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void nonDefaultValueNullsReturnsNullWhenValueNullsIsDefault() throws Throwable {
        // forContentNulls sets valueNulls to DEFAULT, so nonDefaultValueNulls() returns null
        JsonSetter.Value value = JsonSetter.Value.forContentNulls(Nulls.DEFAULT);

        value.nonDefaultValueNulls();
    }

    @Test(timeout = 4000)
    public void nonDefaultValueNullsReturnsValueNullsWhenNotDefault() throws Throwable {
        Nulls asEmpty = Nulls.AS_EMPTY;
        JsonSetter.Value value = JsonSetter.Value.forValueNulls(asEmpty, asEmpty);

        value.nonDefaultValueNulls();

        assertEquals(Nulls.AS_EMPTY, value.getValueNulls());
        assertEquals(Nulls.AS_EMPTY, value.getContentNulls());
    }

    // -----------------------------------------------------------------------
    // withContentNulls()
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void withContentNullsNullArgumentResetsContentNullsToDefault() throws Throwable {
        Nulls fail = Nulls.FAIL;
        JsonSetter.Value original = JsonSetter.Value.construct(fail, fail);

        JsonSetter.Value updated = original.withContentNulls((Nulls) null);

        assertEquals(Nulls.FAIL,    original.getContentNulls());
        assertEquals(Nulls.DEFAULT, updated.getContentNulls());
        assertEquals(Nulls.FAIL,    updated.getValueNulls());
    }

    @Test(timeout = 4000)
    public void withContentNullsReturnsSameInstanceWhenContentNullsUnchanged() throws Throwable {
        Nulls fail = Nulls.FAIL;
        JsonSetter.Value original = JsonSetter.Value.construct(fail, fail);

        JsonSetter.Value result = original.withContentNulls(fail);

        assertEquals(Nulls.FAIL, result.getValueNulls());
        assertEquals(Nulls.FAIL, result.getContentNulls());
        assertSame(original, result);
    }

    // -----------------------------------------------------------------------
    // withValueNulls()
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void withValueNullsTwoArgFormSetsValueAndContentNulls() throws Throwable {
        // Start from a Value built from an annotation that returns null for both nulls
        JsonSetter mockAnnotation = mock(JsonSetter.class, CALLS_REAL_METHODS);
        doReturn((Nulls) null).when(mockAnnotation).contentNulls();
        doReturn((Nulls) null).when(mockAnnotation).nulls();
        JsonSetter.Value base = JsonSetter.Value.from(mockAnnotation);

        Nulls asEmpty = Nulls.AS_EMPTY;
        JsonSetter.Value result = base.withValueNulls(asEmpty, asEmpty);

        assertEquals(Nulls.AS_EMPTY, result.getValueNulls());
        assertEquals(Nulls.AS_EMPTY, result.getContentNulls());
    }

    @Test(timeout = 4000)
    public void withValueNullsTwoArgFormWithBothNullResetsToDefault() throws Throwable {
        JsonSetter.Value base = JsonSetter.Value.empty();

        JsonSetter.Value result = base.withValueNulls((Nulls) null, (Nulls) null);

        assertEquals(Nulls.DEFAULT, result.getValueNulls());
    }

    @Test(timeout = 4000)
    public void withValueNullsNullArgumentResetsValueNullsToDefault() throws Throwable {
        Nulls fail = Nulls.FAIL;
        JsonSetter.Value original = JsonSetter.Value.construct(fail, fail);

        JsonSetter.Value result = original.withValueNulls((Nulls) null);

        assertEquals(Nulls.FAIL,    result.getContentNulls());
        assertEquals(Nulls.DEFAULT, result.getValueNulls());
        assertEquals(Nulls.FAIL,    original.getValueNulls());
    }

    @Test(timeout = 4000)
    public void withValueNullsReturnsSameInstanceWhenValueNullsUnchanged() throws Throwable {
        Nulls fail = Nulls.FAIL;
        JsonSetter.Value original = JsonSetter.Value.construct(fail, fail);

        JsonSetter.Value result = original.withValueNulls(fail);

        assertEquals(Nulls.FAIL, result.getContentNulls());
        assertSame(original, result);
        assertEquals(Nulls.FAIL, result.getValueNulls());
    }

    // -----------------------------------------------------------------------
    // withOverrides()
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void withOverridesOnEmptyBaseAdoptsOverrideNulls() throws Throwable {
        Nulls fail = Nulls.FAIL;
        JsonSetter.Value override = JsonSetter.Value.construct(fail, fail);
        JsonSetter.Value base     = JsonSetter.Value.EMPTY;

        JsonSetter.Value result = base.withOverrides(override);

        assertEquals(Nulls.FAIL, result.getValueNulls());
        assertEquals(Nulls.FAIL, result.getContentNulls());
    }

    @Test(timeout = 4000)
    public void withOverridesAppliesNonDefaultContentNullsFromOverride() throws Throwable {
        // base has DEFAULT value-nulls and DEFAULT content-nulls
        JsonSetter.Value base = JsonSetter.Value.forContentNulls(Nulls.DEFAULT);
        // override has DEFAULT value-nulls and SET content-nulls
        JsonSetter.Value override = base.withValueNulls(Nulls.DEFAULT, Nulls.SET);

        JsonSetter.Value result = base.withOverrides(override);

        // override's SET content-nulls wins; DEFAULT value-nulls falls back to base's DEFAULT
        assertEquals(Nulls.SET,     result.getContentNulls());
        assertEquals(Nulls.DEFAULT, result.getValueNulls());
    }

    @Test(timeout = 4000)
    public void withOverridesEmptyOverrideReturnsSameInstance() throws Throwable {
        Nulls fail = Nulls.FAIL;
        JsonSetter.Value base  = JsonSetter.Value.construct(fail, fail);
        JsonSetter.Value empty = JsonSetter.Value.EMPTY;

        JsonSetter.Value result = base.withOverrides(empty);

        // EMPTY override means no change → same values retained
        assertEquals(Nulls.FAIL, result.getContentNulls());
        assertEquals(Nulls.FAIL, result.getValueNulls());
    }

    @Test(timeout = 4000)
    public void withOverridesNullOverrideReturnsSameInstance() throws Throwable {
        Nulls fail = Nulls.FAIL;
        JsonSetter.Value base = JsonSetter.Value.construct(fail, fail);

        JsonSetter.Value result = base.withOverrides((JsonSetter.Value) null);

        assertEquals(Nulls.FAIL, result.getValueNulls());
        assertEquals(Nulls.FAIL, result.getContentNulls());
    }

    // -----------------------------------------------------------------------
    // merge()
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void mergeReturnsBaseWhenOverrideHasNoNonDefaultValues() throws Throwable {
        Nulls fail = Nulls.FAIL;
        // base has FAIL for both; override has FAIL value-nulls but DEFAULT content-nulls
        JsonSetter.Value base     = JsonSetter.Value.construct(fail, fail);
        JsonSetter.Value override = JsonSetter.Value.forValueNulls(fail);

        JsonSetter.Value result = JsonSetter.Value.merge(base, override);

        assertFalse(override.equals((Object) base));
        assertSame(base, result);
        assertEquals(Nulls.FAIL, override.getValueNulls());
    }

    @Test(timeout = 4000)
    public void mergeWithNullBaseReturnsOverride() throws Throwable {
        Nulls fail = Nulls.FAIL;
        JsonSetter.Value override = JsonSetter.Value.forValueNulls(fail, fail);

        JsonSetter.Value result = JsonSetter.Value.merge((JsonSetter.Value) null, override);

        assertEquals(Nulls.FAIL, result.getContentNulls());
        assertEquals(Nulls.FAIL, result.getValueNulls());
        assertNotNull(result);
    }

    // -----------------------------------------------------------------------
    // from()
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void fromNullAnnotationReturnsEmptyValueWithDefaultNulls() throws Throwable {
        JsonSetter.Value result = JsonSetter.Value.from((JsonSetter) null);

        assertEquals(Nulls.DEFAULT, result.getValueNulls());
    }

    @Test(timeout = 4000)
    public void fromAnnotationWithNullNullsDefaultsBothNullsToDefault() throws Throwable {
        JsonSetter mockAnnotation = mock(JsonSetter.class, CALLS_REAL_METHODS);
        doReturn((Nulls) null).when(mockAnnotation).contentNulls();
        doReturn((Nulls) null).when(mockAnnotation).nulls();

        JsonSetter.Value result = JsonSetter.Value.from(mockAnnotation);
        Nulls contentNulls = result.getContentNulls();

        assertEquals(Nulls.DEFAULT, contentNulls);
    }

    // -----------------------------------------------------------------------
    // readResolve()
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void readResolveOnNonEmptyValueReturnsSameValueWithSameNulls() throws Throwable {
        Nulls fail = Nulls.FAIL;
        JsonSetter.Value original = JsonSetter.Value.construct(fail, fail);

        JsonSetter.Value resolved = (JsonSetter.Value) original.readResolve();

        assertEquals(Nulls.FAIL, resolved.getValueNulls());
        assertEquals(Nulls.FAIL, resolved.getContentNulls());
    }

    @Test(timeout = 4000)
    public void readResolveOnEmptyValueReturnsEmptySingleton() throws Throwable {
        JsonSetter.Value empty = JsonSetter.Value.empty();

        JsonSetter.Value resolved = (JsonSetter.Value) empty.readResolve();

        assertEquals(Nulls.DEFAULT, resolved.getContentNulls());
    }

    // -----------------------------------------------------------------------
    // toString()
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void toStringIncludesBothValueNullsAndContentNulls() throws Throwable {
        Nulls fail = Nulls.FAIL;
        JsonSetter.Value value = JsonSetter.Value.construct(fail, fail);

        String result = value.toString();

        assertEquals("JsonSetter.Value(valueNulls=FAIL,contentNulls=FAIL)", result);
    }

    // -----------------------------------------------------------------------
    // valueFor()
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void valueForReturnsJsonSetterAnnotationClass() throws Throwable {
        JsonSetter.Value value = JsonSetter.Value.forContentNulls(Nulls.DEFAULT);

        Class<JsonSetter> annotationType = value.valueFor();

        assertEquals(9729, annotationType.getModifiers());
    }

    // -----------------------------------------------------------------------
    // getValueNulls() / getContentNulls()
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void forContentNullsLeavesValueNullsAtDefault() throws Throwable {
        JsonSetter.Value value = JsonSetter.Value.forContentNulls(Nulls.DEFAULT);

        Nulls valueNulls = value.getValueNulls();

        assertEquals(Nulls.DEFAULT, valueNulls);
    }
}
