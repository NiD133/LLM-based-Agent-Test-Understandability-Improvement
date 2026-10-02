package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test09 extends Nysiis_ESTest_scaffolding {

    private static final String MIXED_CASE_INPUT_WITH_SYMBOLS = "dIpYze0FuZfr/K1EPh%";
    private static final String EXPECTED_STRICT_NYSIIS_CODE = "DAPYSA";

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Nysiis strictEncoder = new Nysiis();

        String encodedValue = strictEncoder.nysiis(MIXED_CASE_INPUT_WITH_SYMBOLS);

        assertEquals(EXPECTED_STRICT_NYSIIS_CODE, encodedValue);
    }
}
