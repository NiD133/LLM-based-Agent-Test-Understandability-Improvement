package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test27 extends JsonSetter_ESTest_scaffolding {

    /**
     * forContentNulls(...) only sets the content-nulls handling and leaves the
     * value-nulls handling at its default. So a Value built this way should
     * report Nulls.DEFAULT for its value-nulls.
     */
    @Test(timeout = 4000)
    public void forContentNulls_leavesValueNullsAtDefault() throws Throwable {
        JsonSetter.Value value = JsonSetter.Value.forContentNulls(Nulls.DEFAULT);

        Nulls valueNulls = value.getValueNulls();

        assertEquals(Nulls.DEFAULT, valueNulls);
    }
}
