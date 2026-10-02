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
public class DamerauLevenshteinDistance_ESTest_test10 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        Integer integer0 = new Integer(4);
        DamerauLevenshteinDistance damerauLevenshteinDistance0 = new DamerauLevenshteinDistance(integer0);
        CharBuffer charBuffer0 = CharBuffer.allocate(4);
        Integer integer1 = damerauLevenshteinDistance0.apply((CharSequence) charBuffer0, (CharSequence) charBuffer0);
        assertEquals(0, (int) integer1);
    }
}
