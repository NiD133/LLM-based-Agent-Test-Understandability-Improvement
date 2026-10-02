package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.codec.CodecPolicy;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base16_ESTest_test11 extends Base16_ESTest_scaffolding {

    /**
     * Verifies that {@link Base16.Builder#setLowerCase(boolean)} returns the same builder
     * instance (fluent/builder pattern), allowing method chaining.
     */
    @Test(timeout = 4000)
    public void test_setLowerCase_returnsSameBuilderInstance() throws Throwable {
        Base16.Builder builder = Base16.builder();

        // setLowerCase should return the same builder to support method chaining
        Base16.Builder builderAfterSetLowerCase = builder.setLowerCase(false);

        assertSame(builder, builderAfterSetLowerCase);
    }
}
