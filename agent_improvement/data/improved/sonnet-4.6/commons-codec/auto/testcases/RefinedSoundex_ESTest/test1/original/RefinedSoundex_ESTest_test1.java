package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RefinedSoundex_ESTest_test1 extends RefinedSoundex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        RefinedSoundex refinedSoundex0 = new RefinedSoundex();
        int int0 = refinedSoundex0.difference((String) null, "ov9Sw<^R");
        assertEquals(0, int0);
    }
}
