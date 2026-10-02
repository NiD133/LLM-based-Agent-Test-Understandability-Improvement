package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test00 extends Nysiis_ESTest_scaffolding {

    /**
     * In non-strict mode the NYSIIS code is not truncated to 6 characters, so a
     * longer input can yield a key longer than 6. Non-letter characters are
     * stripped before encoding, so "&:ZN(sDK;@X'DhCe" is encoded as if it were
     * "ZNSDKXDHCE".
     */
    @Test(timeout = 4000)
    public void encodeStripsNonLettersAndKeepsFullLengthInNonStrictMode() throws Throwable {
        Nysiis nonStrictNysiis = new Nysiis(false);

        String code = nonStrictNysiis.nysiis("&:ZN(sDK;@X'DhCe");

        assertEquals("ZNSDCXDC", code);
    }
}
