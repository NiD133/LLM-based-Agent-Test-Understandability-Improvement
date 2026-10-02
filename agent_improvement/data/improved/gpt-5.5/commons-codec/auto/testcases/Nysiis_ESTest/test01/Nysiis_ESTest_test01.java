package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test01 extends Nysiis_ESTest_scaffolding {

    private static final String INPUT_WITH_NON_LETTERS = "&:ZN(sDK;@X'DhCe";
    private static final String EXPECTED_STRICT_NYSIIS_CODE = "ZNSDCX";

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Nysiis encoder = new Nysiis();

        String actualCode = encoder.nysiis(INPUT_WITH_NON_LETTERS);

        assertEquals(EXPECTED_STRICT_NYSIIS_CODE, actualCode);
    }
}
