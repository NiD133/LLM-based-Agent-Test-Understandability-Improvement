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
     * Constructing the unescaper with an options array that contains a null
     * element must fail: the constructor copies the options into an EnumSet,
     * which rejects null and throws a NullPointerException.
     */
    @Test(timeout = 4000)
    public void constructorWithNullOptionThrowsNullPointerException() throws Throwable {
        NumericEntityUnescaper.OPTION[] optionsWithNullElement = new NumericEntityUnescaper.OPTION[1];

        try {
            new NumericEntityUnescaper(optionsWithNullElement);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // Expected: the null option cannot be added to the internal EnumSet.
            // The exception carries no message (getMessage() returns null).
        }
    }
}
