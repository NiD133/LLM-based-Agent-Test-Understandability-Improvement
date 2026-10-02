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
public class JacksonInject_ESTest_test01 extends JacksonInject_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        JacksonInject.Value emptyInjectValue = JacksonInject.Value.EMPTY;
        Boolean optionalFlag = new Boolean("sE]@ 6W)1`^'M<pcHc");

        JacksonInject.Value firstValueWithOptionalFlag = emptyInjectValue.withOptional(optionalFlag);
        JacksonInject.Value secondValueWithOptionalFlag = emptyInjectValue.withOptional(optionalFlag);

        boolean valuesWithSameOptionalFlagAreEqual =
                secondValueWithOptionalFlag.equals(firstValueWithOptionalFlag);

        assertTrue(valuesWithSameOptionalFlagAreEqual);
        assertFalse(secondValueWithOptionalFlag.equals((Object) emptyInjectValue));
        assertNotSame(secondValueWithOptionalFlag, firstValueWithOptionalFlag);
    }
}
