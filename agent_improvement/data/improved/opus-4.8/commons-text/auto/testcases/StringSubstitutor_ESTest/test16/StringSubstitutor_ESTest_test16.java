package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test16 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replaceIn(StringBuilder, offset, length) returns false when the
     * source is null, and that the default interpolator uses '$' as its escape char.
     */
    @Test(timeout = 4000)
    public void replaceInNullStringBuilderReturnsFalse() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        StringBuilder nullSource = null;
        int offset = '$';
        int length = '$';
        boolean replaced = interpolator.replaceIn(nullSource, offset, length);

        assertFalse("A null source should never be substituted", replaced);
        assertEquals("The default interpolator escape char should be '$'",
                '$', interpolator.getEscapeChar());
    }
}
