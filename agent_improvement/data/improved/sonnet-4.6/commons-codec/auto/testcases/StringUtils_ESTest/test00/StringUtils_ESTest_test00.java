package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtils_ESTest_test00 extends StringUtils_ESTest_scaffolding {

    // When the byte array is null, newString should return null regardless of the charset name.
    @Test(timeout = 4000)
    public void test_newString_withNullBytesAndNullCharset_returnsNull() throws Throwable {
        String result = StringUtils.newString((byte[]) null, (String) null);
        assertNull(result);
    }
}
