package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test22 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * setFlags() decodes the flags byte into the three "time present" bits, and
     * equals() compares two fields by their (lowest three) flag bits.
     *
     * <p>Here the flags byte is 0xB4 (binary 1011_0100). The only relevant bit
     * that is set among the lowest three is bit 2 (CREATE_TIME_BIT, value 4),
     * so the create-time bit must read back as present.</p>
     *
     * <p>A second, freshly constructed field has all flags cleared, so the two
     * fields must not be considered equal.</p>
     */
    @Test(timeout = 4000)
    public void test22() throws Throwable {
        X5455_ExtendedTimestamp fieldWithCreateTimeFlag = new X5455_ExtendedTimestamp();
        fieldWithCreateTimeFlag.setFlags((byte) 0xB4);

        X5455_ExtendedTimestamp defaultField = new X5455_ExtendedTimestamp();

        boolean fieldsAreEqual = fieldWithCreateTimeFlag.equals(defaultField);

        assertTrue(fieldWithCreateTimeFlag.isBit2_createTimePresent());
        assertFalse(fieldsAreEqual);
    }
}
