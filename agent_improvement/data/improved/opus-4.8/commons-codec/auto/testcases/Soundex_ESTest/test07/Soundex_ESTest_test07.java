package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test07 extends Soundex_ESTest_scaffolding {

    /**
     * A Soundex created with a custom mapping should still report the standard,
     * constant maximum code length of 4, regardless of the mapping supplied.
     */
    @Test(timeout = 4000)
    public void customMappingStillReportsDefaultMaxLengthOfFour() throws Throwable {
        char[] customMapping = { '-' };
        Soundex soundex = new Soundex(customMapping);

        int maxLength = soundex.getMaxLength();

        assertEquals(4, maxLength);
    }
}
