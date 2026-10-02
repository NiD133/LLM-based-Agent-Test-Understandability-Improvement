package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test10 extends Soundex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_getMaxLength_returnsDefaultFourForNewSoundex() throws Throwable {
        Soundex soundex = new Soundex();
        int maxLength = soundex.getMaxLength();
        // Soundex codes are always exactly 4 characters by definition
        assertEquals(4, maxLength);
    }
}
