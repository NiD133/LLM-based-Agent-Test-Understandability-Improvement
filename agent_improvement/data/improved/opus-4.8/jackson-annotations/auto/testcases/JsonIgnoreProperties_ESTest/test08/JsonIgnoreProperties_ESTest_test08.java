package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test08 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that {@code withoutMerge()} returns a new Value that differs from the
     * original only in the {@code merge} flag (flipped from true to false), and that
     * this single difference is enough to make the two Values unequal.
     */
    @Test(timeout = 4000)
    public void withoutMergeFlipsMergeFlagAndBreaksEquality() throws Throwable {
        // Build a Value with no ignored properties and every flag false except merge=true.
        // construct(ignored, ignoreUnknown, allowGetters, allowSetters, merge)
        LinkedHashSet<String> noIgnoredProperties = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value mergingValue =
                JsonIgnoreProperties.Value.construct(noIgnoredProperties, false, false, false, true);

        // Derive a copy that only turns merging off.
        JsonIgnoreProperties.Value nonMergingValue = mergingValue.withoutMerge();

        // The original still ignores no unknown properties.
        assertFalse(mergingValue.getIgnoreUnknown());

        // The derived copy carries over every flag unchanged...
        assertFalse(nonMergingValue.getIgnoreUnknown());
        assertFalse(nonMergingValue.getAllowSetters());
        assertFalse(nonMergingValue.getAllowGetters());
        // ...except merge, which is now off.
        assertFalse(nonMergingValue.getMerge());

        // The differing merge flag makes the two Values unequal.
        assertFalse(mergingValue.equals(nonMergingValue));
    }
}
