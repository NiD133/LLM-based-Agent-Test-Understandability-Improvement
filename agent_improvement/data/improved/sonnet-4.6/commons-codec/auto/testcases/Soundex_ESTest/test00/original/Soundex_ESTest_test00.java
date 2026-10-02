package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test00 extends Soundex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Soundex soundex0 = Soundex.US_ENGLISH;
        String string0 = soundex0.encode("vyHWF);{");
        assertEquals("V100", string0);
    }
}
