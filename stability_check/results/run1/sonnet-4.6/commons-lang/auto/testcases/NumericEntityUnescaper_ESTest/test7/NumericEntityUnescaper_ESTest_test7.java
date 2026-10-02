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
     * Passing an array whose sole element is null should raise NullPointerException
     * because EnumSet.copyOf rejects null entries.
     */
    @Test(timeout = 4000)
    public void test_constructorWithNullOptionThrowsNullPointerException() throws Throwable {
        NumericEntityUnescaper.OPTION[] optionsWithNullElement = new NumericEntityUnescaper.OPTION[1];
        NumericEntityUnescaper unescaper = null;
        try {
            unescaper = new NumericEntityUnescaper(optionsWithNullElement);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
        }
    }
}
