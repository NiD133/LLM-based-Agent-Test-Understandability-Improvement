package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RefinedSoundex_ESTest_test4 extends RefinedSoundex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        char[] charArray0 = new char[6];
        RefinedSoundex refinedSoundex0 = new RefinedSoundex(charArray0);
        int int0 = refinedSoundex0.difference("org.apache.commons.codec.EncoderException", "org.apache.commons.codec.EncoderException");
        assertEquals(1, int0);
    }
}
