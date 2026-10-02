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

    // getMappingCode returns (char) 0 for non-letter input because the method
    // short-circuits before consulting the mapping table when the character is not a letter.
    @Test(timeout = 4000)
    public void test2_getMappingCodeReturnsNullCharForDigitInput() throws Throwable {
        char[] threeEntryCustomMapping = new char[3];
        RefinedSoundex soundexWithCustomMapping = new RefinedSoundex(threeEntryCustomMapping);

        char mappingCode = soundexWithCustomMapping.getMappingCode((char) 57); // '9' == 57

        assertEquals((char) 0, mappingCode);
    }
}
