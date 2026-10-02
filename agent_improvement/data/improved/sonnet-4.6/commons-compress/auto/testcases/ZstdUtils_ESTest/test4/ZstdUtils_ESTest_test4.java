package org.apache.commons.compress.compressors.zstandard;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test4 extends ZstdUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_matches_returnsFalse_whenNullSignatureAndNegativeLength() throws Throwable {
        // A negative length is less than the required magic byte length (4),
        // so matches() should return false immediately without accessing the null array.
        byte[] nullSignature = null;
        int negativeLength = -1988;

        boolean result = ZstdUtils.matches(nullSignature, negativeLength);

        assertFalse(result);
    }
}
