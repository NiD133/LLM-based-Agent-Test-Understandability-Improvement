package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test04 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that a JacksonInject.Value constructed with a non-null id:
     * - is not equal to a Boolean object (different type check in equals())
     * - reports hasId() as true since the id is non-null
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Arrange: construct a Value with a non-null injection id and useInput/optional both set to true
        Object injectId = new Object();
        Boolean trueFlag = Boolean.valueOf(true);
        JacksonInject.Value injectValue = JacksonInject.Value.construct(injectId, trueFlag, trueFlag);

        // Act: compare the Value against a Boolean (a different, incompatible type)
        boolean equalsBoolean = injectValue.equals(trueFlag);

        // Assert: equals() returns false for an object of a different class,
        //         and hasId() is true since injectId is non-null
        assertFalse(equalsBoolean);
        assertTrue(injectValue.hasId());
    }
}
