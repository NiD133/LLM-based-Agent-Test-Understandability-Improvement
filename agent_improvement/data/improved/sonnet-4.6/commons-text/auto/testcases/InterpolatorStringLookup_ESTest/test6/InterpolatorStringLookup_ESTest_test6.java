package org.apache.commons.text.lookup;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InterpolatorStringLookup_ESTest_test6 extends InterpolatorStringLookup_ESTest_scaffolding {

    /**
     * Verifies that toString() returns a non-null string when the interpolator is constructed
     * with an empty lookup map, an XML-encoder default lookup, and default lookups disabled.
     */
    @Test(timeout = 4000)
    public void test_toString_withEmptyMapAndXmlEncoderDefaultLookup_returnsNonNull() throws Throwable {
        HashMap<String, StringLookup> emptyLookupMap = new HashMap<>();
        XmlEncoderStringLookup xmlEncoderDefaultLookup = new XmlEncoderStringLookup();
        InterpolatorStringLookup interpolator = new InterpolatorStringLookup(emptyLookupMap, xmlEncoderDefaultLookup, false);

        String result = interpolator.toString();

        assertNotNull(result);
    }
}
