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
     * The constructor receives a non-empty OPTION[] whose only element is null.
     * Because the array length is greater than zero, the constructor tries to
     * build an EnumSet from it via EnumSet.copyOf(Arrays.asList(options)), and
     * the null element causes a NullPointerException.
     */
    @Test(timeout = 4000)
    public void constructorRejectsNullOptionElement() throws Throwable {
        NumericEntityUnescaper.OPTION[] optionsWithNullElement = new NumericEntityUnescaper.OPTION[1];

        try {
            new NumericEntityUnescaper(optionsWithNullElement);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // No message is expected; getMessage() returns null.
        }
    }
}
