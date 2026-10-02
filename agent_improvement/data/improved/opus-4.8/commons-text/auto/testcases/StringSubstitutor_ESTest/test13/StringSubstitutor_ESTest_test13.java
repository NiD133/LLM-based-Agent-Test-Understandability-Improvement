package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test13 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * replaceIn(TextStringBuilder, offset, length) should report "no substitution made"
     * (return false) when the requested length is negative, and the substitutor's
     * configuration (here the default escape char '$') should be left untouched.
     */
    @Test(timeout = 4000)
    public void replaceInWithNegativeLengthMakesNoChange() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();

        TextStringBuilder source = TextStringBuilder.wrap(new char[1]);
        int offset = 0;
        int negativeLength = -2281;

        boolean altered = substitutor.replaceIn(source, offset, negativeLength);

        assertFalse("negative length must not alter the source", altered);
        assertEquals("default escape char should remain '$'", '$', substitutor.getEscapeChar());
    }
}
