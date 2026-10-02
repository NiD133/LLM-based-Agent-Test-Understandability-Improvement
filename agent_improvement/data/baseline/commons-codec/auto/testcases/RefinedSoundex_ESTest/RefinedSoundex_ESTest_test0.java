package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RefinedSoundex_ESTest_test0 extends RefinedSoundex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        RefinedSoundex refinedSoundex0 = new RefinedSoundex();
        Object object0 = refinedSoundex0.US_ENGLISH.encode((Object) "01360240043788015936020505");
        assertEquals("", object0);
    }
}
