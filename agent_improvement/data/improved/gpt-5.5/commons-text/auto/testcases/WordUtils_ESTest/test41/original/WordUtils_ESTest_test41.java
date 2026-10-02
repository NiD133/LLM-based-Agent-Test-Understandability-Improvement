package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test41 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test41() throws Throwable {
        String string0 = WordUtils.uncapitalize(" ");
        assertEquals(" ", string0);
    }
}
