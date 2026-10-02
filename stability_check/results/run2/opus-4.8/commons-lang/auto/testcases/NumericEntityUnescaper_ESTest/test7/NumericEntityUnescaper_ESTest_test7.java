package org.apache.commons.lang3.text.translate;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NumericEntityUnescaper_ESTest_test7 extends NumericEntityUnescaper_ESTest_scaffolding {

    /**
     * The constructor copies the supplied options into an EnumSet. When the
     * options array contains a null element, EnumSet.copyOf rejects it and
     * throws a NullPointerException.
     */
    @Test(timeout = 4000)
    public void constructorWithNullOptionThrowsNullPointerException() throws Throwable {
        NumericEntityUnescaper.OPTION[] optionsContainingNull = new NumericEntityUnescaper.OPTION[1];

        try {
            new NumericEntityUnescaper(optionsContainingNull);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The exception carries no message.
            assertNull(e.getMessage());
        }
    }
}
