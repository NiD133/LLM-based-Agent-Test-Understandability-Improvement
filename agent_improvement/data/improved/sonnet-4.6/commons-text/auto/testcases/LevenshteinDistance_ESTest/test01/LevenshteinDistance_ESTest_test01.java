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
public class LevenshteinDistance_ESTest_test01 extends LevenshteinDistance_ESTest_scaffolding {

    // "|!(,ny:" -> "|!(,n:" differs by a single character deletion ('y'), so the distance is 1
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        LevenshteinDistance levenshteinDistance0 = LevenshteinDistance.getDefaultInstance();
        Integer distance = levenshteinDistance0.apply((CharSequence) "|!(,ny:", (CharSequence) "|!(,n:");
        assertEquals(1, (int) distance);
    }
}
