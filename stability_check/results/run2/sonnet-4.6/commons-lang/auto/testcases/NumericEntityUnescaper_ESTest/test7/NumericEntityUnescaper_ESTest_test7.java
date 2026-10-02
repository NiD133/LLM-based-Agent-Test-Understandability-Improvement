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
     * Verifies that constructing a NumericEntityUnescaper with an array containing
     * a null element throws a NullPointerException, because EnumSet.copyOf
     * does not accept null values.
     */
    @Test(timeout = 4000)
    public void test_constructor_withNullOptionElement_throwsNullPointerException() throws Throwable {
        NumericEntityUnescaper.OPTION[] optionsWithNullElement = new NumericEntityUnescaper.OPTION[1];
        // optionsWithNullElement[0] is null (default array value)

        try {
            new NumericEntityUnescaper(optionsWithNullElement);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
        }
    }
}
