package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test02 extends Soundex_ESTest_scaffolding {

    private static final String SHORT_CUSTOM_MAPPING = "'NY8Wa^[4";

    @Test(timeout = 4000)
    public void encodeWithShortCustomMappingRejectsUnmappedCharacter() throws Throwable {
        Soundex soundex = new Soundex(SHORT_CUSTOM_MAPPING);

        try {
            soundex.encode(SHORT_CUSTOM_MAPPING);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException exception) {
            verifyException("org.apache.commons.codec.language.Soundex", exception);
        }
    }
}
