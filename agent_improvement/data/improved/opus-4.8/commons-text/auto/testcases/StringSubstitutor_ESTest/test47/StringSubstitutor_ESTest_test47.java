package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test47 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * The static {@link StringSubstitutor#replaceSystemProperties(Object)} helper performs a
     * one-off substitution and does not touch a separately constructed instance. A
     * default-constructed substitutor should therefore still report the default escape
     * character ('$') afterwards.
     */
    @Test(timeout = 4000)
    public void escapeCharIsDefaultDollarSignAfterReplaceSystemProperties() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();

        StringSubstitutor.replaceSystemProperties(substitutor);

        assertEquals('$', substitutor.getEscapeChar());
    }
}
