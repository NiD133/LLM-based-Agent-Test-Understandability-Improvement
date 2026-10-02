package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test28 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Replacing a null StringBuffer should be handled gracefully (no exception),
     * and must not affect the substitutor's configuration such as its default
     * escape character ('$').
     */
    @Test(timeout = 4000)
    public void testReplaceNullStringBufferKeepsDefaultEscapeChar() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();

        substitutor.replace((StringBuffer) null);

        assertEquals('$', substitutor.getEscapeChar());
    }
}
