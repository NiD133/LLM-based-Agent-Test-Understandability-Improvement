package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test11 extends Soundex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        Soundex soundexWithCustomMapping = new Soundex("#9fO$#-_", false);

        assertEquals(4, soundexWithCustomMapping.getMaxLength());
    }
}
