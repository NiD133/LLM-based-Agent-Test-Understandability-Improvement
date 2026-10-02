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
     * Verifies that passing a StringSubstitutor instance as the source object to
     * replaceSystemProperties() does not mutate the instance's escape character.
     * The static method treats the argument as text via toString(), leaving the
     * substitutor's own configuration (escape char = '$') intact.
     */
    @Test(timeout = 4000)
    public void test47() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();

        // Pass the substitutor itself as the Object source — replaceSystemProperties
        // resolves ${...} tokens in substitutor.toString(), but does not alter the instance.
        StringSubstitutor.replaceSystemProperties(substitutor);

        // The escape character must remain the default '$' (StringSubstitutor.DEFAULT_ESCAPE).
        assertEquals('$', substitutor.getEscapeChar());
    }
}
