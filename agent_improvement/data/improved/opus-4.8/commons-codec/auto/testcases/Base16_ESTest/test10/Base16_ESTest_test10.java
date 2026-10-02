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
     * Verifies that {@link Base16.Builder#setLowerCase(boolean)} follows the
     * fluent builder contract by returning the same builder instance it was
     * called on, allowing further calls to be chained.
     */
    @Test(timeout = 4000)
    public void setLowerCaseReturnsSameBuilderForChaining() throws Throwable {
        Base16.Builder builder = Base16.builder();

        Base16.Builder returnedBuilder = builder.setLowerCase(true);

        assertSame(builder, returnedBuilder);
    }
}
