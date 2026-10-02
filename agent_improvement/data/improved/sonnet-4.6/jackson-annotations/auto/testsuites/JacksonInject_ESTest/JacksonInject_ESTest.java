package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.OptBoolean;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true,
        resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest extends JacksonInject_ESTest_scaffolding {

    // --- construct() / getUseInput() ---

    @Test(timeout = 4000)
    public void test00_construct_withUseInputTrue_getUseInputReturnsTrue() throws Throwable {
        Boolean useInputTrue = Boolean.valueOf(true);
        JacksonInject.Value value = JacksonInject.Value.construct((Object) null, useInputTrue, useInputTrue);
        assertTrue(value.getUseInput());
    }

    @Test(timeout = 4000)
    public void test24_construct_withNonNullIdAndUseInputTrue_getUseInputReturnsNonNull() throws Throwable {
        Object id = new Object();
        Boolean useInputTrue = Boolean.valueOf(true);
        JacksonInject.Value value = JacksonInject.Value.construct(id, useInputTrue, useInputTrue);
        Boolean useInput = value.getUseInput();
        assertNotNull(useInput);
        assertTrue(value.hasId());
    }

    @Test(timeout = 4000)
    public void test22_construct_withEmptyStringId_treatsAsNullSoHasIdIsFalse() throws Throwable {
        Boolean falseBool = new Boolean("");
        JacksonInject.Value value = JacksonInject.Value.construct((Object) "", (Boolean) null, falseBool);
        assertFalse(value.hasId());
    }

    // --- empty() / EMPTY ---

    @Test(timeout = 4000)
    public void test09_emptyConstant_hasNoId() throws Throwable {
        JacksonInject.Value empty = JacksonInject.Value.EMPTY;
        assertFalse(empty.hasId());
    }

    @Test(timeout = 4000)
    public void test23_emptyConstant_getOptionalReturnsNull() throws Throwable {
        JacksonInject.Value empty = JacksonInject.Value.EMPTY;
        Boolean optional = empty.getOptional();
        assertNull(optional);
    }

    @Test(timeout = 4000)
    public void test27_emptyFactory_getOptionalReturnsNull() throws Throwable {
        JacksonInject.Value empty = JacksonInject.Value.empty();
        assertNull(empty.getOptional());
    }

    // --- forId() ---

    @Test(timeout = 4000)
    public void test05_forId_reflexiveEquality() throws Throwable {
        Object id = new Object();
        JacksonInject.Value value = JacksonInject.Value.forId(id);
        assertTrue(value.equals(value));
        assertTrue(value.hasId());
    }

    @Test(timeout = 4000)
    public void test21_forId_nullId_getUseInputReturnsNull() throws Throwable {
        JacksonInject.Value value = JacksonInject.Value.forId((Object) null);
        assertNull(value.getUseInput());
    }

    // --- from() ---

    @Test(timeout = 4000)
    public void test19_from_nullAnnotation_returnsEmptyWithNullOptional() throws Throwable {
        JacksonInject.Value value = JacksonInject.Value.from((JacksonInject) null);
        assertNull(value.getOptional());
    }

    @Test(timeout = 4000)
    public void test20_from_mockWithNullUseInput_throwsNullPointerException() throws Throwable {
        JacksonInject mockAnnotation = mock(JacksonInject.class, CALLS_REAL_METHODS);
        doReturn((OptBoolean) null).when(mockAnnotation).useInput();
        doReturn((String) null).when(mockAnnotation).value();
        try {
            JacksonInject.Value.from(mockAnnotation);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("com.fasterxml.jackson.annotation.JacksonInject$Value", e);
        }
    }

    // --- withId() ---

    @Test(timeout = 4000)
    public void test08_withId_nonNullObject_hasIdReturnsTrue() throws Throwable {
        JacksonInject.Value empty = JacksonInject.Value.EMPTY;
        JacksonInject.Value withId = empty.withId(empty);
        assertTrue(withId.hasId());
    }

    @Test(timeout = 4000)
    public void test16_withId_sameId_returnsSameInstance() throws Throwable {
        Object id = new Object();
        JacksonInject.Value value = JacksonInject.Value.forId(id);
        JacksonInject.Value result = value.withId(id);
        assertSame(result, value);
    }

    @Test(timeout = 4000)
    public void test17_withId_nullOnEmptyValue_returnsSameInstance() throws Throwable {
        JacksonInject.Value empty = JacksonInject.Value.EMPTY;
        JacksonInject.Value result = empty.withId((Object) null);
        assertSame(result, empty);
    }

    @Test(timeout = 4000)
    public void test18_withId_null_whenIdWasSet_createsNewValueWithNoId() throws Throwable {
        Object id = new Object();
        JacksonInject.Value value = JacksonInject.Value.forId(id);
        JacksonInject.Value result = value.withId((Object) null);
        assertNotSame(result, value);
        assertFalse(result.hasId());
    }

    // --- withUseInput() ---

    @Test(timeout = 4000)
    public void test02_withUseInput_true_createsValueNotEqualToEmpty() throws Throwable {
        JacksonInject.Value empty = JacksonInject.Value.EMPTY;
        JacksonInject.Value withUseInput = empty.withUseInput(Boolean.TRUE);
        assertFalse(empty.equals(withUseInput));
        assertFalse(withUseInput.equals((Object) empty));
    }

    @Test(timeout = 4000)
    public void test13_withUseInput_null_whenUseInputWasTrue_createsNewInstance() throws Throwable {
        Object id = new Object();
        JacksonInject.Value value = JacksonInject.Value.construct(id, Boolean.TRUE, Boolean.TRUE);
        JacksonInject.Value result = value.withUseInput((Boolean) null);
        assertNotSame(result, value);
        assertFalse(result.equals((Object) value));
        assertTrue(result.hasId());
    }

    @Test(timeout = 4000)
    public void test14_withUseInput_null_whenUseInputAlreadyNull_returnsSameInstance() throws Throwable {
        JacksonInject.Value empty = JacksonInject.Value.EMPTY;
        JacksonInject.Value result = empty.withUseInput((Boolean) null);
        assertSame(result, empty);
    }

    @Test(timeout = 4000)
    public void test15_withUseInput_sameValue_returnsSameInstance() throws Throwable {
        Object id = new Object();
        Boolean useInputTrue = Boolean.valueOf(true);
        JacksonInject.Value value = JacksonInject.Value.construct(id, useInputTrue, useInputTrue);
        JacksonInject.Value result = value.withUseInput(useInputTrue);
        assertTrue(result.hasId());
        assertSame(result, value);
    }

    // --- withOptional() ---

    @Test(timeout = 4000)
    public void test01_withOptional_twoCallsWithSameValue_producesEqualButDistinctInstances() throws Throwable {
        JacksonInject.Value empty = JacksonInject.Value.EMPTY;
        Boolean falseBool = new Boolean("sE]@ 6W)1`^'M<pcHc");
        JacksonInject.Value first = empty.withOptional(falseBool);
        JacksonInject.Value second = empty.withOptional(falseBool);
        assertTrue(second.equals(first));
        assertFalse(second.equals((Object) empty));
        assertNotSame(second, first);
    }

    @Test(timeout = 4000)
    public void test03_withOptional_false_createsValueNotEqualToEmpty() throws Throwable {
        JacksonInject.Value empty = JacksonInject.Value.EMPTY;
        JacksonInject.Value withOptional = empty.withOptional(Boolean.FALSE);
        assertFalse(withOptional.equals((Object) empty));
        assertFalse(empty.equals(withOptional));
    }

    @Test(timeout = 4000)
    public void test10_withOptional_null_whenOptionalAlreadyNull_returnsSameInstance() throws Throwable {
        Object id = new Object();
        JacksonInject.Value value = JacksonInject.Value.forId(id);
        JacksonInject.Value result = value.withOptional((Boolean) null);
        assertTrue(result.hasId());
        assertSame(result, value);
    }

    @Test(timeout = 4000)
    public void test11_withOptional_null_whenOptionalWasTrue_createsNewInstance() throws Throwable {
        Object id = new Object();
        Boolean trueValue = Boolean.valueOf(true);
        JacksonInject.Value value = JacksonInject.Value.construct(id, trueValue, trueValue);
        JacksonInject.Value result = value.withOptional((Boolean) null);
        assertTrue(result.hasId());
        assertFalse(result.equals((Object) value));
        assertNotSame(result, value);
    }

    @Test(timeout = 4000)
    public void test12_withOptional_sameValue_returnsSameInstance() throws Throwable {
        Object id = new Object();
        Boolean trueValue = Boolean.valueOf(true);
        JacksonInject.Value value = JacksonInject.Value.construct(id, trueValue, trueValue);
        JacksonInject.Value result = value.withOptional(trueValue);
        assertTrue(result.hasId());
        assertSame(result, value);
    }

    // --- willUseInput() ---

    @Test(timeout = 4000)
    public void test06_willUseInput_whenUseInputIsNull_usesProvidedDefault() throws Throwable {
        Object id = new Object();
        Boolean trueValue = Boolean.valueOf(true);
        JacksonInject.Value value = JacksonInject.Value.construct(id, (Boolean) null, trueValue);
        boolean result = value.willUseInput(false);
        assertFalse(result);
        assertTrue(value.hasId());
    }

    @Test(timeout = 4000)
    public void test07_willUseInput_whenUseInputIsTrue_overridesDefaultFalse() throws Throwable {
        JacksonInject.Value empty = JacksonInject.Value.EMPTY;
        JacksonInject.Value withUseInputTrue = empty.withUseInput(Boolean.TRUE);
        boolean result = withUseInputTrue.willUseInput(false);
        assertTrue(result);
    }

    // --- equals() ---

    @Test(timeout = 4000)
    public void test04_equals_valueVsBoolean_returnsFalse() throws Throwable {
        Object id = new Object();
        Boolean trueValue = Boolean.valueOf(true);
        JacksonInject.Value value = JacksonInject.Value.construct(id, trueValue, trueValue);
        boolean result = value.equals(trueValue);
        assertFalse(result);
        assertTrue(value.hasId());
    }

    @Test(timeout = 4000)
    public void test26_equals_valueWithObjectId_notEqualToEmpty() throws Throwable {
        JacksonInject.Value empty = JacksonInject.Value.EMPTY;
        Boolean falseBool = Boolean.valueOf(false);
        JacksonInject.Value withObjectAsId = JacksonInject.Value.construct((Object) empty, falseBool, falseBool);
        boolean result = empty.equals(withObjectAsId);
        assertFalse(result);
        assertTrue(withObjectAsId.hasId());
    }

    // --- constructor / getId() ---

    @Test(timeout = 4000)
    public void test25_constructor_withNonNullId_getIdReturnsNonNull() throws Throwable {
        Object id = new Object();
        JacksonInject.Value value = new JacksonInject.Value(id, (Boolean) null, (Boolean) null);
        Object retrievedId = value.getId();
        assertNotNull(retrievedId);
        assertTrue(value.hasId());
    }

    // --- valueFor() ---

    @Test(timeout = 4000)
    public void test28_valueFor_returnsJacksonInjectAnnotationType() throws Throwable {
        Object id = new Object();
        JacksonInject.Value value = JacksonInject.Value.forId(id);
        value.valueFor();
        assertTrue(value.hasId());
    }
}
