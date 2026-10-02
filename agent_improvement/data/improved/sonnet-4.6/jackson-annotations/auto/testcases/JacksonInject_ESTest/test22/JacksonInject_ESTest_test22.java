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
public class JacksonInject_ESTest_test22 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that constructing a Value with an empty-string id results in hasId() == false,
     * because the construct() method normalizes empty-string ids to null.
     */
    @Test(timeout = 4000)
    public void test_constructWithEmptyStringId_hasIdReturnsFalse() throws Throwable {
        // new Boolean("") evaluates to false (only "true" parses as true)
        Boolean optionalFlag = new Boolean("");

        // Passing "" as the id; construct() normalizes "" to null, so _id will be null
        JacksonInject.Value value = JacksonInject.Value.construct((Object) "", (Boolean) null, optionalFlag);

        // Since the id was normalized to null, hasId() should return false
        assertFalse(value.hasId());
    }
}
