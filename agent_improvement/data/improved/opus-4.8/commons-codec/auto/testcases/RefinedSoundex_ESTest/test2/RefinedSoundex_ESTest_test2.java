package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RefinedSoundex_ESTest_test2 extends RefinedSoundex_ESTest_scaffolding {

    /**
     * getMappingCode returns the null character (the char with numeric value 0)
     * for any input that is not a letter, regardless of the configured mapping.
     * Here a digit ('9') is passed, so no mapping lookup occurs and the method
     * returns 0.
     */
    @Test(timeout = 4000)
    public void mappingCodeForNonLetterIsNullChar() throws Throwable {
        char[] customMapping = new char[3];
        RefinedSoundex refinedSoundex = new RefinedSoundex(customMapping);

        char mappingCode = refinedSoundex.getMappingCode('9');

        assertEquals((char) 0, mappingCode);
    }
}
