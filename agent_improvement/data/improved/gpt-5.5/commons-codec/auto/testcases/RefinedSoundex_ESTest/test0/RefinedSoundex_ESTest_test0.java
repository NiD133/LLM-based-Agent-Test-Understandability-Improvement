package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RefinedSoundex_ESTest_test0 extends RefinedSoundex_ESTest_scaffolding {

    private static final String DEFAULT_US_ENGLISH_MAPPING = "01360240043788015936020505";
    private static final String NO_SOUNDEX_CODE_FOR_NON_LETTER_INPUT = "";

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        RefinedSoundex refinedSoundex = new RefinedSoundex();

        Object encodedMapping = refinedSoundex.US_ENGLISH.encode((Object) DEFAULT_US_ENGLISH_MAPPING);

        assertEquals(NO_SOUNDEX_CODE_FOR_NON_LETTER_INPUT, encodedMapping);
    }
}
