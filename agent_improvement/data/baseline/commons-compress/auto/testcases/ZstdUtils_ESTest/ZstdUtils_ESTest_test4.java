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
    public void test4() throws Throwable {
        boolean boolean0 = ZstdUtils.matches((byte[]) null, (-1988));
        assertFalse(boolean0);
    }
}
