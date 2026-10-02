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
public class JacksonInject_ESTest_test05 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that a JacksonInject.Value created with a non-null id:
     * 1. Reports hasId() == true
     * 2. Is equal to itself (reflexive equality)
     */
    @Test(timeout = 4000)
    public void test05_valueWithId_hasIdAndIsEqualToItself() throws Throwable {
        Object injectionId = new Object();
        JacksonInject.Value valueWithId = JacksonInject.Value.forId(injectionId);

        boolean isEqualToItself = valueWithId.equals(valueWithId);

        assertTrue("Value created with a non-null id should report hasId() == true", valueWithId.hasId());
        assertTrue("A Value must be equal to itself (reflexive equality)", isEqualToItself);
    }
}
