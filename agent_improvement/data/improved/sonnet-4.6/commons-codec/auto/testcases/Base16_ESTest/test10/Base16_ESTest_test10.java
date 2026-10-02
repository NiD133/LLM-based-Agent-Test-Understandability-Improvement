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
public class Base16_ESTest_test10 extends Base16_ESTest_scaffolding {

    /**
     * Verifies that {@link Base16.Builder#setLowerCase(boolean)} supports method chaining
     * by returning the same builder instance rather than a new one.
     */
    @Test(timeout = 4000)
    public void test_setLowerCase_returnsSameBuilderInstance_forMethodChaining() throws Throwable {
        Base16.Builder builder = Base16.builder();

        Base16.Builder builderAfterSetLowerCase = builder.setLowerCase(true);

        assertSame("setLowerCase should return 'this' builder to support fluent chaining",
                builderAfterSetLowerCase, builder);
    }
}
