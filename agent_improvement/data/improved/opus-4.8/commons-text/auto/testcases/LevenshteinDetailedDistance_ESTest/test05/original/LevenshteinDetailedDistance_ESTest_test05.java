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
public class LevenshteinDetailedDistance_ESTest_test05 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        CharBuffer charBuffer0 = CharBuffer.allocate(13);
        Integer integer0 = new Integer(13);
        LevenshteinDetailedDistance levenshteinDetailedDistance0 = new LevenshteinDetailedDistance(integer0);
        LevenshteinResults levenshteinResults0 = levenshteinDetailedDistance0.apply((CharSequence) charBuffer0, (CharSequence) "E6sZn1lY$kTP*\"");
        assertEquals((-1), (int) levenshteinResults0.getDistance());
    }
}
