package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.zip.ZipException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ExtraFieldUtils_ESTest_test13 extends ExtraFieldUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        ExtraFieldUtils.UnparseableExtraField extraFieldUtils_UnparseableExtraField0 = ExtraFieldUtils.UnparseableExtraField.SKIP;
        int int0 = extraFieldUtils_UnparseableExtraField0.getKey();
        assertEquals(1, int0);
    }
}
