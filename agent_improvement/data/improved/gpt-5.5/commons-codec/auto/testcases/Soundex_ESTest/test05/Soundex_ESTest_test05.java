package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test05 extends Soundex_ESTest_scaffolding {

    private static final String CUSTOM_MAPPING = "org.apache.commons.codec.EncoderException";
    private static final String LEFT_VALUE = ")1XSFmv!V?i#";
    private static final String RIGHT_VALUE = "org.apache.commons.codec.EncoderException";

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Soundex soundex = new Soundex(CUSTOM_MAPPING);

        int genealogyDifference = soundex.US_ENGLISH_GENEALOGY.difference(LEFT_VALUE, RIGHT_VALUE);

        assertEquals(4, soundex.getMaxLength());
        assertEquals(1, genealogyDifference);
    }
}
