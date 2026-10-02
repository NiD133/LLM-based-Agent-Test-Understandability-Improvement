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
public class JsonTypeInfo_ESTest_test35 extends JsonTypeInfo_ESTest_scaffolding {

    // JsonTypeInfo.Value.EMPTY is a pre-built sentinel with no type identification enabled.
    // Its idType must be NONE, meaning no type metadata will be serialized or expected.
    @Test(timeout = 4000)
    public void test_emptyValue_hasIdTypeNone() throws Throwable {
        JsonTypeInfo.Value emptyTypeInfoValue = JsonTypeInfo.Value.EMPTY;
        JsonTypeInfo.Id idType = emptyTypeInfoValue.getIdType();
        assertEquals(JsonTypeInfo.Id.NONE, idType);
    }
}
