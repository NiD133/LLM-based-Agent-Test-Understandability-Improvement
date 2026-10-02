package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test08 extends Nysiis_ESTest_scaffolding {

    private static final String MIXED_SYMBOL_INPUT = "|5tl0=0cuh:\u007F63pEY";
    private static final String EXPECTED_STRICT_NYSIIS_CODE = "TLCAPY";

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Nysiis encoder = new Nysiis();

        String encoded = encoder.nysiis(MIXED_SYMBOL_INPUT);

        assertTrue(encoder.isStrict());
        assertEquals(EXPECTED_STRICT_NYSIIS_CODE, encoded);
    }
}
