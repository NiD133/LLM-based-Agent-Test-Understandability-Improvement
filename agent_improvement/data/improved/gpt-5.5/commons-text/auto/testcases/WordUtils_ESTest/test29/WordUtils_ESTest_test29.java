package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test29 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test29() throws Throwable {
        final String textWithoutSpaces = "b6Qf>#}c]W/%TXT9},Eb";
        final int lowerLimit = 7;
        final int upperLimit = 7;
        final String suffixAfterAbbreviation = "b6Qf>#}c]W/%TXT9},Eb";

        final String abbreviated = WordUtils.abbreviate(
                textWithoutSpaces,
                lowerLimit,
                upperLimit,
                suffixAfterAbbreviation);

        assertEquals("b6Qf>#}b6Qf>#}c]W/%TXT9},Eb", abbreviated);
    }
}
