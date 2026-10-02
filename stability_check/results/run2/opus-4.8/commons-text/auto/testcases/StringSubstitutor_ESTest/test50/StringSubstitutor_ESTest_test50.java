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
     * The copy constructor should carry over the escape character from the
     * source substitutor. An interpolator uses the default escape char '$',
     * so a copy of it must report the same escape char.
     */
    @Test(timeout = 4000)
    public void copyConstructorPreservesDefaultEscapeChar() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        StringSubstitutor copy = new StringSubstitutor(interpolator);

        assertEquals('$', copy.getEscapeChar());
    }
}
