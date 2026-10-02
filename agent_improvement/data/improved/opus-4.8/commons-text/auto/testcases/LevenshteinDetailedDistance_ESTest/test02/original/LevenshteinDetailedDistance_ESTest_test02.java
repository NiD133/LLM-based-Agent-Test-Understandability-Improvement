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
public class LevenshteinDetailedDistance_ESTest_test02 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        LevenshteinDetailedDistance levenshteinDetailedDistance0 = new LevenshteinDetailedDistance();
        LevenshteinResults levenshteinResults0 = levenshteinDetailedDistance0.apply((CharSequence) "", (CharSequence) "");
        assertEquals(0, (int) levenshteinResults0.getInsertCount());
        assertEquals(0, (int) levenshteinResults0.getSubstituteCount());
        assertEquals(0, (int) levenshteinResults0.getDeleteCount());
    }
}
