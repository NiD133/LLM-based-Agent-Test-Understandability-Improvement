package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDistance_ESTest_test13 extends LevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        Integer integer0 = new Integer(0);
        LevenshteinDistance levenshteinDistance0 = new LevenshteinDistance(integer0);
        CharBuffer charBuffer0 = CharBuffer.allocate(0);
        Integer integer1 = levenshteinDistance0.apply((CharSequence) charBuffer0, (CharSequence) "&D/r2c/;WL~$DxE.m");
        assertEquals((-1), (int) integer1);
    }
}
