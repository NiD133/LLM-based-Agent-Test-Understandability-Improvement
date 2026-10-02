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
public class JacksonInject_ESTest_test06 extends JacksonInject_ESTest_scaffolding {

    // When useInput is null, willUseInput() should fall back to the supplied default value;
    // and a non-null id means hasId() should return true.
    @Test(timeout = 4000)
    public void test_willUseInput_returnsFalseDefaultWhenUseInputIsNull_andHasIdIsTrueForNonNullId() throws Throwable {
        Object injectionId = new Object();
        Boolean optional = Boolean.valueOf(true);
        // Construct a Value with a non-null id, useInput=null, and optional=true
        JacksonInject.Value injectValue = JacksonInject.Value.construct(injectionId, (Boolean) null, optional);

        // useInput is null, so willUseInput should return the supplied default (false)
        boolean usesInput = injectValue.willUseInput(false);
        assertFalse(usesInput);

        // injectionId is non-null, so hasId() should be true
        assertTrue(injectValue.hasId());
    }
}
