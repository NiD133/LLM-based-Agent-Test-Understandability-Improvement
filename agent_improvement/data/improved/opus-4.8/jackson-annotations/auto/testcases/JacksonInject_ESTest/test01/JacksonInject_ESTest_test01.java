package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test01 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that {@link JacksonInject.Value#withOptional(Boolean)} produces
     * equal-but-distinct copies, and that those copies differ from the original
     * EMPTY value whose "optional" flag is left unset.
     */
    @Test(timeout = 4000)
    public void withOptional_createsEqualButDistinctCopies() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.EMPTY;

        // A Boolean built from a non-"true" String resolves to FALSE.
        Boolean optionalFlag = new Boolean("sE]@ 6W)1`^'M<pcHc");

        // Each call returns a brand-new instance because EMPTY's optional flag is unset (null).
        JacksonInject.Value firstCopy = emptyValue.withOptional(optionalFlag);
        JacksonInject.Value secondCopy = emptyValue.withOptional(optionalFlag);

        // Same field values => equal.
        assertTrue(secondCopy.equals(firstCopy));

        // Differs from the original EMPTY value (optional unset vs. FALSE).
        assertFalse(secondCopy.equals((Object) emptyValue));

        // Equal in value but they are separate object instances.
        assertNotSame(secondCopy, firstCopy);
    }
}
