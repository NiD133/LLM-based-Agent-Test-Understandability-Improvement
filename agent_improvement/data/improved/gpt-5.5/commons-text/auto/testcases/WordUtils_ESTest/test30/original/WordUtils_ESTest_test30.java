package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test30 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test30() throws Throwable {
        String string0 = WordUtils.abbreviate("Y oD/'", 0, 1, "Upper value cannot be less than -1");
        assertEquals("YUpper value cannot be less than -1", string0);
    }
}
