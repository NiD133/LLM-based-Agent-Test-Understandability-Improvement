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
public class JacksonInject_ESTest_test01 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that two Value instances produced by calling withOptional() with the
     * same argument on EMPTY are equal to each other but distinct from EMPTY, and
     * that the two resulting instances are not the same object reference.
     */
    @Test(timeout = 4000)
    public void test_withOptional_producesEqualButDistinctInstancesFromEmpty() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.EMPTY;

        // Boolean(String) treats any string other than "true" (case-insensitive) as false
        Boolean falseBoolean = new Boolean("sE]@ 6W)1`^'M<pcHc");

        JacksonInject.Value firstValueWithOptional = emptyValue.withOptional(falseBoolean);
        JacksonInject.Value secondValueWithOptional = emptyValue.withOptional(falseBoolean);

        // Two independently created values with the same optional flag should be equal
        boolean areEqual = secondValueWithOptional.equals(firstValueWithOptional);
        assertTrue(areEqual);

        // Both should differ from the EMPTY value (which has optional == null)
        assertFalse(secondValueWithOptional.equals((Object) emptyValue));

        // They should be distinct object references despite being equal by value
        assertNotSame(secondValueWithOptional, firstValueWithOptional);
    }
}
