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
public class LevenshteinDetailedDistance_ESTest_test17 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance();

        char[] leftCharacters = new char[7];
        leftCharacters[3] = 'S';

        char[] rightCharacters = new char[8];
        rightCharacters[1] = 'S';
        rightCharacters[3] = 'f';
        rightCharacters[5] = 'S';

        CharBuffer rightBuffer = CharBuffer.wrap(rightCharacters);
        CharBuffer leftBuffer = CharBuffer.wrap(leftCharacters);

        LevenshteinResults result = distance.apply((CharSequence) leftBuffer, (CharSequence) rightBuffer);

        assertEquals(0, (int) result.getDeleteCount());
        assertEquals(3, (int) result.getSubstituteCount());
    }
}
