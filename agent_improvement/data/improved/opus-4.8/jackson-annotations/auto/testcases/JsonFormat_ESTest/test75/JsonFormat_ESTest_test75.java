package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.TimeZone;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test75 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that attaching a TimeZone via withTimeZone() does not change a
     * Value's equality. JsonFormat.Value.equals() compares the stored timezone
     * string (_timezoneStr), not the resolved TimeZone object, and withTimeZone()
     * leaves that string null. The new Value therefore still equals the empty one.
     */
    @Test(timeout = 4000)
    public void withTimeZoneKeepsValueEqualToEmpty() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();
        TimeZone defaultTimeZone = TimeZone.getDefault();

        JsonFormat.Value valueWithTimeZone = emptyValue.withTimeZone(defaultTimeZone);

        assertEquals(emptyValue, valueWithTimeZone);
    }
}
