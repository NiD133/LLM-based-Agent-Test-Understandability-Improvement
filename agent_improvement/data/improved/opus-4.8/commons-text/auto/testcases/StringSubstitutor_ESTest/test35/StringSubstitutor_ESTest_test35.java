package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test35 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Replacing a null source (via the offset/length overload) is a no-op that returns null,
     * and doing so leaves the substitutor's configuration untouched. Verify that the default
     * interpolator still reports its default escape character ('$') afterwards.
     */
    @Test(timeout = 4000)
    public void test35() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();
        final int dollar = '$';

        interpolator.replace((CharSequence) null, dollar, dollar);

        assertEquals('$', interpolator.getEscapeChar());
    }
}
