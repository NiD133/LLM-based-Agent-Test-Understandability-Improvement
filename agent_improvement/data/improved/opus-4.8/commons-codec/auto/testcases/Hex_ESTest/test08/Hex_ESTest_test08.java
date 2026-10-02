package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test08 extends Hex_ESTest_scaffolding {

    /**
     * Verifies that {@link Hex#toString()} on a default-constructed Hex codec
     * returns a non-null string representation (which includes the charset name).
     */
    @Test(timeout = 4000)
    public void toStringOnDefaultHexReturnsNonNull() throws Throwable {
        Hex hex = new Hex();

        String description = hex.toString();

        assertNotNull(description);
    }
}
