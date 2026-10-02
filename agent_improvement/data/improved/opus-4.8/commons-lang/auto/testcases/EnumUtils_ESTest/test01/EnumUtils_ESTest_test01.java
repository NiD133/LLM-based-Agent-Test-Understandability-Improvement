package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.EnumSet;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test01 extends EnumUtils_ESTest_scaffolding {

    /**
     * Verifies that decoding an empty bit-vector array yields an empty EnumSet:
     * with no bits set, none of the enum constants should be selected.
     */
    @Test(timeout = 4000)
    public void processBitVectorsWithEmptyArrayReturnsEmptyEnumSet() throws Throwable {
        Class<Locale.FilteringMode> enumClass = Locale.FilteringMode.class;
        long[] emptyBitVectors = new long[0];

        EnumSet<Locale.FilteringMode> decodedValues =
                EnumUtils.processBitVectors(enumClass, emptyBitVectors);

        assertEquals(0, decodedValues.size());
    }
}
