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
public class DamerauLevenshteinDistance_ESTest_test01 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        DamerauLevenshteinDistance damerauLevenshteinDistance0 = new DamerauLevenshteinDistance();
        Integer integer0 = damerauLevenshteinDistance0.apply((CharSequence) "@", (CharSequence) "*@");
        assertEquals(1, (int) integer0);
    }
}
