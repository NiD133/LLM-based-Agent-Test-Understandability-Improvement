package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test06 extends Nysiis_ESTest_scaffolding {

    // NYSIIS encodes a dotted class-name string by treating it as a word sequence;
    // dots are stripped by the cleaner, leaving "ORGAPACHECOMMONSCODECENCODEYE XCEPTION"
    // which in strict mode (max 6 chars) reduces to "ORGAPA".
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Nysiis nysiis = new Nysiis();
        Object encodedValue = nysiis.encode((Object) "org.apache.commons.codec.EncodeyException");
        assertEquals("ORGAPA", encodedValue);
        assertNotNull(encodedValue);
    }
}
