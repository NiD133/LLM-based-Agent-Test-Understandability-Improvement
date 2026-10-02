package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test11 extends MurmurHash2_ESTest_scaffolding {

    private static final String INPUT_TEXT = "sM";
    private static final int SUBSTRING_START_INDEX = 367;
    private static final int SUBSTRING_LENGTH = 367;

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        try {
            MurmurHash2.hash64(INPUT_TEXT, SUBSTRING_START_INDEX, SUBSTRING_LENGTH);
            fail("Expecting exception: StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
        }
    }
}
