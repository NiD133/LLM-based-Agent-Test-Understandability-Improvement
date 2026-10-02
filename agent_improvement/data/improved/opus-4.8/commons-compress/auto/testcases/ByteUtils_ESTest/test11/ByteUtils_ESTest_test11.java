package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteUtils_ESTest_test11 extends ByteUtils_ESTest_scaffolding {

    /**
     * A long holds at most eight bytes, so {@link ByteUtils#fromLittleEndian(byte[])}
     * must reject any array longer than eight bytes by throwing an
     * IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void fromLittleEndianRejectsArrayLongerThanEightBytes() throws Throwable {
        byte[] eighteenBytes = new byte[18];

        try {
            ByteUtils.fromLittleEndian(eighteenBytes);
            fail("Expected IllegalArgumentException: array exceeds eight bytes");
        } catch (IllegalArgumentException e) {
            // Message: "Can't read more than eight bytes into a long value"
            verifyException("org.apache.commons.compress.utils.ByteUtils", e);
        }
    }
}
