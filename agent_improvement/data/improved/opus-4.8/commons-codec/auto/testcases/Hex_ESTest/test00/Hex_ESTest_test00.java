package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test00 extends Hex_ESTest_scaffolding {

    /**
     * Encoding a null Object must fail fast: the null is treated as a null
     * byte[], so {@link Hex#encode(Object)} throws a NullPointerException from
     * within the Hex class when it tries to read the array length.
     */
    @Test(timeout = 4000)
    public void encodeNullObjectThrowsNullPointerException() throws Throwable {
        Hex hex = new Hex();

        try {
            hex.encode((Object) null);
            fail("Expected a NullPointerException when encoding a null Object");
        } catch (NullPointerException e) {
            // The exception originates inside the Hex class and carries no message.
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
