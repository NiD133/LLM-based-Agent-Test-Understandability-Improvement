package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JaroWinklerSimilarity_ESTest_test2 extends JaroWinklerSimilarity_ESTest_scaffolding {

    /**
     * Tests Jaro-Winkler similarity between a long null-char sequence (1772 chars) and
     * a short null-char sequence (8 chars). Despite both containing only null characters,
     * the large length difference yields a partial similarity of ~0.668.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        // Short sequence: 8 null characters wrapped in a CharBuffer
        char[] eightNullChars = new char[8];
        CharBuffer shortNullCharBuffer = CharBuffer.wrap(eightNullChars);

        // Long sequence: 1772 null characters allocated in a CharBuffer
        CharBuffer longNullCharBuffer = CharBuffer.allocate(1772);

        JaroWinklerSimilarity jaroWinklerSimilarity = JaroWinklerSimilarity.INSTANCE;

        // Compute similarity: long sequence vs short sequence
        Double similarity = jaroWinklerSimilarity.apply((CharSequence) longNullCharBuffer, (CharSequence) shortNullCharBuffer);

        assertEquals(0.6681715575620767, (double) similarity, 0.01);
    }
}
