package org.apache.commons.text.lookup;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InterpolatorStringLookup_ESTest_test1 extends InterpolatorStringLookup_ESTest_scaffolding {

    /**
     * Verifies that when a key begins with the prefix separator (':'), the part before
     * the separator is treated as the (empty) lookup prefix and the remainder as the name.
     * Here the empty prefix is registered to an {@link XmlEncoderStringLookup}, so the
     * remaining text is returned XML-encoded (the leading apostrophe becomes "&apos;").
     */
    @Test(timeout = 4000)
    public void lookupWithEmptyPrefixDelegatesToRegisteredXmlEncoder() throws Throwable {
        // Register an XML-encoder lookup under the empty prefix "".
        XmlEncoderStringLookup xmlEncoderLookup = new XmlEncoderStringLookup();
        Map<String, StringLookup> lookupsByPrefix = new HashMap<String, StringLookup>();
        lookupsByPrefix.put("", xmlEncoderLookup);

        // Build the interpolator without adding the default lookups.
        InterpolatorStringLookup interpolator =
                new InterpolatorStringLookup(lookupsByPrefix, xmlEncoderLookup, false);

        // The leading ':' splits the key into an empty prefix and the name "'Rk ^bnj|([Bw~^W".
        String result = interpolator.lookup(":'Rk ^bnj|([Bw~^W");

        // The XML encoder converts the apostrophe to "&apos;" and leaves the rest unchanged.
        assertEquals("&apos;Rk ^bnj|([Bw~^W", result);
    }
}
