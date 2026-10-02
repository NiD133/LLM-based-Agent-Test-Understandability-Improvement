package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test35 extends StringSubstitutor_ESTest_scaffolding {

    // '$' as an int (ASCII 36) is used as the offset and length arguments
    private static final int DOLLAR_SIGN_AS_INT = (int) '$';

    /**
     * Verifies that calling replace() with a null CharSequence does not alter the
     * escape character of a StringSubstitutor created via createInterpolator().
     * The default escape character is '$'.
     */
    @Test(timeout = 4000)
    public void test_replaceNullCharSequence_doesNotChangeDefaultEscapeChar() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        // replace() should handle null source without throwing
        interpolator.replace((CharSequence) null, DOLLAR_SIGN_AS_INT, DOLLAR_SIGN_AS_INT);

        assertEquals('$', interpolator.getEscapeChar());
    }
}
