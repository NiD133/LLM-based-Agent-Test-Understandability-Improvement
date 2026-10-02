package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JaroWinklerSimilarity_ESTest_test1 extends JaroWinklerSimilarity_ESTest_scaffolding {

    /** Allowed floating-point error when comparing similarity scores. */
    private static final double SCORE_TOLERANCE = 0.01;

    /**
     * When one of the inputs is empty, no characters can match, so the
     * Jaro-Winkler similarity must be 0.0.
     *
     * <p>Here {@code emptyInput} is an empty sub-sequence (wrapping bytes
     * [2, 2) of a length-2 buffer yields length 0), compared against the
     * non-empty {@code twoCharInput}.</p>
     */
    @Test(timeout = 4000)
    public void applyReturnsZeroWhenOneInputIsEmpty() throws Throwable {
        JaroWinklerSimilarity similarity = JaroWinklerSimilarity.INSTANCE;

        CharBuffer twoCharInput = CharBuffer.allocate(2);
        CharBuffer emptyInput = CharBuffer.wrap((CharSequence) twoCharInput, 2, 2);

        Double score = similarity.apply((CharSequence) emptyInput, (CharSequence) twoCharInput);

        assertEquals(0.0, (double) score, SCORE_TOLERANCE);
    }
}
