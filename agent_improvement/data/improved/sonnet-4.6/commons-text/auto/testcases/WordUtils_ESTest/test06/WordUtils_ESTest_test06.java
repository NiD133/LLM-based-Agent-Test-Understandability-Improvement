package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test06 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06_wrapNullStringReturnsNull() throws Throwable {
        // WordUtils.wrap documents that a null input returns null, regardless of other parameters
        String result = WordUtils.wrap(null, 296, ";)(5b_Sh4o|A8@", true);
        assertNull(result);
    }
}
