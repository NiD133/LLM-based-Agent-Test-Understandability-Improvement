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
     * A default StringSubstitutor uses '$' as its escape character, and calling
     * replace() with a null source is a no-op that leaves the escape character unchanged.
     */
    @Test(timeout = 4000)
    public void replaceNullSourceKeepsDefaultEscapeChar() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();

        substitutor.replace((String) null);

        assertEquals('$', substitutor.getEscapeChar());
    }
}
