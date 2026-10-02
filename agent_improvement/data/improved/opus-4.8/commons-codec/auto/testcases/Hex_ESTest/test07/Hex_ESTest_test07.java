package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test07 extends Hex_ESTest_scaffolding {

    /**
     * Decoding a String with an odd number of hexadecimal characters must fail,
     * because every byte requires exactly two characters. Here the input "]" has
     * a single character, so Hex#decode is expected to throw from inside the Hex
     * class (a DecoderException reporting "Odd number of characters 1.").
     */
    @Test(timeout = 4000)
    public void decodeStringWithOddNumberOfCharactersThrowsException() throws Throwable {
        Hex hex = new Hex();

        try {
            hex.decode((Object) "]");
            fail("Expected an exception because the input has an odd number of hex characters");
        } catch (Exception e) {
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
