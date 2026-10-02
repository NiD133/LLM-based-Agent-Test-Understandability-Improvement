package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test52 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test52() throws Throwable {
        // Build an array where WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED occupies both slots
        JsonFormat.Feature unwrapped = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED;
        JsonFormat.Feature[] featuresWithUnwrapped = new JsonFormat.Feature[] { unwrapped, unwrapped };

        // Construct Features using the same array for both enabled and disabled,
        // so WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED has its bit set in both masks
        JsonFormat.Features conflictingFeatures =
                JsonFormat.Features.construct(featuresWithUnwrapped, featuresWithUnwrapped);

        // without() clears the enabled bit for WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED,
        // changing the internal state and returning a new distinct instance
        JsonFormat.Features featuresAfterWithout =
                conflictingFeatures.without(featuresWithUnwrapped);

        assertNotSame(featuresAfterWithout, conflictingFeatures);
        assertFalse(featuresAfterWithout.equals((Object) conflictingFeatures));
    }
}
