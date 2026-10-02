package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test09 extends EnumUtils_ESTest_scaffolding {

    /**
     * Generating a bit vector for every constant of an enum should set one bit
     * per ordinal. {@link Locale.FilteringMode} declares 5 constants (ordinals
     * 0 through 4), so the resulting vector is binary 11111, i.e. 31.
     */
    @Test(timeout = 4000)
    public void generateBitVectorForAllConstantsSetsOneBitPerOrdinal() throws Throwable {
        Class<Locale.FilteringMode> filteringModeEnum = Locale.FilteringMode.class;

        List<Locale.FilteringMode> allConstants = EnumUtils.getEnumList(filteringModeEnum);
        long bitVector = EnumUtils.generateBitVector(filteringModeEnum, allConstants);

        assertEquals(31L, bitVector);
    }
}
