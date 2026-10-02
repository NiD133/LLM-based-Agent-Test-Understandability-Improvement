package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test39 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_interpolatorHasDefaultEscapeChar_afterReplaceNullCharArray() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        // Replacing a null char array is a no-op; the substitutor state should remain unchanged.
        interpolator.replace((char[]) null);

        // The default escape character for variable substitution is '$'.
        assertEquals('$', interpolator.getEscapeChar());
    }
}
