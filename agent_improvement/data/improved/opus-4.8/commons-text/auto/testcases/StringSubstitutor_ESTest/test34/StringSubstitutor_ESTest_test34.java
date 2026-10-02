package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test34 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Replacing a null source should return null, and the interpolator created
     * by {@link StringSubstitutor#createInterpolator()} should use the default
     * escape character '$'.
     */
    @Test(timeout = 4000)
    public void replaceNullSourceReturnsNullAndKeepsDefaultEscapeChar() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        String result = interpolator.replace((Object) null);

        assertNull("Replacing a null source should yield null", result);
        assertEquals("Interpolator should use the default escape character",
                '$', interpolator.getEscapeChar());
    }
}
