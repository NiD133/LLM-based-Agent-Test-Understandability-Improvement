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
     * Verifies that PercentCodec can be constructed with a null always-encode-chars array.
     * When alwaysEncodeChars is null, the codec skips custom char registration and only
     * marks the '%' escape character for encoding. The plusForSpace flag is set to true,
     * meaning spaces will be encoded as '+' instead of '%20'.
     */
    @Test(timeout = 4000)
    public void test01_constructorAcceptsNullAlwaysEncodeCharsWithPlusForSpaceEnabled() throws Throwable {
        PercentCodec percentCodec = new PercentCodec((byte[]) null, true);
        assertNotNull("PercentCodec should be created successfully with null alwaysEncodeChars", percentCodec);
    }
}
