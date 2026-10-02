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
public class ExtraFieldUtils_ESTest_test14 extends ExtraFieldUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        // Exercises the public constructor; the original test expects construction to complete normally.
        ExtraFieldUtils extraFieldUtils = new ExtraFieldUtils();
    }
}
