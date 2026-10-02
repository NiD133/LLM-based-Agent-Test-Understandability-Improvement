package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test01 extends PercentCodec_ESTest_scaffolding {

    /**
     * The two-argument constructor should accept a null "always encode" array.
     * Passing null means no extra US-ASCII characters are forced to be encoded
     * (only the '%' escape character is registered internally), and the
     * plusForSpace flag is enabled. Construction must complete without throwing.
     */
    @Test(timeout = 4000)
    public void constructorAcceptsNullAlwaysEncodeCharsWithPlusForSpaceEnabled() throws Throwable {
        byte[] noAlwaysEncodeChars = null;
        boolean plusForSpace = true;

        PercentCodec percentCodec = new PercentCodec(noAlwaysEncodeChars, plusForSpace);

        assertNotNull(percentCodec);
    }
}
