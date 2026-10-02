package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test37 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that a default StrSubstitutor has escape preservation disabled
     * and uses '$' as the escape character.
     */
    @Test(timeout = 4000)
    public void test_defaultConstructor_hasExpectedEscapeDefaults() throws Throwable {
        StrSubstitutor substitutor = new StrSubstitutor();

        assertFalse("Escape preservation should be disabled by default",
                substitutor.isPreserveEscapes());
        assertEquals("Default escape character should be '$'",
                '$', substitutor.getEscapeChar());
    }
}
