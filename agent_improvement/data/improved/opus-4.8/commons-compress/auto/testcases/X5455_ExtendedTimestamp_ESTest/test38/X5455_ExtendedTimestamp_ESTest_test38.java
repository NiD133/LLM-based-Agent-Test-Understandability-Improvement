package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test38 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * The header id reported by this extra field must always be the fixed
     * "UT" tag 0x5455 (decimal 21589), regardless of which timestamps are set.
     */
    @Test(timeout = 4000)
    public void getHeaderIdReturnsFixedUtTag() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        ZipShort headerId = extendedTimestamp.getHeaderId();

        int expectedHeaderId = 0x5455; // 21589, the "UT" extra field tag
        assertEquals(expectedHeaderId, headerId.getValue());
    }
}
