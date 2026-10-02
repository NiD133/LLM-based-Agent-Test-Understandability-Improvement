package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test03 extends Entities_ESTest_scaffolding {

    /**
     * Verifies that non-ASCII characters are escaped to their named HTML entities
     * when the output charset is ASCII. The section sign (U+00A7, §) cannot be
     * represented in ASCII, so it must be escaped to &sect;
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Configure output settings to use the ASCII charset, which forces
        // non-ASCII characters to be replaced with named HTML entities.
        Document.OutputSettings asciiOutputSettings = new Document.OutputSettings();
        asciiOutputSettings.charset("ascii");

        // The input contains the section sign character § (U+00A7) at the end.
        // All other characters are plain ASCII and should pass through unchanged.
        String inputWithSectionSign = "kp1D(Cu%~vB8caHD§";

        String escaped = Entities.escape(inputWithSectionSign, asciiOutputSettings);

        // § (U+00A7) cannot be encoded in ASCII, so it becomes the named entity &sect;
        assertEquals("kp1D(Cu%~vB8caHD&sect;", escaped);
    }
}
