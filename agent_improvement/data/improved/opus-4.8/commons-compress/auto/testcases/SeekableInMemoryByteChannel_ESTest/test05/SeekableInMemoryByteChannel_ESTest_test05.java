package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SeekableInMemoryByteChannel_ESTest_test05 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    /**
     * Truncating to a negative size must be rejected with an
     * IllegalArgumentException, as required by the SeekableByteChannel contract.
     */
    @Test(timeout = 4000)
    public void truncateToNegativeSizeThrowsIllegalArgumentException() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();

        final long negativeSize = -794L;
        try {
            channel.truncate(negativeSize);
            fail("Expected an IllegalArgumentException for a negative truncation size");
        } catch (IllegalArgumentException expected) {
            // Message produced by the channel: "New size is negative: -794"
            verifyException("org.apache.commons.compress.utils.SeekableInMemoryByteChannel", expected);
        }
    }
}
