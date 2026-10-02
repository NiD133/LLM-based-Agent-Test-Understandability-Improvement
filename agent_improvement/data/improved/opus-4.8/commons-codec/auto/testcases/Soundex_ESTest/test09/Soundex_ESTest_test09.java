package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test09 extends Soundex_ESTest_scaffolding {

    /**
     * Verifies that changing the maxLength on the shared {@link Soundex#US_ENGLISH}
     * instance does not affect a separately constructed Soundex instance: each
     * instance keeps its own maxLength, which defaults to 4.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        char[] customMapping = new char[9];
        Soundex soundex = new Soundex(customMapping);

        // Mutate the shared static US_ENGLISH instance, not the local one.
        Soundex.US_ENGLISH.setMaxLength(0);

        // The locally created instance is unaffected and retains the default maxLength.
        assertEquals(4, soundex.getMaxLength());
    }
}
