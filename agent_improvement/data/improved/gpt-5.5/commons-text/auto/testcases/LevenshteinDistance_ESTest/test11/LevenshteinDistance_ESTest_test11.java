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
public class LevenshteinDistance_ESTest_test11 extends LevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        Integer threshold = new Integer(0);
        LevenshteinDistance distanceWithZeroThreshold = new LevenshteinDistance(threshold);
        CharBuffer emptyRightInput = CharBuffer.allocate(0);

        Integer distance = distanceWithZeroThreshold.apply(
                (CharSequence) "or.apace.commons.tex.StrLokp",
                (CharSequence) emptyRightInput);

        assertEquals((-1), (int) distance);
    }
}
