package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test15 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * Verifies that {@link UnsynchronizedByteArrayInputStream.Builder#setLength(int)}
     * returns the same builder instance, enabling a fluent call chain.
     */
    @Test(timeout = 4000)
    public void setLengthReturnsSameBuilderForChaining() throws Throwable {
        UnsynchronizedByteArrayInputStream.Builder builder = new UnsynchronizedByteArrayInputStream.Builder();

        UnsynchronizedByteArrayInputStream.Builder returnedBuilder = builder.setLength(653);

        assertSame(builder, returnedBuilder);
    }
}
