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

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        Base16.Builder builder = Base16.builder();

        Base16.Builder returnedBuilder = builder.setLowerCase(false);

        assertSame("setLowerCase should return the same builder for method chaining", builder, returnedBuilder);
    }
}
