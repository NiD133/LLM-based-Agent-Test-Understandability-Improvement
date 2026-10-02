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
public class JacksonInject_ESTest_test26 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that constructing a Value with the EMPTY singleton as its id produces
     * a Value that is not equal to EMPTY, and that hasId() correctly reports true
     * because the id field is a non-null object reference.
     */
    @Test(timeout = 4000)
    public void test26() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.EMPTY;
        Boolean useInputFalse = Boolean.valueOf(false);
        Boolean optionalFalse = Boolean.valueOf(false);

        // Use the EMPTY singleton itself as the injection id.
        // Since EMPTY is a non-null, non-empty-String object, construct() keeps it as the id.
        JacksonInject.Value valueWithEmptyAsId =
                JacksonInject.Value.construct((Object) emptyValue, useInputFalse, optionalFalse);

        // The two Values differ: emptyValue has a null id, valueWithEmptyAsId does not.
        boolean valuesAreEqual = emptyValue.equals(valueWithEmptyAsId);
        assertFalse(valuesAreEqual);

        // hasId() must return true because the id was set to a non-null object (emptyValue).
        assertTrue(valueWithEmptyAsId.hasId());
    }
}
