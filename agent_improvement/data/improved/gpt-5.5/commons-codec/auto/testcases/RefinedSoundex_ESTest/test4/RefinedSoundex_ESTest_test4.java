package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RefinedSoundex_ESTest_test4 extends RefinedSoundex_ESTest_scaffolding {

    private static final String ENCODER_EXCEPTION_CLASS_NAME = "org.apache.commons.codec.EncoderException";
    private static final int EXPECTED_DIFFERENCE_FOR_ZERO_MAPPING = 1;

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        final char[] zeroSoundexMapping = new char[6];
        final RefinedSoundex refinedSoundex = new RefinedSoundex(zeroSoundexMapping);

        final int difference = refinedSoundex.difference(
                ENCODER_EXCEPTION_CLASS_NAME,
                ENCODER_EXCEPTION_CLASS_NAME);

        assertEquals(EXPECTED_DIFFERENCE_FOR_ZERO_MAPPING, difference);
    }
}
