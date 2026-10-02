package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Soundex_ESTest_test03 extends Soundex_ESTest_scaffolding {

    /**
     * Soundex.encode(Object) only accepts String arguments. Passing a non-String
     * object (here, the Soundex instance itself) must raise an exception from
     * within the Soundex class.
     */
    @Test(timeout = 4000)
    public void encodeRejectsNonStringObject() throws Throwable {
        Soundex soundex = Soundex.US_ENGLISH_SIMPLIFIED;
        Object nonStringArgument = soundex;

        try {
            soundex.encode(nonStringArgument);
            fail("Expected an exception because the argument is not a String");
        } catch (Exception e) {
            // "Parameter supplied to Soundex encode is not of type java.lang.String"
            verifyException("org.apache.commons.codec.language.Soundex", e);
        }
    }
}
