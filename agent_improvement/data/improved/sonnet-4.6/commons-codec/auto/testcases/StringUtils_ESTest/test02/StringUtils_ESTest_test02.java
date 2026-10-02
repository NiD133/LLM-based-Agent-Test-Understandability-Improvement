package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtils_ESTest_test02 extends StringUtils_ESTest_scaffolding {

    // getBytesUnchecked returns null immediately when the input string is null,
    // regardless of whether the charset name is valid.
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        byte[] result = StringUtils.getBytesUnchecked((String) null, "&[:;4RVOrOFGnv4c^?");
        assertNull(result);
    }
}
