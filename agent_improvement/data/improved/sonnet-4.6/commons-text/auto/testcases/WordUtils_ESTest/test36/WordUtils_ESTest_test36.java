package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test36 extends WordUtils_ESTest_scaffolding {

    // WordUtils documents that its constructor is public to permit JavaBean tooling,
    // even though normal usage is via the static utility methods.
    @Test(timeout = 4000)
    public void test_constructorIsPublicAndInstantiatesWithoutError() throws Throwable {
        WordUtils wordUtils0 = new WordUtils();
    }
}
