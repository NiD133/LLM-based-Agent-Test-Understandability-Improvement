package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtils_ESTest_test16 extends StringUtils_ESTest_scaffolding {

    /**
     * Verifies that the (deprecated) public no-arg constructor of {@link StringUtils}
     * can be invoked successfully and yields a non-null instance.
     */
    @Test(timeout = 4000)
    public void testDefaultConstructorCreatesInstance() throws Throwable {
        StringUtils stringUtils = new StringUtils();

        assertNotNull(stringUtils);
    }
}
