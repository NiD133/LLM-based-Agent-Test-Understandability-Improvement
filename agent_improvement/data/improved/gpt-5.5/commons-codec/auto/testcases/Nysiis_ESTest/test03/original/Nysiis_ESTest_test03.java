package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test03 extends Nysiis_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Nysiis nysiis0 = new Nysiis();
        nysiis0.nysiis("");
        assertTrue(nysiis0.isStrict());
    }
}
