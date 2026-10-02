package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test30 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * A default StrSubstitutor uses '$' as its escape character, and replacing a
     * null CharSequence is a no-op that does not affect that configuration.
     */
    @Test(timeout = 4000)
    public void replacingNullCharSequenceLeavesDefaultEscapeCharUnchanged() throws Throwable {
        StrSubstitutor substitutor = new StrSubstitutor();

        substitutor.replace((CharSequence) null);

        assertEquals('$', substitutor.getEscapeChar());
    }
}
