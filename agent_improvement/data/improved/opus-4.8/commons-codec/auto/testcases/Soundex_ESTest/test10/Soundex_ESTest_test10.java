package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test10 extends Soundex_ESTest_scaffolding {

    /**
     * A Soundex instance created with the default constructor should report the
     * standard Soundex code length of 4 characters.
     */
    @Test(timeout = 4000)
    public void defaultMaxLengthIsFour() throws Throwable {
        Soundex soundex = new Soundex();

        int maxLength = soundex.getMaxLength();

        assertEquals(4, maxLength);
    }
}
