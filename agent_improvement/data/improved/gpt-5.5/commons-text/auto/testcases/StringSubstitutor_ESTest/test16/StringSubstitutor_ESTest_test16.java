package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test16 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        final StringBuilder nullSourceBuffer = null;
        final int startOffset = '$';
        final int replacementLength = '$';

        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        boolean wasModified = interpolator.replaceIn(nullSourceBuffer, startOffset, replacementLength);

        assertEquals('$', interpolator.getEscapeChar());
        assertFalse(wasModified);
    }
}
