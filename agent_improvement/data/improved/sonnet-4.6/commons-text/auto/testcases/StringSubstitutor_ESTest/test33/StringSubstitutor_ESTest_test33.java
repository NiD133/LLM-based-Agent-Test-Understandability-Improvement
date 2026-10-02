package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test33 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replacing a null string is a no-op and that the default
     * escape character remains '$' after the call.
     */
    @Test(timeout = 4000)
    public void test_replaceNullString_defaultEscapeCharIsPreserved() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();

        // replace(null) should complete without throwing
        substitutor.replace((String) null);

        // The default escape character must be '$'
        assertEquals('$', substitutor.getEscapeChar());
    }
}
