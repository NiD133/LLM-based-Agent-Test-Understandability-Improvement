package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test19 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        char[] charArray0 = new char[4];
        String string0 = WordUtils.initials("R'G4}z<EYofF'C", charArray0);
        assertEquals("R", string0);
    }
}
