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
public class DamerauLevenshteinDistance_ESTest_test07 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        Integer integer0 = new Integer(582);
        DamerauLevenshteinDistance damerauLevenshteinDistance0 = new DamerauLevenshteinDistance(integer0);
        CharBuffer charBuffer0 = CharBuffer.allocate(582);
        CharBuffer charBuffer1 = CharBuffer.wrap((CharSequence) charBuffer0, 582, 582);
        Integer integer1 = damerauLevenshteinDistance0.apply((CharSequence) charBuffer0, (CharSequence) charBuffer1);
        assertEquals(582, (int) integer1);
    }
}
