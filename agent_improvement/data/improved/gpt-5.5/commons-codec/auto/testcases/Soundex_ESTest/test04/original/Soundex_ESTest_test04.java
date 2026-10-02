package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test04 extends Soundex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Soundex soundex0 = new Soundex("9^n}]@7bH(,#/L");
        Object object0 = soundex0.encode((Object) "9^n}]@7bH(,#/L");
        assertEquals(4, soundex0.getMaxLength());
        assertEquals("N^#0", object0);
    }
}
