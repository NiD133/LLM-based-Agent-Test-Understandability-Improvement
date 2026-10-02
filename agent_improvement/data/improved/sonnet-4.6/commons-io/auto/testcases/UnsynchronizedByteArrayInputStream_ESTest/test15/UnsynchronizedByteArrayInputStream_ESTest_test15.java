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

    // Verifies that Builder.setLength() returns the same builder instance,
    // enabling fluent method chaining.
    @Test(timeout = 4000)
    public void test_setLength_returnsSameBuilderInstance() throws Throwable {
        UnsynchronizedByteArrayInputStream.Builder builder = new UnsynchronizedByteArrayInputStream.Builder();
        UnsynchronizedByteArrayInputStream.Builder builderAfterSetLength = builder.setLength(653);
        assertSame(builderAfterSetLength, builder);
    }
}
