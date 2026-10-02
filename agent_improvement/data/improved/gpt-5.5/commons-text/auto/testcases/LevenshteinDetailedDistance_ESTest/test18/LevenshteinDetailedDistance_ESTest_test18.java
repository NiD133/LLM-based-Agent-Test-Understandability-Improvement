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
public class LevenshteinDetailedDistance_ESTest_test18 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        char[] targetCharacters = new char[7];
        targetCharacters[3] = 'S';

        char[] sourceCharacters = new char[8];
        sourceCharacters[0] = 'S';
        sourceCharacters[2] = 'S';
        sourceCharacters[5] = 'S';

        CharBuffer sourceBuffer = CharBuffer.wrap(sourceCharacters);
        CharBuffer targetBuffer = CharBuffer.wrap(targetCharacters);
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance();

        LevenshteinResults results = distance.apply((CharSequence) sourceBuffer, (CharSequence) targetBuffer);

        assertEquals(3, (int) results.getDistance());
        assertEquals(0, (int) results.getSubstituteCount());
    }
}
