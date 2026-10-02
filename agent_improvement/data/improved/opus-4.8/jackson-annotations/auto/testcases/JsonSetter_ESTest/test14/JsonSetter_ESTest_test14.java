package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test14 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that calling {@code withValueNulls} with the value-nulls setting
     * that the instance already has returns the same instance unchanged (a no-op),
     * rather than allocating a new {@code Value}.
     */
    @Test(timeout = 4000)
    public void withValueNulls_sameSetting_returnsSameInstance() throws Throwable {
        JsonSetter.Value original = JsonSetter.Value.construct(Nulls.FAIL, Nulls.FAIL);

        JsonSetter.Value result = original.withValueNulls(Nulls.FAIL);

        assertSame("Re-applying the existing value-nulls setting should return the same instance",
                original, result);
        assertEquals(Nulls.FAIL, result.getValueNulls());
        assertEquals(Nulls.FAIL, result.getContentNulls());
    }
}
