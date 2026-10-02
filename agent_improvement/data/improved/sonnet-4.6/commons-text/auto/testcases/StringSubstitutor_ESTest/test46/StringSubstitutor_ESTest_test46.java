package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test46 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test46_setValueDelimiterDoesNotChangeEscapeChar() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        // setValueDelimiter returns 'this' for fluent chaining; verify escape char is unaffected
        substitutor.setValueDelimiter('$');
        assertEquals('$', substitutor.getEscapeChar());
    }
}
