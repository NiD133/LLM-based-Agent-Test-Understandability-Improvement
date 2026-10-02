package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test19 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        final char[] leftCharacters = new char[7];
        final char[] rightCharacters = new char[8];
        rightCharacters[2] = 'f';
        leftCharacters[3] = 'f';
        rightCharacters[6] = 'f';
        rightCharacters[7] = 'f';

        final CharBuffer rightInput = CharBuffer.wrap(rightCharacters);
        final Integer threshold = new Integer(7);
        final LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(threshold);
        final CharBuffer leftInput = CharBuffer.wrap(leftCharacters);

        final LevenshteinResults result = distance.apply((CharSequence) leftInput, (CharSequence) rightInput);

        assertEquals(3, (int) result.getDistance());
    }
}
