package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test06 extends Soundex_ESTest_scaffolding {

    /**
     * A Soundex built with a custom mapping string should still report the
     * default maximum code length of 4, since {@code maxLength} is a fixed
     * field that is independent of the mapping passed to the constructor.
     */
    @Test(timeout = 4000)
    public void customMappingKeepsDefaultMaxLengthOfFour() throws Throwable {
        String genealogyMapping = "-123-12--22455-12623-1-2-2";
        Soundex soundex = new Soundex(genealogyMapping);

        int maxLength = soundex.getMaxLength();

        assertEquals(4, maxLength);
    }
}
