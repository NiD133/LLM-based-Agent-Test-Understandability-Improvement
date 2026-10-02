package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInTakingStringBuilderWithNull {

    @Test
    void replaceInReturnsFalseForNullStringBuilder() {
        final Map<String, Object> values = new HashMap<>();
        final StrSubstitutor substitutor = new StrSubstitutor(values, "", "", 'T', "K+<'f");

        assertFalse(substitutor.replaceIn((StringBuilder) null));
    }
}
