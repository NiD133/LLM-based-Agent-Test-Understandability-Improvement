package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RefinedSoundex_ESTest_test2 extends RefinedSoundex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        char[] charArray0 = new char[3];
        RefinedSoundex refinedSoundex0 = new RefinedSoundex(charArray0);
        char char0 = refinedSoundex0.getMappingCode('9');
        assertEquals('\u0000', char0);
    }
}
