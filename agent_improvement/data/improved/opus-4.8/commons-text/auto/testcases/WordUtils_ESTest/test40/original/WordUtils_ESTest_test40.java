package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test40 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test40() throws Throwable {
        String string0 = WordUtils.wrap("upper value cannot be less than -1", 2);
        assertNotNull(string0);
        assertEquals("upper\r\nvalue\r\ncannot\r\nbe\r\nless\r\nthan\r\n-1", string0);
    }
}
