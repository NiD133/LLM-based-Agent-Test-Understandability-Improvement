package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test06 extends Soundex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Soundex soundex0 = new Soundex("-123-12--22455-12623-1-2-2");
        assertEquals(4, soundex0.getMaxLength());
    }
}
