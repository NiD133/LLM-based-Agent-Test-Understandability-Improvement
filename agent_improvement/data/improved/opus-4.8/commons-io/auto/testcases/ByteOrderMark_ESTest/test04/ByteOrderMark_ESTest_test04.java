package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test04 extends ByteOrderMark_ESTest_scaffolding {

    /**
     * A BOM's own raw bytes should match itself: feeding {@code matches} the exact
     * byte array that backs the BOM must return {@code true}.
     */
    @Test(timeout = 4000)
    public void matchesReturnsTrueForOwnRawBytes() throws Throwable {
        ByteOrderMark utf32Be = ByteOrderMark.UTF_32BE;

        int[] ownRawBytes = utf32Be.getRawBytes();
        boolean matchesOwnBytes = utf32Be.matches(ownRawBytes);

        assertTrue(matchesOwnBytes);
    }
}
