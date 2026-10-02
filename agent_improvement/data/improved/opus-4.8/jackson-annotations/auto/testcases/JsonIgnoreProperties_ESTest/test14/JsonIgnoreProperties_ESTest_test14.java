package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test14 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Building a Value from a null annotation yields the EMPTY default instance.
     * After JDK deserialization (readResolve), that instance should still report
     * its empty defaults, in particular allowGetters == false.
     */
    @Test(timeout = 4000)
    public void readResolveOfValueFromNullAnnotationKeepsAllowGettersFalse() throws Throwable {
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);

        JsonIgnoreProperties.Value resolvedValue = (JsonIgnoreProperties.Value) emptyValue.readResolve();

        assertFalse(resolvedValue.getAllowGetters());
    }
}
