package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test13 extends Base58_ESTest_scaffolding {

    /**
     * Verifies that {@link Base58#builder()} returns a non-null {@link Base58.Builder} instance,
     * confirming the factory method is properly initialised.
     */
    @Test(timeout = 4000)
    public void testBuilderFactoryReturnsNonNullInstance() throws Throwable {
        Base58.Builder builder = Base58.builder();
        assertNotNull(builder);
    }
}
