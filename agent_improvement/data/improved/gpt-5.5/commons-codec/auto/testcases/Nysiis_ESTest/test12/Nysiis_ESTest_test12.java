package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test12 extends Nysiis_ESTest_scaffolding {

    private static final String INPUT_WITH_IGNORED_NON_LETTERS = "tl1[CoH5>Aeu)UA;J.";
    private static final String STRICT_NYSIIS_CODE = "TLCAHA";

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        Nysiis strictEncoder = new Nysiis();

        String encodedValue = strictEncoder.nysiis(INPUT_WITH_IGNORED_NON_LETTERS);

        assertEquals(STRICT_NYSIIS_CODE, encodedValue);
        assertNotNull(encodedValue);
    }
}
