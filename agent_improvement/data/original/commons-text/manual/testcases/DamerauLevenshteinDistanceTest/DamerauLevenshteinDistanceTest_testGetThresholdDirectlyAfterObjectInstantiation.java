package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DamerauLevenshteinDistanceTest_testGetThresholdDirectlyAfterObjectInstantiation {

    private static DamerauLevenshteinDistance defaultInstance;

    @BeforeAll
    static void createInstance() {
        defaultInstance = new DamerauLevenshteinDistance();
    }

    static Stream<Arguments> limitedDamerauLevenshteinDistanceTestCases() {
        return Stream.of(Arguments.of("", "test", 10, 4), Arguments.of("test", "", 10, 4), Arguments.of("", "test", 2, -1), Arguments.of("test", "", 2, -1), Arguments.of("testing long string", "testing", 2, -1), Arguments.of("kitten", "sitting", 1, -1), Arguments.of("saturday", "sunday", 3, 3), Arguments.of("hello", "world", 6, 4), Arguments.of("algorithm", "logarithm", 1, -1), Arguments.of("computer", "comptuer", 1, 1), Arguments.of("receive", "recieve", 3, 1), Arguments.of("programming", "porgramming", 0, -1), Arguments.of("test", "tset", 1, 1), Arguments.of("example", "exmaple", 3, 1), Arguments.of("transform", "transfrom", 0, -1), Arguments.of("information", "infromation", 1, 1), Arguments.of("development", "developemnt", 3, 1), Arguments.of("password", "passwrod", 0, -1), Arguments.of("separate", "seperate", 1, 1), Arguments.of("definitely", "definately", 3, 1), Arguments.of("occurrence", "occurence", 0, -1), Arguments.of("necessary", "neccessary", 1, 1), Arguments.of("restaurant", "restaraunt", 4, 2), Arguments.of("beginning", "begining", 0, -1), Arguments.of("government", "goverment", 1, 1), Arguments.of("abcdefghijklmnop", "ponmlkjihgfedcba", 17, 15), Arguments.of("AAAAAAAAAA", "BBBBBBBBBB", 5, -1), Arguments.of("abababababab", "babababababa", 2, 2), Arguments.of("supercalifragilisticexpialidocious", "supercalifragilisticexpialidocous", 3, 1), Arguments.of("pneumonoultramicroscopicsilicovolcanoconiosiss", "pneumonoultramicroscopicsilicovolcanoconiosis", 0, -1), Arguments.of("abcdefg", "gfedcba", 6, 6), Arguments.of("xyxyxyxyxy", "yxyxyxyxyx", 4, 2), Arguments.of("aaaaabbbbbccccc", "cccccbbbbbaaaaa", 5, -1), Arguments.of("thequickbrownfoxjumpsoverthelazydog", "thequickbrownfoxjumpsovrethelazydog", 1, 1), Arguments.of("antidisestablishmentarianism", "antidisestablishmentarianisn", 3, 1));
    }

    static Stream<Arguments> limitedDamerauLevenshteinDistanceTestCases_SimilarityInput() {
        return SimilarityInputTest.similarityInputs().flatMap(cls -> limitedDamerauLevenshteinDistanceTestCases().map(arguments -> {
            final Object[] values = Arrays.copyOf(arguments.get(), arguments.get().length + 1);
            values[values.length - 1] = cls;
            return Arguments.of(values);
        }));
    }

    static Stream<Arguments> unlimitedDamerauLevenshteinDistanceTestCases() {
        return Stream.of(Arguments.of("", "test", 4), Arguments.of("test", "", 4), Arguments.of("kitten", "sitting", 3), Arguments.of("saturday", "sunday", 3), Arguments.of("hello", "world", 4), Arguments.of("algorithm", "logarithm", 3), Arguments.of("computer", "comptuer", 1), Arguments.of("receive", "recieve", 1), Arguments.of("programming", "porgramming", 1), Arguments.of("test", "tset", 1), Arguments.of("example", "exmaple", 1), Arguments.of("transform", "transfrom", 1), Arguments.of("information", "infromation", 1), Arguments.of("development", "developemnt", 1), Arguments.of("password", "passwrod", 1), Arguments.of("separate", "seperate", 1), Arguments.of("definitely", "definately", 1), Arguments.of("occurrence", "occurence", 1), Arguments.of("necessary", "neccessary", 1), Arguments.of("restaurant", "restaraunt", 2), Arguments.of("beginning", "begining", 1), Arguments.of("government", "goverment", 1), Arguments.of("abcdefghijklmnop", "ponmlkjihgfedcba", 15), Arguments.of("AAAAAAAAAA", "BBBBBBBBBB", 10), Arguments.of("abababababab", "babababababa", 2), Arguments.of("supercalifragilisticexpialidocious", "supercalifragilisticexpialidocous", 1), Arguments.of("pneumonoultramicroscopicsilicovolcanoconiosiss", "pneumonoultramicroscopicsilicovolcanoconiosis", 1), Arguments.of("abcdefg", "gfedcba", 6), Arguments.of("xyxyxyxyxy", "yxyxyxyxyx", 2), Arguments.of("aaaaabbbbbccccc", "cccccbbbbbaaaaa", 10), Arguments.of("thequickbrownfoxjumpsoverthelazydog", "thequickbrownfoxjumpsovrethelazydog", 1), Arguments.of("antidisestablishmentarianism", "antidisestablishmentarianisn", 1));
    }

    static Stream<Arguments> unlimitedDamerauLevenshteinDistanceTestCases_SimilarityInput() {
        return SimilarityInputTest.similarityInputs().flatMap(cls -> unlimitedDamerauLevenshteinDistanceTestCases().map(arguments -> {
            final Object[] values = Arrays.copyOf(arguments.get(), arguments.get().length + 1);
            values[values.length - 1] = cls;
            return Arguments.of(values);
        }));
    }

    @Test
    void testGetThresholdDirectlyAfterObjectInstantiation() {
        assertNull(defaultInstance.getThreshold());
    }
}
