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

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        char[] shortCustomMapping = new char[3];
        RefinedSoundex refinedSoundex = new RefinedSoundex(shortCustomMapping);

        char mappingCodeForDigit = refinedSoundex.getMappingCode('9');

        assertEquals('\u0000', mappingCodeForDigit);
    }
}
