package org.apache.commons.text;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test49 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that enabling the "disable substitution in values" flag via
     * {@link StringSubstitutor#setDisableSubstitutionInValues(boolean)} is
     * reflected by {@link StringSubstitutor#isDisableSubstitutionInValues()}.
     */
    @Test(timeout = 4000)
    public void settingDisableSubstitutionInValuesToTrueIsReflectedByGetter() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();

        substitutor.setDisableSubstitutionInValues(true);

        assertTrue(substitutor.isDisableSubstitutionInValues());
    }
}
