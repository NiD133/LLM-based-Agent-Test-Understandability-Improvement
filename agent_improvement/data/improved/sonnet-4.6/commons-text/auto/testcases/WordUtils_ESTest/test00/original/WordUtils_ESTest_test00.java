package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test00 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        String string0 = WordUtils.wrap("|eT ($ElK)2^p:", (-3137), "|eT ($ElK)2^p:", false, "|eT ($ElK)2^p:");
        assertEquals("eT|eT ($ElK)2^p:$E|eT ($ElK)2^p:)2|eT ($ElK)2^p:p:", string0);
    }
}
