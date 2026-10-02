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

    /**
     * When upper == lower and the input contains no spaces, abbreviate() truncates at
     * upper and appends the appendToEnd string unconditionally, because the string is
     * longer than upper and no word boundary was found.
     *
     * Input:       "b6Qf>#}c]W/%TXT9},Eb"  (20 chars, no spaces)
     * lower/upper: 7 / 7
     * appendToEnd: "b6Qf>#}c]W/%TXT9},Eb"
     *
     * Expected:    first 7 chars + appendToEnd = "b6Qf>#}b6Qf>#}c]W/%TXT9},Eb"
     */
    @Test(timeout = 4000)
    public void test29() throws Throwable {
        String input       = "b6Qf>#}c]W/%TXT9},Eb";
        int    lower       = 7;
        int    upper       = 7;
        String appendToEnd = "b6Qf>#}c]W/%TXT9},Eb";

        String abbreviated = WordUtils.abbreviate(input, lower, upper, appendToEnd);

        assertEquals("b6Qf>#}b6Qf>#}c]W/%TXT9},Eb", abbreviated);
    }
}
