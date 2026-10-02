package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.OptBoolean;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true,
        resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest extends JsonIncludeProperties_ESTest_scaffolding {

    // -------------------------------------------------------------------------
    // Value.from(JsonIncludeProperties)
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void fromAnnotation_withFalseOrder_isNotOrdered() throws Throwable {
        String[] noProperties = new String[0];
        JsonIncludeProperties annotation = mock(JsonIncludeProperties.class, CALLS_REAL_METHODS);
        doReturn(OptBoolean.FALSE).when(annotation).order();
        doReturn(noProperties).when(annotation).value();

        JsonIncludeProperties.Value value = JsonIncludeProperties.Value.from(annotation);

        assertFalse(value.getOrdered());
    }

    @Test(timeout = 4000)
    public void fromAnnotation_withNullOrder_throwsNullPointerException() throws Throwable {
        JsonIncludeProperties annotation = mock(JsonIncludeProperties.class, CALLS_REAL_METHODS);
        doReturn((OptBoolean) null).when(annotation).order();
        doReturn((String[]) null).when(annotation).value();

        try {
            JsonIncludeProperties.Value.from(annotation);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
        }
    }

    // -------------------------------------------------------------------------
    // Value.equals()
    // -------------------------------------------------------------------------

    /**
     * ALL has null included; a Value with an empty (non-null) included set
     * is not equal to ALL, because null and empty are semantically different
     * (null = "include everything", empty = "include nothing").
     */
    @Test(timeout = 4000)
    public void equals_allValueVsEmptyIncludedValue_returnsFalse() throws Throwable {
        LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
        JsonIncludeProperties.Value valueWithEmptyIncluded = new JsonIncludeProperties.Value(emptySet, (Boolean) null);
        JsonIncludeProperties.Value allValue = JsonIncludeProperties.Value.from((JsonIncludeProperties) null);

        assertFalse(valueWithEmptyIncluded.equals(allValue));
    }

    @Test(timeout = 4000)
    public void equals_sameIncludedDifferentOrdered_returnsFalse() throws Throwable {
        LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
        // new Boolean(non-"true" string) evaluates to false
        JsonIncludeProperties.Value valueFalseOrdered = new JsonIncludeProperties.Value(emptySet, new Boolean("j:w.BxrN!bO}"));
        JsonIncludeProperties.Value valueTrueOrdered = new JsonIncludeProperties.Value(emptySet, Boolean.TRUE);

        assertFalse(valueFalseOrdered.equals(valueTrueOrdered));
    }

    @Test(timeout = 4000)
    public void equals_sameIncludedSameOrdered_returnsTrue() throws Throwable {
        LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
        Boolean ordered = new Boolean("j:w.BxrN!bO}");
        JsonIncludeProperties.Value value1 = new JsonIncludeProperties.Value(emptySet, ordered);
        JsonIncludeProperties.Value value2 = new JsonIncludeProperties.Value(emptySet, ordered);

        assertTrue(value1.equals(value2));
    }

    @Test(timeout = 4000)
    public void equals_null_returnsFalse() throws Throwable {
        LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
        JsonIncludeProperties.Value value = new JsonIncludeProperties.Value(emptySet, Boolean.valueOf(true));

        assertFalse(value.equals(null));
    }

    @Test(timeout = 4000)
    public void equals_self_returnsTrue() throws Throwable {
        JsonIncludeProperties.Value allValue = JsonIncludeProperties.Value.ALL;

        assertTrue(allValue.equals(allValue));
    }

    @Test(timeout = 4000)
    public void equals_differentType_returnsFalse() throws Throwable {
        LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
        JsonIncludeProperties.Value value = new JsonIncludeProperties.Value(emptySet, new Boolean("j:w.BxrN!bO}"));

        assertFalse(value.equals("j:w.BxrN!bO}"));
    }

    // -------------------------------------------------------------------------
    // Set membership: Value must not accidentally match String elements
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void removeAllValueFromStringSet_returnsFalse() throws Throwable {
        LinkedHashSet<String> stringSet = new LinkedHashSet<String>();
        JsonIncludeProperties.Value allValue = JsonIncludeProperties.Value.all();

        assertFalse(stringSet.remove(allValue));
    }

    @Test(timeout = 4000)
    public void removeValueFromStringSet_returnsFalse() throws Throwable {
        LinkedHashSet<String> stringSet = new LinkedHashSet<String>();
        JsonIncludeProperties.Value value = new JsonIncludeProperties.Value(stringSet, new Boolean("j:w.BxrN!bO}"));

        assertFalse(stringSet.remove(value));
    }

    // -------------------------------------------------------------------------
    // Value.withOverrides()
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void withOverrides_self_returnsEqualButDistinctInstance() throws Throwable {
        LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
        JsonIncludeProperties.Value value = new JsonIncludeProperties.Value(emptySet, new Boolean("j:w.BxrN!bO}"));

        JsonIncludeProperties.Value result = value.withOverrides(value);

        assertEquals(value, result);
        assertNotSame(value, result);
    }

    /**
     * When this.included = {null} and override.included = {"$h]54GG#Ke5AZNb`7r"},
     * the intersection is empty, so the result differs from both inputs.
     */
    @Test(timeout = 4000)
    public void withOverrides_disjointIncludedSets_producesEmptyIntersection() throws Throwable {
        LinkedHashSet<String> setWithProperty = new LinkedHashSet<String>();
        setWithProperty.add("$h]54GG#Ke5AZNb`7r");
        JsonIncludeProperties.Value overrideValue = new JsonIncludeProperties.Value(setWithProperty, (Boolean) null);

        // Produces a Value with included = {null} (array of length 1 has one null element)
        String[] arrayWithNull = new String[1];
        JsonIncludeProperties annotation = mock(JsonIncludeProperties.class, CALLS_REAL_METHODS);
        doReturn(OptBoolean.DEFAULT).when(annotation).order();
        doReturn(arrayWithNull).when(annotation).value();
        JsonIncludeProperties.Value baseValue = JsonIncludeProperties.Value.from(annotation);

        JsonIncludeProperties.Value result = baseValue.withOverrides(overrideValue);

        assertTrue(result.getIncluded().isEmpty());
    }

    @Test(timeout = 4000)
    public void withOverrides_sameNonEmptyIncludedSet_returnsEqualButDistinct() throws Throwable {
        LinkedHashSet<String> setWithProperty = new LinkedHashSet<String>();
        setWithProperty.add("U 6?8|");
        JsonIncludeProperties.Value value = new JsonIncludeProperties.Value(setWithProperty, (Boolean) null);

        JsonIncludeProperties.Value result = value.withOverrides(value);

        assertEquals(value, result);
        assertNotSame(value, result);
    }

    /**
     * ALL has null included; when used as the base ("this"), withOverrides returns
     * the override as-is since an undefined base adopts the override entirely.
     */
    @Test(timeout = 4000)
    public void withOverrides_nullIncludedBase_returnsOverride() throws Throwable {
        LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
        JsonIncludeProperties.Value valueWithEmptyIncluded = new JsonIncludeProperties.Value(emptySet, (Boolean) null);
        JsonIncludeProperties.Value allValue = JsonIncludeProperties.Value.from((JsonIncludeProperties) null);

        JsonIncludeProperties.Value result = allValue.withOverrides(valueWithEmptyIncluded);

        assertSame(valueWithEmptyIncluded, result);
    }

    /**
     * ALL has null included; when used as the override, withOverrides returns
     * "this" unchanged since an undefined override does not restrict anything.
     */
    @Test(timeout = 4000)
    public void withOverrides_nullIncludedOverride_returnsThis() throws Throwable {
        LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
        JsonIncludeProperties.Value value = new JsonIncludeProperties.Value(emptySet, (Boolean) null);
        JsonIncludeProperties.Value allValue = JsonIncludeProperties.Value.from((JsonIncludeProperties) null);

        JsonIncludeProperties.Value result = value.withOverrides(allValue);

        assertSame(value, result);
    }

    @Test(timeout = 4000)
    public void withOverrides_null_returnsThis() throws Throwable {
        LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
        JsonIncludeProperties.Value value = new JsonIncludeProperties.Value(emptySet, Boolean.valueOf(true));

        JsonIncludeProperties.Value result = value.withOverrides((JsonIncludeProperties.Value) null);

        assertSame(value, result);
    }

    // -------------------------------------------------------------------------
    // Value.toString()
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void toString_emptyIncludedNullOrdered_formatsCorrectly() throws Throwable {
        LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
        JsonIncludeProperties.Value value = new JsonIncludeProperties.Value(emptySet, (Boolean) null);

        assertEquals("JsonIncludeProperties.Value(included=[],ordered=null)", value.toString());
    }

    // -------------------------------------------------------------------------
    // Value.getOrdered()
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void getOrdered_falseBoolean_returnsFalse() throws Throwable {
        LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
        // new Boolean(non-"true" string) evaluates to false
        JsonIncludeProperties.Value value = new JsonIncludeProperties.Value(emptySet, new Boolean("j:w.BxrN!bO}"));

        assertEquals(Boolean.FALSE, value.getOrdered());
    }

    // -------------------------------------------------------------------------
    // Value.valueFor()
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void valueFor_returnsJsonIncludePropertiesClass_notAnEnum() throws Throwable {
        LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
        JsonIncludeProperties.Value value = new JsonIncludeProperties.Value(emptySet, new Boolean("j:w.BxrN!bO}"));

        Class<JsonIncludeProperties> annotationType = value.valueFor();

        assertEquals(JsonIncludeProperties.class, annotationType);
        assertFalse(annotationType.isEnum());
    }

    // -------------------------------------------------------------------------
    // Set.contains() — Value must not accidentally match String elements
    // -------------------------------------------------------------------------

    @Test(timeout = 4000)
    public void containsValue_inStringSet_returnsFalse() throws Throwable {
        LinkedHashSet<String> stringSet = new LinkedHashSet<String>();
        JsonIncludeProperties.Value value = new JsonIncludeProperties.Value(stringSet, new Boolean("TRUE"));

        assertFalse(stringSet.contains(value));
    }
}
