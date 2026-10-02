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

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        char[] charArray0 = new char[9];
        Soundex soundex0 = new Soundex(charArray0);
        soundex0.US_ENGLISH.setMaxLength(0);
        assertEquals(4, soundex0.getMaxLength());
    }
}
