package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test37 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_emptyValue_hasNothingAsInclusionType() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;
        JsonTypeInfo.As inclusionType = emptyValue.getInclusionType();
        assertEquals("EMPTY value should have NOTHING as inclusion type",
                JsonTypeInfo.As.NOTHING, inclusionType);
    }
}
