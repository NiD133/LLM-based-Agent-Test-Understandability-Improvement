package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test09 extends AlphabetConverter_ESTest_scaffolding {

    /** Unicode code point 1 = SOH (Start of Heading) control character. */
    private static final int CODE_POINT_SOH = 1;

    /** The encoded string that the converter maps to the SOH control character. */
    private static final String ENCODED_SOH = "qor_5~yr2yEtVdG{";

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // Map the SOH code point to an arbitrary encoded string and build a converter from it
        HashMap<Integer, String> encodingMap = new HashMap<Integer, String>();
        encodingMap.put(Integer.valueOf(CODE_POINT_SOH), ENCODED_SOH);
        assertEquals(1, encodingMap.size());

        AlphabetConverter converter = AlphabetConverter.createConverterFromMap(encodingMap);

        // Decoding the encoded string should return the original SOH character
        String decoded = converter.decode(ENCODED_SOH);
        assertEquals(String.valueOf((char) CODE_POINT_SOH), decoded);
    }
}
