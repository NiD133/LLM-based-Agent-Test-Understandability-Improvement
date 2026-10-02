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
     * a null OPTION element throws NullPointerException.
     *
     * The constructor delegates to EnumSet.copyOf(), which rejects null elements,
     * so a single-element array whose entry was never assigned (defaults to null)
     * must cause this failure.
     */
    @Test(timeout = 4000)
    public void test7() throws Throwable {
        // Array of length 1 whose sole element is null (never assigned)
        NumericEntityUnescaper.OPTION[] optionsWithNullElement = new NumericEntityUnescaper.OPTION[1];

        try {
            new NumericEntityUnescaper(optionsWithNullElement);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // EnumSet.copyOf() throws NullPointerException when the collection contains null
        }
    }
}
