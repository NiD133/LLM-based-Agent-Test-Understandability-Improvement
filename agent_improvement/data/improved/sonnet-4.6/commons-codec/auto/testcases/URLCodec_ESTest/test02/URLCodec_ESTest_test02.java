package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.BitSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test02 extends URLCodec_ESTest_scaffolding {

    /**
     * Verifies that URLCodec decodes percent-encoded sequences correctly:
     * %25 -> '%' and %2B -> '+', while URL-safe characters (* and alphanumerics) pass through unchanged.
     */
    @Test(timeout = 4000)
    public void test02_decodePercentEncodedPercentAndPlusSign() throws Throwable {
        URLCodec urlCodec = new URLCodec();

        // Input: "*aAC%25%2B" where %25 is a percent sign and %2B is a plus sign
        String encodedInput = "*aAC%25%2B";
        String decodedResult = urlCodec.decode(encodedInput);

        assertNotNull(decodedResult);
        assertEquals("*aAC%+", decodedResult);
    }
}
