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
public class JsonTypeInfo_ESTest_test01 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        JsonTypeInfo.Value emptyTypeInfo = JsonTypeInfo.Value.EMPTY;
        Boolean requireTypeIdForSubtypes = Boolean.valueOf(false);

        JsonTypeInfo.Value typeInfoWithoutRequiredSubtypeIds =
                emptyTypeInfo.withRequireTypeIdForSubtypes(requireTypeIdForSubtypes);
        boolean valuesAreEqual = typeInfoWithoutRequiredSubtypeIds.equals(emptyTypeInfo);

        assertFalse("Changing requireTypeIdForSubtypes should keep ids hidden",
                typeInfoWithoutRequiredSubtypeIds.getIdVisible());
        assertFalse("The original empty value should not equal the modified value",
                emptyTypeInfo.equals((Object) typeInfoWithoutRequiredSubtypeIds));
        assertTrue("The default setting should still write type ids for default implementations",
                typeInfoWithoutRequiredSubtypeIds.shouldWriteTypeIdForDefaultImpl());
        assertFalse("The modified value should not equal the original empty value",
                valuesAreEqual);
    }
}
