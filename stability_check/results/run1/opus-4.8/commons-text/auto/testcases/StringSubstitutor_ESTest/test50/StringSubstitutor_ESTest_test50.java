package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test50 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * The copy constructor should carry over the escape character from the source
     * instance. An interpolator created via {@link StringSubstitutor#createInterpolator()}
     * uses the default escape character '$', so the copy must expose the same value.
     */
    @Test(timeout = 4000)
    public void copyConstructorPreservesDefaultEscapeChar() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();
        StringSubstitutor copy = new StringSubstitutor(interpolator);

        assertEquals('$', copy.getEscapeChar());
    }
}
