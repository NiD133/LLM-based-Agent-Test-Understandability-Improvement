package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test31 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replacing a null source string returns null without throwing,
     * even when the supplied offset and length are out of any valid range, and that
     * the default escape character of an interpolator remains '$'.
     */
    @Test(timeout = 4000)
    public void replaceNullSourceReturnsNullAndKeepsDefaultEscapeChar() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        String result = interpolator.replace((String) null, 807, 807);

        assertNull("Replacing a null source must return null", result);
        assertEquals("Interpolator should keep the default '$' escape character",
                '$', interpolator.getEscapeChar());
    }
}
