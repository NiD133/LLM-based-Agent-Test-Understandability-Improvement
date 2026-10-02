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
public class DamerauLevenshteinDistance_ESTest_test02 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Source: a buffer of 116 null characters
        CharBuffer sourceBuffer = CharBuffer.allocate(116);

        // Unlimited distance calculator (no threshold)
        DamerauLevenshteinDistance distanceCalculator = new DamerauLevenshteinDistance();

        // Target: an empty slice of sourceBuffer (start == end == 116), representing the empty string
        CharBuffer emptyTargetBuffer = CharBuffer.wrap((CharSequence) sourceBuffer, 116, 116);

        // Transforming 116 characters into an empty sequence requires exactly 116 deletions
        Integer distance = distanceCalculator.apply((CharSequence) sourceBuffer, (CharSequence) emptyTargetBuffer);
        assertEquals(116, (int) distance);
    }
}
