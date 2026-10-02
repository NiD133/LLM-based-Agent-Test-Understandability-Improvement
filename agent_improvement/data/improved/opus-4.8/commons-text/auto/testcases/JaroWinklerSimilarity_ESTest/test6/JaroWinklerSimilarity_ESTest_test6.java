package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JaroWinklerSimilarity_ESTest_test6 extends JaroWinklerSimilarity_ESTest_scaffolding {

    /**
     * Comparing a character sequence with itself must yield the maximum
     * similarity score of 1.0. Here the same CharBuffer instance is passed as
     * both inputs, so the two sequences are identical.
     */
    @Test(timeout = 4000)
    public void identicalSequencesReturnPerfectSimilarity() throws Throwable {
        JaroWinklerSimilarity similarity = JaroWinklerSimilarity.INSTANCE;
        CharBuffer sameSequence = CharBuffer.allocate(2);

        Double score = similarity.apply((CharSequence) sameSequence, (CharSequence) sameSequence);

        assertEquals(1.0, score.doubleValue(), 0.01);
    }
}
