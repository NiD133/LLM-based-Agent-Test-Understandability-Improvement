package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test33 extends Validate_ESTest_scaffolding {

    /**
     * An empty array contains no null elements, so {@link Validate#noNullElements(Object[])}
     * should accept it without throwing a ValidationException.
     */
    @Test(timeout = 4000)
    public void noNullElementsAcceptsEmptyArray() throws Throwable {
        Object[] emptyArray = new Object[0];

        Validate.noNullElements(emptyArray);

        assertEquals(0, emptyArray.length);
    }
}
