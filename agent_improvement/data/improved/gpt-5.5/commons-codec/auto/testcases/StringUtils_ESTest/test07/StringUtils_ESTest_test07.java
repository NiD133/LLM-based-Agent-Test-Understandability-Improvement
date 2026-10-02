package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtils_ESTest_test07 extends StringUtils_ESTest_scaffolding {

    private static final String NUL_PADDED_TEXT = "[\u0000N\u0000Q\u0000.\u00003\u00008\u0000g\u0000~\u00009\u0000u\u0000=\u0000Y\u0000G\u0000O\u0000x\u0000n\u0000W\u0000";
    private static final String SAME_VISIBLE_TEXT_WITHOUT_NULS = "[NQ.38g~9u=YGOxnW";

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        CharBuffer nulPaddedBuffer = CharBuffer.wrap((CharSequence) NUL_PADDED_TEXT);

        boolean sequencesAreEqual = StringUtils.equals(
                (CharSequence) nulPaddedBuffer,
                (CharSequence) SAME_VISIBLE_TEXT_WITHOUT_NULS);

        assertFalse(sequencesAreEqual);
    }
}
