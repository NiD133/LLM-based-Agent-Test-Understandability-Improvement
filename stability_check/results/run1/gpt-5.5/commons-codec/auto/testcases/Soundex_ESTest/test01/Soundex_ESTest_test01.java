package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test01 extends Soundex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Soundex usEnglishSoundex = Soundex.US_ENGLISH;

        // Verifies that the default US English encoder accepts a null String input.
        usEnglishSoundex.encode((String) null);
    }
}
