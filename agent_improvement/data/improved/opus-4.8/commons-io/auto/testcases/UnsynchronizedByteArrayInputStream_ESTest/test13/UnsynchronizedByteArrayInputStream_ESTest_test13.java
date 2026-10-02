package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test13 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * Verifies that {@link UnsynchronizedByteArrayInputStream.Builder#setOffset(int)}
     * returns the same builder instance, enabling a fluent call chain.
     */
    @Test(timeout = 4000)
    public void setOffsetReturnsSameBuilderForChaining() throws Throwable {
        UnsynchronizedByteArrayInputStream.Builder builder = UnsynchronizedByteArrayInputStream.builder();

        UnsynchronizedByteArrayInputStream.Builder returnedBuilder = builder.setOffset(1092);

        assertSame(builder, returnedBuilder);
    }
}
