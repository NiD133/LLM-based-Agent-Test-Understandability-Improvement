package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test23 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        CharSequence[] charSequenceArray0 = new CharSequence[4];
        charSequenceArray0[0] = (CharSequence) "6~h5%B";
        charSequenceArray0[1] = (CharSequence) "6~h5%B";
        charSequenceArray0[2] = (CharSequence) "6~h5%B";
        charSequenceArray0[3] = (CharSequence) "6~h5%B";
        boolean boolean0 = WordUtils.containsAllWords("6~h5%B", charSequenceArray0);
        assertTrue(boolean0);
    }
}
