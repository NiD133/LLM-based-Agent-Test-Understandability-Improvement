package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test13 extends Nysiis_ESTest_scaffolding {

    private static final String INPUT_WITH_NON_LETTER_CHARACTERS = "@A9Q mhK(EVj%r";
    private static final String EXPECTED_STRICT_NYSIIS_CODE = "AGNCAF";

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        Nysiis encoder = new Nysiis();

        String encodedValue = encoder.encode(INPUT_WITH_NON_LETTER_CHARACTERS);

        assertEquals(EXPECTED_STRICT_NYSIIS_CODE, encodedValue);
    }
}
