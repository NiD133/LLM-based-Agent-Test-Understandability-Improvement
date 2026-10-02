package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test00 extends Nysiis_ESTest_scaffolding {

    private static final String NAME_WITH_PUNCTUATION = "&:ZN(sDK;@X'DhCe";
    private static final String NON_STRICT_NYSIIS_CODE = "ZNSDCXDC";

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Nysiis nonStrictEncoder = new Nysiis(false);

        String encodedName = nonStrictEncoder.nysiis(NAME_WITH_PUNCTUATION);

        assertEquals(NON_STRICT_NYSIIS_CODE, encodedName);
    }
}
