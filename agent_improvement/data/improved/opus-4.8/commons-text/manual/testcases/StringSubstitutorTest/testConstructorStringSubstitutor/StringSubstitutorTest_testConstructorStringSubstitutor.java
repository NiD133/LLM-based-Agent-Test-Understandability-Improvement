package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.text.lookup.StringLookupFactory;
import org.junit.jupiter.api.Test;

/**
 * Tests the copy constructor {@link StringSubstitutor#StringSubstitutor(StringSubstitutor)},
 * verifying that every configurable property is copied from the source instance to the new one.
 */
public class StringSubstitutorTest_testConstructorStringSubstitutor {

    @Test
    void testConstructorStringSubstitutor() {
        // Configure a source substitutor with a non-default value for every property.
        final StringSubstitutor source = new StringSubstitutor();
        source.setDisableSubstitutionInValues(true);
        source.setEnableSubstitutionInVariables(true);
        source.setEnableUndefinedVariableException(true);
        source.setEscapeChar('e');
        source.setValueDelimiter('d');
        source.setVariablePrefix('p');
        source.setVariableResolver(StringLookupFactory.INSTANCE.nullStringLookup());
        source.setVariableSuffix('s');

        // Copy the source via the copy constructor.
        final StringSubstitutor target = new StringSubstitutor(source);

        // The copy must carry over all of the source's settings.
        assertTrue(target.isDisableSubstitutionInValues());
        assertTrue(target.isEnableSubstitutionInVariables());
        assertTrue(target.isEnableUndefinedVariableException());
        assertEquals('e', target.getEscapeChar());
        assertTrue(target.getValueDelimiterMatcher().toString().endsWith("['d']"),
                target.getValueDelimiterMatcher().toString());
        assertTrue(target.getVariablePrefixMatcher().toString().endsWith("['p']"),
                target.getVariablePrefixMatcher().toString());
        assertTrue(target.getVariableSuffixMatcher().toString().endsWith("['s']"),
                target.getVariableSuffixMatcher().toString());
    }
}
