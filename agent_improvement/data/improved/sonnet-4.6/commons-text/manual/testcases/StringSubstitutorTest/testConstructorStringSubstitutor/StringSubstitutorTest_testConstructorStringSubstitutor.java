package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.text.lookup.StringLookupFactory;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the copy constructor {@code StringSubstitutor(StringSubstitutor)}
 * transfers all configuration settings from the source instance to the new instance.
 */
public class StringSubstitutorTest_testConstructorStringSubstitutor {

    @Test
    void testConstructorStringSubstitutor() {
        // Arrange: configure a source substitutor with non-default settings for every property
        final StringSubstitutor source = new StringSubstitutor();
        source.setDisableSubstitutionInValues(true);
        source.setEnableSubstitutionInVariables(true);
        source.setEnableUndefinedVariableException(true);
        source.setEscapeChar('e');
        source.setValueDelimiter('d');
        source.setVariablePrefix('p');
        source.setVariableResolver(StringLookupFactory.INSTANCE.nullStringLookup());
        source.setVariableSuffix('s');

        // Act: create a copy via the copy constructor
        final StringSubstitutor target = new StringSubstitutor(source);

        // Assert: every setting was copied faithfully
        assertTrue(target.isDisableSubstitutionInValues(),
                "disableSubstitutionInValues should be copied");
        assertTrue(target.isEnableSubstitutionInVariables(),
                "enableSubstitutionInVariables should be copied");
        assertTrue(target.isEnableUndefinedVariableException(),
                "enableUndefinedVariableException should be copied");
        assertEquals('e', target.getEscapeChar(),
                "escapeChar should be copied");

        final String valueDelimiterDesc  = target.getValueDelimiterMatcher().toString();
        final String variablePrefixDesc  = target.getVariablePrefixMatcher().toString();
        final String variableSuffixDesc  = target.getVariableSuffixMatcher().toString();

        assertTrue(valueDelimiterDesc.endsWith("['d']"),
                "valueDelimiterMatcher should match 'd': " + valueDelimiterDesc);
        assertTrue(variablePrefixDesc.endsWith("['p']"),
                "variablePrefixMatcher should match 'p': " + variablePrefixDesc);
        assertTrue(variableSuffixDesc.endsWith("['s']"),
                "variableSuffixMatcher should match 's': " + variableSuffixDesc);
    }
}
