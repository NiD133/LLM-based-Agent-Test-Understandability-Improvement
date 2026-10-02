package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test11 extends Soundex_ESTest_scaffolding {

    /**
     * The custom mapping and the specialCaseHW flag passed to the constructor do
     * not affect maxLength, which always defaults to the standard Soundex value of 4.
     */
    @Test(timeout = 4000)
    public void maxLengthDefaultsToFourRegardlessOfCustomMapping() throws Throwable {
        Soundex soundexWithCustomMapping = new Soundex("#9fO$#-_", false);

        assertEquals(4, soundexWithCustomMapping.getMaxLength());
    }
}
