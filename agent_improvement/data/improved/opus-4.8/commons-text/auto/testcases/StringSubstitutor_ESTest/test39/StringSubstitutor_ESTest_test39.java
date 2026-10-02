package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test39 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Replacing a null char[] source should be handled gracefully and must not
     * change the substitutor's configuration. Here we confirm the default escape
     * character ('$') remains in place after such a call.
     */
    @Test(timeout = 4000)
    public void replaceNullCharArrayKeepsDefaultEscapeChar() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        interpolator.replace((char[]) null);

        assertEquals('$', interpolator.getEscapeChar());
    }
}
