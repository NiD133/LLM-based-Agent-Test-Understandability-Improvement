package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test06 extends Nysiis_ESTest_scaffolding {

    private static final String INPUT_TEXT = "org.apache.commons.codec.EncodeyException";
    private static final String EXPECTED_NYSIIS_CODE = "ORGAPA";

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Nysiis strictEncoder = new Nysiis();

        Object encodedValue = strictEncoder.encode((Object) INPUT_TEXT);

        assertEquals(EXPECTED_NYSIIS_CODE, encodedValue);
        assertNotNull(encodedValue);
    }
}
