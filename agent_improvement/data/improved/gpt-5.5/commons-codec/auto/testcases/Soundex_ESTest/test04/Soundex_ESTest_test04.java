package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test04 extends Soundex_ESTest_scaffolding {

    private static final String CUSTOM_MAPPING = "9^n}]@7bH(,#/L";
    private static final String INPUT_TO_ENCODE = "9^n}]@7bH(,#/L";
    private static final String EXPECTED_ENCODED_VALUE = "N^#0";
    private static final int DEFAULT_SOUNDEX_MAX_LENGTH = 4;

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Soundex soundex = new Soundex(CUSTOM_MAPPING);

        Object encodedValue = soundex.encode((Object) INPUT_TO_ENCODE);

        assertEquals(DEFAULT_SOUNDEX_MAX_LENGTH, soundex.getMaxLength());
        assertEquals(EXPECTED_ENCODED_VALUE, encodedValue);
    }
}
