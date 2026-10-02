package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test38 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replacing a null char[] source is a safe no-op (the
     * offset/length arguments are irrelevant for a null source) and that the
     * interpolator created by {@link StringSubstitutor#createInterpolator()}
     * keeps the default escape character '$'.
     */
    @Test(timeout = 4000)
    public void replaceNullCharArraySourceReturnsNullAndKeepsDefaultEscapeChar() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        // The original test passed '$' (numeric value 36) as both offset and length;
        // for a null source these arguments are ignored.
        int offset = '$';
        int length = '$';
        String result = interpolator.replace((char[]) null, offset, length);

        assertNull("Replacing a null char[] source should return null", result);
        assertEquals('$', interpolator.getEscapeChar());
    }
}
