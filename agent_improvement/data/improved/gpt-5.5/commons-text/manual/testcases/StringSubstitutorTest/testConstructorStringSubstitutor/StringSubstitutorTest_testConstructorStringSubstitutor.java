package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.text.lookup.StringLookupFactory;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testConstructorStringSubstitutor {

    @Test
    void testConstructorStringSubstitutor() {
        final StringSubstitutor source = new StringSubstitutor();
        source.setDisableSubstitutionInValues(true);
        source.setEnableSubstitutionInVariables(true);
        source.setEnableUndefinedVariableException(true);
        source.setEscapeChar('e');
        source.setValueDelimiter('d');
        source.setVariablePrefix('p');
        source.setVariableResolver(StringLookupFactory.INSTANCE.nullStringLookup());
        source.setVariableSuffix('s');

        final StringSubstitutor target = new StringSubstitutor(source);

        assertTrue(target.isDisableSubstitutionInValues());
        assertTrue(target.isEnableSubstitutionInVariables());
        assertTrue(target.isEnableUndefinedVariableException());
        assertEquals('e', target.getEscapeChar());
        assertTrue(target.getValueDelimiterMatcher().toString().endsWith("['d']"), target.getValueDelimiterMatcher().toString());
        assertTrue(target.getVariablePrefixMatcher().toString().endsWith("['p']"), target.getValueDelimiterMatcher().toString());
        assertTrue(target.getVariableSuffixMatcher().toString().endsWith("['s']"), target.getValueDelimiterMatcher().toString());
    }
}
