package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test06 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        String string0 = WordUtils.wrap((String) null, 296, ";)(5b_Sh4o|A8@", true);
        assertNull(string0);
    }
}
