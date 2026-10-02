package org.apache.commons.compress.compressors.zstandard;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test3 extends ZstdUtils_ESTest_scaffolding {

    /**
     * When {@code length} is at least the size of the Zstandard frame magic (4 bytes),
     * {@link ZstdUtils#matches} reads {@code signature[0..3]} to compare against the magic.
     * Here the signature array holds only a single byte (whose value 0x28 happens to match
     * the first magic byte), so the comparison loop walks past the end of the array and
     * an ArrayIndexOutOfBoundsException is thrown.
     */
    @Test(timeout = 4000)
    public void matchesThrowsWhenSignatureShorterThanRequestedLength() throws Throwable {
        byte[] signatureTooShort = new byte[] { (byte) 0x28 };
        int requestedLength = 40;

        try {
            ZstdUtils.matches(signatureTooShort, requestedLength);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            verifyException("org.apache.commons.compress.compressors.zstandard.ZstdUtils", e);
        }
    }
}
