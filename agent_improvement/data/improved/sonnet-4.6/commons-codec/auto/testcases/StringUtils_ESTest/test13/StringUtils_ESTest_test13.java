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
public class StringUtils_ESTest_test13 extends StringUtils_ESTest_scaffolding {

    /**
     * An empty string encoded in UTF-16 produces zero content bytes.
     * UTF-16 may prepend a BOM when encoding non-empty strings, but for an
     * empty input there are no characters to encode, so the result is empty.
     */
    @Test(timeout = 4000)
    public void test_getBytesUtf16_emptyString_returnsEmptyByteArray() throws Throwable {
        byte[] utf16Bytes = StringUtils.getBytesUtf16("");

        assertEquals("Encoding an empty string with UTF-16 should yield zero bytes", 0, utf16Bytes.length);
    }
}
