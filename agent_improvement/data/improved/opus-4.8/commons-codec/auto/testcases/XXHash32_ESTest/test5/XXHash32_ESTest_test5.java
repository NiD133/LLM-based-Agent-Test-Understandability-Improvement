package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test5 extends XXHash32_ESTest_scaffolding {

    /**
     * Feeds a single byte followed by a run of zero bytes into XXHash32 and
     * verifies the resulting checksum.
     *
     * The first update adds one byte (value 109). The second update appends
     * 16 zero bytes from a zero-filled array, which is enough to fill and
     * process a 16-byte block. The expected checksum below was computed for
     * this exact sequence of 17 bytes with the default seed of 0.
     */
    @Test(timeout = 4000)
    public void test5() throws Throwable {
        XXHash32 hash = new XXHash32();

        hash.update(109);

        byte[] zeroBytes = new byte[22];
        hash.update(zeroBytes, 0, 16);

        assertEquals(1174888648L, hash.getValue());
    }
}
