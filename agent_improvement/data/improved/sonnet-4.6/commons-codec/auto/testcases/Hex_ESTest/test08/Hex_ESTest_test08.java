package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test08 extends Hex_ESTest_scaffolding {

    /**
     * Verifies that {@link Hex#toString()} returns a non-null string when using
     * the default charset (UTF-8). The string representation includes the charset
     * name in the format "[charsetName=UTF-8]".
     */
    @Test(timeout = 4000)
    public void testToStringReturnsNonNullForDefaultCharset() throws Throwable {
        Hex hexWithDefaultCharset = new Hex();
        String hexStringRepresentation = hexWithDefaultCharset.toString();
        assertNotNull(hexStringRepresentation);
    }
}
