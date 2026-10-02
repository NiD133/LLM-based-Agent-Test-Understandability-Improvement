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
public class JsonTypeInfo_ESTest_test24 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that calling withPropertyName(null) on Value.EMPTY returns the same
     * instance. Value.EMPTY already has a null property name, so withPropertyName
     * detects the no-op (null == null) and returns 'this' unchanged.
     */
    @Test(timeout = 4000)
    public void test24() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        // EMPTY._propertyName is null; passing null should be a no-op and return the same instance
        JsonTypeInfo.Value resultAfterSettingNullPropertyName = emptyValue.withPropertyName((String) null);

        assertSame(resultAfterSettingNullPropertyName, emptyValue);
    }
}
