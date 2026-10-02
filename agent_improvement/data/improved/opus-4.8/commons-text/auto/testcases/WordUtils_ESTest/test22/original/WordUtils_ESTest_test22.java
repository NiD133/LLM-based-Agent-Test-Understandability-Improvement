package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test22 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        CharSequence[] charSequenceArray0 = new CharSequence[4];
        charSequenceArray0[0] = (CharSequence) "The validated object is null";
        boolean boolean0 = WordUtils.containsAllWords("The Validated Object Is Null", charSequenceArray0);
        assertFalse(boolean0);
    }
}
