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
    public void test10() throws Throwable {
        Soundex defaultSoundex = new Soundex();

        int defaultMaxLength = defaultSoundex.getMaxLength();

        assertEquals(4, defaultMaxLength);
    }
}
