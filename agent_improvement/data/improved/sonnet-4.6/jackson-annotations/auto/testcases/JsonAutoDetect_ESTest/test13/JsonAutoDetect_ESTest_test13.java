package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.lang.reflect.Member;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test13 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Tests that withOverrides returns the same Value instance when the overrides
     * do not change any effective visibility.
     *
     * baseValue has all six visibilities set to NONE.
     * scalarConstructorOverride has SCALAR_CONSTRUCTOR set to NONE and all others at DEFAULT.
     * When overrides are applied, DEFAULT entries leave the base unchanged, and
     * NONE overriding NONE still yields NONE — so the result is identical to baseValue,
     * and withOverrides is expected to return the same object reference.
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        JsonAutoDetect.Visibility none = JsonAutoDetect.Visibility.NONE;

        // Build a base Value with every accessor visibility set to NONE.
        JsonAutoDetect.Value allNoneValue = JsonAutoDetect.Value.construct(
                none, none, none, none, none, none);

        // Build an override Value that only sets SCALAR_CONSTRUCTOR to NONE;
        // all other accessors stay at DEFAULT (i.e., "no override").
        JsonAutoDetect.Value scalarConstructorOverride =
                JsonAutoDetect.Value.construct(PropertyAccessor.SCALAR_CONSTRUCTOR, none);

        // Applying scalarConstructorOverride onto allNoneValue should not change anything:
        // DEFAULT entries defer to the base (NONE), and NONE overriding NONE is still NONE.
        // Therefore withOverrides must return the exact same object.
        JsonAutoDetect.Value result = allNoneValue.withOverrides(scalarConstructorOverride);
        assertSame(allNoneValue, result);

        // Verify that scalarConstructorOverride only affected SCALAR_CONSTRUCTOR;
        // all other accessors in that override remain at DEFAULT.
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, scalarConstructorOverride.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, scalarConstructorOverride.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, scalarConstructorOverride.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, scalarConstructorOverride.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.NONE,    scalarConstructorOverride.getScalarConstructorVisibility());
    }
}
