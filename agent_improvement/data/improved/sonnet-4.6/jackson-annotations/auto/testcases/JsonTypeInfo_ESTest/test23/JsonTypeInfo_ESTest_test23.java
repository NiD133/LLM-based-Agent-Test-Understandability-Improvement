package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test23 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that withIdVisible(true) produces a new Value not equal to the original EMPTY Value,
     * and that the inequality holds in both directions.
     */
    @Test(timeout = 4000)
    public void test23() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;
        JsonTypeInfo.Value valueWithIdVisible = emptyValue.withIdVisible(true);

        boolean emptyEqualsWithIdVisible = emptyValue.equals(valueWithIdVisible);

        assertFalse(valueWithIdVisible.equals((Object) emptyValue));
        assertFalse(emptyEqualsWithIdVisible);
    }
}
