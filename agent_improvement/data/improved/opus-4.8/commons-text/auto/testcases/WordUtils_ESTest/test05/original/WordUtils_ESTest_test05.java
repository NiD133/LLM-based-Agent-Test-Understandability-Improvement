package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test05 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        String string0 = WordUtils.wrap("?*!2g,cNA6z2~8(~C,", 259, "*|", true);
        assertEquals("?*!2g,cNA6z2~8(~C,", string0);
    }
}
