package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test09 extends Soundex_ESTest_scaffolding {

    /**
     * Verifies that calling setMaxLength on the shared US_ENGLISH static instance
     * does not affect the maxLength of a separately constructed Soundex instance.
     * The local instance retains the default maxLength of 4.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        char[] customMapping = new char[9];
        Soundex customSoundex = new Soundex(customMapping);

        // Mutate the static US_ENGLISH instance's maxLength — this should not affect customSoundex
        Soundex.US_ENGLISH.setMaxLength(0);

        // customSoundex was constructed independently and retains the default maxLength of 4
        assertEquals(4, customSoundex.getMaxLength());
    }
}
