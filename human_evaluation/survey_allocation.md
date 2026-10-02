# Human study: what each survey contains

Recovered from the 15 shipped Qualtrics surveys (`FinalSurveys/Agent_<N>_Understandability.qsf`) by
`identify_stimuli.py`: the same data as `analysis/design_map.csv` and `analysis/tables/stimuli.csv`, laid out per survey.
A *pair* is one original test and its agent-improved version. In Part 1 the two versions are separate Likert
items (screen = position among the 13 Part-1 screens, screen 10 is the attention check; I = improved, O = original).
In Part 2 they are shown side by side as Test A and Test B (the attention check is the third of seven screens).
LOC is the original test without blank lines, comments, package and import lines; short ≤ 15, medium 16–30, long > 30.
Features are the changes `feature_analysis/` detects in the improved version.

## Totals

| | developer-written | EvoSuite | total |
|---|--:|--:|--:|
| Claude Opus 4.8 | 31 | 31 | 62 |
| Claude Sonnet 4.6 | 30 | 29 | 59 |
| GPT-5.5 | 29 | 30 | 59 |
| total | 90 | 90 | 180 |

LOC: short 59, medium 91, long 30. Projects: 14 of 14. Distinct tests: 179 of 180 slots. Part 1: 90 pairs, Part 2: 90 pairs.

## Feature coverage per model × origin

Features that occur in ≥ 2 % of that column's test cases (the rows of the RQ1 heatmap), and how many of the column's 30 pairs carry each.

| column | recurrent features | min pairs per feature | features with ≥ 3 pairs | features with no pair | min Part-2 pairs per feature |
|---|--:|--:|--:|---|--:|
| GPT-5.5 / EvoSuite | 8 | 2 | 7 | — | 2 |
| GPT-5.5 / developer-written | 20 | 2 | 14 | — | 1 |
| Claude Opus 4.8 / EvoSuite | 9 | 6 | 9 | — | 3 |
| Claude Opus 4.8 / developer-written | 22 | 1 | 20 | — | 1 |
| Claude Sonnet 4.6 / EvoSuite | 9 | 3 | 9 | — | 2 |
| Claude Sonnet 4.6 / developer-written | 16 | 1 | 13 | — | 1 |

## Survey 1

### Part 1 — six pairs shown as twelve Likert items

| pair | screens (I = improved, O = original) | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | 7O, 12I | GPT-5.5 | developer-written | threeten-extra | `TestInternationalFixedChronology#test_chronology` | 76 (long) | Line Comment Deleted, Blank-Line Separation Added, Extract Attribute, Modify Class Annotation |
| 2 | 5I, 11O | Claude Opus 4.8 | developer-written | commons-codec | `MurmurHash2Test#testHash32ByteArrayIntInt` | 17 (medium) | Variable Rename, Javadoc Added/Updated, Attribute Rename, Extract Attribute, Change Attribute Access Modifier |
| 3 | 6I, 14O | GPT-5.5 | EvoSuite | commons-csv | `CSVRecord_ESTest#test16` | 22 (medium) | Variable Rename, Blank-Line Separation Added |
| 4 | 4O, 8I | Claude Sonnet 4.6 | EvoSuite | commons-collections | `FilterListIterator_ESTest#test08` | 18 (medium) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Inline Variable |
| 5 | 3O, 13I | Claude Sonnet 4.6 | developer-written | jsoup | `EntitiesTest#alwaysEscapeLtAndGtInAttributeValues` | 12 (short) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Line Comment Deleted, Blank-Line Separation Added, Extract Variable, Extract Attribute, Replace Variable With Attribute |
| 6 | 2I, 9O | Claude Opus 4.8 | EvoSuite | commons-compress | `LZMAUtils_ESTest#test09` | 9 (short) | Variable Rename, Method Rename, Javadoc Added/Updated, Blank-Line Separation Added |

### Part 2 — six side-by-side comparisons

| pair | improved version is | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | Test A | Claude Opus 4.8 | EvoSuite | jackson-annotations | `JsonAutoDetect_ESTest#test36` | 19 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Inline Variable, Change Variable Type |
| 2 | Test A | Claude Sonnet 4.6 | developer-written | commons-collections | `IndexedCollectionTest#testUnsupportedAdd` | 224 (long) | Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Line Comment Deleted, Javadoc Deleted, Blank-Line Separation Added, Change Attribute Type, Inline Method, Change Method Access Modifier, Remove Parameter, Remove Class Annotation |
| 3 | Test B | GPT-5.5 | EvoSuite | commons-lang | `TimedSemaphore_ESTest#test12` | 21 (medium) | Variable Rename, Line Comment Deleted, Blank-Line Separation Added, Extract Variable, Add Variable Modifier |
| 4 | Test A | Claude Opus 4.8 | developer-written | commons-compress | `LZMAUtilsTest#testCanTurnOffCaching` | 12 (short) | Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |
| 5 | Test B | GPT-5.5 | EvoSuite | itextpdf | `GroupedRandomAccessSource_ESTest#test5` | 24 (medium) | Variable Rename, Blank-Line Separation Added, Extract Variable |
| 6 | Test B | GPT-5.5 | developer-written | commons-text | `RandomStringGeneratorTest#testWithinMultipleRanges` | 25 (medium) | Variable Rename, Line Comment Deleted, Blank-Line Separation Added, Add Variable Modifier, Parameterize Variable, Replace Variable With Attribute, Extract Method |

## Survey 2

### Part 1 — six pairs shown as twelve Likert items

| pair | screens (I = improved, O = original) | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | 7O, 12I | Claude Opus 4.8 | developer-written | commons-codec | `BinaryCodecTest#testEncodeByteArray` | 151 (long) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Block Comment Deleted, Blank-Line Separation Added, Inline Variable, Add Variable Modifier, Extract Method, Change Attribute Access Modifier |
| 2 | 5I, 11O | GPT-5.5 | EvoSuite | commons-text | `LevenshteinDetailedDistance_ESTest#test10` | 16 (medium) | Variable Rename, Blank-Line Separation Added, Parameterize Variable, Extract Attribute, Extract Method |
| 3 | 6I, 14O | Claude Sonnet 4.6 | developer-written | commons-codec | `SoundexTest#testSoundexUtilsNullBehaviour` | 13 (short) | Method Rename, Blank-Line Separation Added |
| 4 | 4O, 8I | Claude Opus 4.8 | EvoSuite | jackson-annotations | `JsonAutoDetect_ESTest#test07` | 18 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Inline Variable |
| 5 | 3O, 13I | Claude Sonnet 4.6 | EvoSuite | commons-io | `ByteOrderMark_ESTest#test13` | 10 (short) | Variable Rename, Method Rename, Javadoc Added/Updated, Inline Variable |
| 6 | 2I, 9O | GPT-5.5 | developer-written | commons-compress | `SegmentConstantPoolArrayCacheTest#testMultipleArrayMultipleHit` | 26 (medium) | Variable Rename, Line Comment Deleted, Blank-Line Separation Added, Extract Attribute, Extract Method |

### Part 2 — six side-by-side comparisons

| pair | improved version is | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | Test A | Claude Opus 4.8 | EvoSuite | commons-text | `AlphabetConverter_ESTest#test04` | 16 (medium) | Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |
| 2 | Test A | Claude Opus 4.8 | developer-written | commons-collections | `IndexedCollectionTest#testCollectionAdd` | 235 (long) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Extract Variable, Change Variable Type, Replace Variable With Attribute, Inline Method, Change Method Access Modifier, Add Method Modifier, Remove Class Annotation |
| 3 | Test B | GPT-5.5 | developer-written | jackson-annotations | `JsonIncludePropertiesTest#testFromAnnotation` | 23 (medium) | Variable Rename, Line Comment Deleted, Blank-Line Separation Added, Extract Variable |
| 4 | Test A | Claude Sonnet 4.6 | developer-written | commons-lang | `RandomUtilsTest#testConstructor` | 10 (short) | Line Comment Added/Updated, Javadoc Added/Updated |
| 5 | Test B | GPT-5.5 | EvoSuite | commons-io | `NullInputStream_ESTest#test07` | 14 (short) | Variable Rename, Line Comment Deleted, Blank-Line Separation Added, Extract Variable, Add Variable Modifier |
| 6 | Test B | Claude Sonnet 4.6 | EvoSuite | commons-io | `FileAlterationObserver_ESTest#test04` | 28 (medium) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Inline Variable |

## Survey 3

### Part 1 — six pairs shown as twelve Likert items

| pair | screens (I = improved, O = original) | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | 7O, 12I | GPT-5.5 | EvoSuite | jackson-annotations | `JsonIgnoreProperties_ESTest#test03` | 16 (medium) | Variable Rename, Blank-Line Separation Added |
| 2 | 5I, 11O | Claude Opus 4.8 | EvoSuite | commons-codec | `PercentCodec_ESTest#test02` | 16 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |
| 3 | 6I, 14O | Claude Opus 4.8 | developer-written | commons-lang | `EnumUtilsTest#testGenerateBitVector_nullClassWithArray` | 9 (short) | Javadoc Added/Updated |
| 4 | 4O, 8I | Claude Sonnet 4.6 | developer-written | spatial4j | `TestGeohashUtils#testLookupHashLenForWidthHeight` | 16 (medium) | Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Extract Attribute |
| 5 | 3O, 13I | Claude Sonnet 4.6 | EvoSuite | commons-io | `FileTimes_ESTest#test16` | 10 (short) | Variable Rename, Method Rename, Line Comment Added/Updated, Blank-Line Separation Added |
| 6 | 2I, 9O | GPT-5.5 | developer-written | commons-collections | `SparseBloomFilterTest#testContains` | 101 (long) | Variable Rename, Line Comment Deleted, Javadoc Deleted, Blank-Line Separation Added, Extract Variable, Change Variable Type, Add Variable Modifier, Parameterize Variable, Extract Method, Change Method Access Modifier, Remove Method Modifier, Change Return Type |

### Part 2 — six side-by-side comparisons

| pair | improved version is | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | Test A | Claude Sonnet 4.6 | EvoSuite | commons-text | `LongestCommonSubsequence_ESTest#test02` | 11 (short) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Extract Variable |
| 2 | Test A | Claude Sonnet 4.6 | developer-written | commons-io | `CircularByteBufferTest#testReadByteArrayIllegalArgumentException` | 9 (short) | Variable Rename, Method Rename, Line Comment Deleted, Blank-Line Separation Added, Extract Variable, Extract Attribute |
| 3 | Test B | GPT-5.5 | EvoSuite | commons-lang | `TimedSemaphore_ESTest#test12` | 21 (medium) | Variable Rename, Line Comment Deleted, Blank-Line Separation Added, Extract Variable, Add Variable Modifier |
| 4 | Test A | GPT-5.5 | developer-written | commons-math | `CovarianceTest#testLongley` | 20 (medium) | Blank-Line Separation Added, Attribute Rename, Extract Variable, Add Attribute Modifier, Change Method Access Modifier, Change Attribute Access Modifier |
| 5 | Test B | Claude Opus 4.8 | EvoSuite | commons-math | `CalinskiHarabasz_ESTest#test3` | 19 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |
| 6 | Test B | Claude Opus 4.8 | developer-written | commons-collections | `IndexedCollectionTest#testCollectionRemove` | 236 (long) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Line Comment Deleted, Blank-Line Separation Added, Inline Variable, Change Variable Type, Change Attribute Type, Replace Variable With Attribute, Inline Method, Change Method Access Modifier, Add Method Modifier, Remove Class Annotation |

## Survey 4

### Part 1 — six pairs shown as twelve Likert items

| pair | screens (I = improved, O = original) | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | 7O, 12I | GPT-5.5 | developer-written | threeten-extra | `TestDayOfYear#test_atYear_int_invalidDay` | 21 (medium) | Variable Rename, Line Comment Deleted, Blank-Line Separation Added, Attribute Rename, Extract Variable |
| 2 | 5I, 11O | Claude Sonnet 4.6 | developer-written | jackson-annotations | `JsonIgnorePropertiesTest#testEquality` | 15 (short) | Variable Rename, Line Comment Added/Updated, Blank-Line Separation Added |
| 3 | 6I, 14O | Claude Sonnet 4.6 | EvoSuite | commons-lang | `TimedSemaphore_ESTest#test01` | 38 (long) | Variable Rename, Line Comment Added/Updated, Blank-Line Separation Added, Inline Variable |
| 4 | 4O, 8I | Claude Opus 4.8 | developer-written | commons-lang | `EnumUtilsTest#testGenerateBitVector_nullArray` | 9 (short) | Javadoc Added/Updated |
| 5 | 3O, 13I | GPT-5.5 | EvoSuite | jackson-annotations | `JsonIncludeProperties_ESTest#test11` | 21 (medium) | Variable Rename, Blank-Line Separation Added |
| 6 | 2I, 9O | Claude Opus 4.8 | EvoSuite | commons-text | `LevenshteinDetailedDistance_ESTest#test11` | 16 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Change Variable Type |

### Part 2 — six side-by-side comparisons

| pair | improved version is | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | Test A | Claude Opus 4.8 | developer-written | commons-text | `RandomStringGeneratorTest#testZeroLength` | 12 (short) | Javadoc Added/Updated, Blank-Line Separation Added, Extract Variable |
| 2 | Test A | Claude Sonnet 4.6 | developer-written | commons-collections | `SimpleBloomFilterTest#testContains` | 101 (long) | Line Comment Added/Updated, Javadoc Deleted, Blank-Line Separation Added, Extract Variable, Change Variable Type, Inline Method, Change Method Access Modifier, Remove Method Modifier, Change Return Type |
| 3 | Test B | Claude Sonnet 4.6 | EvoSuite | commons-math | `TriDiagonalTransformer_ESTest#test1` | 22 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Extract Attribute |
| 4 | Test A | GPT-5.5 | EvoSuite | commons-collections | `ObjectGraphIterator_ESTest#test6` | 14 (short) | Variable Rename, Line Comment Added/Updated, Blank-Line Separation Added |
| 5 | Test B | GPT-5.5 | developer-written | jsoup | `ValidateTest#testNotEmpty` | 24 (medium) | Line Comment Deleted, Blank-Line Separation Added, Inline Variable, Extract Method, Assert Throws, Remove Class Annotation |
| 6 | Test B | Claude Opus 4.8 | EvoSuite | threeten-extra | `AccountingChronology_ESTest#test21` | 16 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Line Comment Deleted, Blank-Line Separation Added, Extract Variable |

## Survey 5

### Part 1 — six pairs shown as twelve Likert items

| pair | screens (I = improved, O = original) | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | 7O, 12I | Claude Sonnet 4.6 | developer-written | jackson-annotations | `JsonAutoDetectTest#testSimpleChanges` | 21 (medium) | Variable Rename, Line Comment Added/Updated, Blank-Line Separation Added |
| 2 | 5I, 11O | Claude Sonnet 4.6 | EvoSuite | jsoup | `Printer_ESTest#test09` | 19 (medium) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |
| 3 | 6I, 14O | GPT-5.5 | developer-written | jackson-annotations | `JsonIncludePropertiesTest#testHashCodeIncludesContents` | 13 (short) | Variable Rename, Blank-Line Separation Added |
| 4 | 4O, 8I | GPT-5.5 | EvoSuite | commons-text | `AlphabetConverter_ESTest#test15` | 33 (long) | Variable Rename, Blank-Line Separation Added |
| 5 | 3O, 13I | Claude Opus 4.8 | EvoSuite | commons-compress | `X5455_ExtendedTimestamp_ESTest#test17` | 12 (short) | Variable Rename, Method Rename, Javadoc Added/Updated, Blank-Line Separation Added, Inline Variable |
| 6 | 2I, 9O | Claude Opus 4.8 | developer-written | commons-compress | `LZMAUtilsTest#testGetUncompressedFilename` | 20 (medium) | Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Add Method Annotation, Remove Method Annotation, Add Parameter |

### Part 2 — six side-by-side comparisons

| pair | improved version is | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | Test A | Claude Sonnet 4.6 | developer-written | commons-compress | `ExtraFieldUtilsTest#testMerge` | 45 (long) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Attribute Rename, Extract Attribute |
| 2 | Test A | GPT-5.5 | EvoSuite | commons-cli | `PosixParser_ESTest#test04` | 19 (medium) | Variable Rename, Line Comment Added/Updated, Blank-Line Separation Added, Extract Attribute |
| 3 | Test B | Claude Sonnet 4.6 | EvoSuite | threeten-extra | `InternationalFixedChronology_ESTest#test07` | 16 (medium) | Variable Rename, Method Rename, Javadoc Added/Updated, Line Comment Deleted, Blank-Line Separation Added, Extract Variable, Inline Variable |
| 4 | Test A | Claude Opus 4.8 | developer-written | spatial4j | `SpatialContextFactoryTest#testSystemPropertyLookup` | 21 (medium) | Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Attribute Rename, Change Attribute Access Modifier |
| 5 | Test B | GPT-5.5 | developer-written | jackson-annotations | `JsonAutoDetectTest#testAnnotationProperties` | 15 (short) | Variable Rename, Line Comment Deleted, Blank-Line Separation Added, Extract Attribute, Extract Method |
| 6 | Test B | Claude Opus 4.8 | EvoSuite | jsoup | `StringUtil_ESTest#test14` | 9 (short) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |

## Survey 6

### Part 1 — six pairs shown as twelve Likert items

| pair | screens (I = improved, O = original) | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | 7O, 12I | Claude Sonnet 4.6 | EvoSuite | itextpdf | `GroupedRandomAccessSource_ESTest#test4` | 24 (medium) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |
| 2 | 5I, 11O | GPT-5.5 | EvoSuite | commons-collections | `SimpleBloomFilter_ESTest#test04` | 18 (medium) | Variable Rename, Line Comment Deleted, Blank-Line Separation Added, Extract Attribute |
| 3 | 6I, 14O | Claude Sonnet 4.6 | developer-written | commons-codec | `BCodecTest#testBase64ImpossibleSamplesLenient` | 23 (medium) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Line Comment Deleted, Blank-Line Separation Added |
| 4 | 4O, 8I | Claude Opus 4.8 | EvoSuite | commons-compress | `X5455_ExtendedTimestamp_ESTest#test16` | 11 (short) | Variable Rename, Method Rename, Javadoc Added/Updated, Blank-Line Separation Added |
| 5 | 3O, 13I | Claude Opus 4.8 | developer-written | commons-collections | `IndexedCollectionTest#testEnsureDuplicateObjectsCauseException` | 214 (long) | Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Inline Method, Change Method Access Modifier, Change Return Type, Remove Thrown Exception Type, Remove Class Annotation |
| 6 | 2I, 9O | GPT-5.5 | developer-written | commons-text | `LevenshteinDetailedDistanceTest#testGetDefaultInstanceOne` | 10 (short) | Variable Rename, Blank-Line Separation Added, Extract Attribute |

### Part 2 — six side-by-side comparisons

| pair | improved version is | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | Test A | GPT-5.5 | developer-written | threeten-extra | `TestInternationalFixedChronology#test_era_yearDay_loop` | 80 (long) | Extract Attribute, Replace Variable With Attribute, Extract Method, Modify Class Annotation |
| 2 | Test A | Claude Opus 4.8 | EvoSuite | commons-csv | `CSVRecord_ESTest#test28` | 17 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Extract Variable, Inline Variable, Change Variable Type |
| 3 | Test B | Claude Opus 4.8 | developer-written | threeten-extra | `TestYears#test_parse_CharSequence_invalid` | 10 (short) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Modify Method Annotation |
| 4 | Test A | GPT-5.5 | EvoSuite | commons-collections | `FilterListIterator_ESTest#test04` | 21 (medium) | Variable Rename, Blank-Line Separation Added |
| 5 | Test B | Claude Sonnet 4.6 | developer-written | jackson-annotations | `JsonTypeInfoTest#testWriteTypeIdForDefaultImplFromAnnotation` | 14 (short) | Variable Rename, Line Comment Added/Updated, Line Comment Deleted, Blank-Line Separation Added |
| 6 | Test B | Claude Sonnet 4.6 | EvoSuite | spatial4j | `SpatialContextFactory_ESTest#test11` | 16 (medium) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Line Comment Deleted, Blank-Line Separation Added |

## Survey 7

### Part 1 — six pairs shown as twelve Likert items

| pair | screens (I = improved, O = original) | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | 7O, 12I | GPT-5.5 | developer-written | commons-text | `RandomStringGeneratorTest#testRemoveFilters` | 19 (medium) | Variable Rename, Blank-Line Separation Added, Extract Attribute |
| 2 | 5I, 11O | Claude Opus 4.8 | developer-written | threeten-extra | `TestDayOfMonth#test_adjustInto_null` | 18 (medium) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Line Comment Deleted, Extract Variable |
| 3 | 6I, 14O | GPT-5.5 | EvoSuite | commons-collections | `CartesianProductIterator_ESTest#test0` | 20 (medium) | Variable Rename, Blank-Line Separation Added |
| 4 | 4O, 8I | Claude Opus 4.8 | EvoSuite | commons-text | `AlphabetConverter_ESTest#test17` | 34 (long) | Line Comment Added/Updated, Javadoc Added/Updated, Line Comment Deleted, Blank-Line Separation Added |
| 5 | 3O, 13I | Claude Sonnet 4.6 | EvoSuite | threeten-extra | `Minutes_ESTest#test13` | 11 (short) | Variable Rename, Method Rename, Line Comment Added/Updated, Blank-Line Separation Added, Inline Variable, Extract Attribute |
| 6 | 2I, 9O | Claude Sonnet 4.6 | developer-written | commons-text | `LongestCommonSubsequenceTest#testGettingLongestCommonSubsequenceApplyStringNull` | 11 (short) | Method Rename, Line Comment Added/Updated, Blank-Line Separation Added, Attribute Rename, Extract Variable, Add Method Annotation |

### Part 2 — six side-by-side comparisons

| pair | improved version is | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | Test A | Claude Sonnet 4.6 | EvoSuite | spatial4j | `SpatialContextFactory_ESTest#test02` | 16 (medium) | Variable Rename, Method Rename, Javadoc Added/Updated, Line Comment Deleted, Blank-Line Separation Added |
| 2 | Test A | GPT-5.5 | EvoSuite | commons-text | `JaroWinklerSimilarity_ESTest#test4` | 15 (short) | Variable Rename, Blank-Line Separation Added, Add Variable Modifier, Extract Attribute |
| 3 | Test B | GPT-5.5 | developer-written | jackson-annotations | `JsonTypeInfoTest#testWriteTypeIdForDefaultImplEqualsAndHashCode` | 14 (short) | Variable Rename, Blank-Line Separation Added, Add Variable Modifier |
| 4 | Test A | Claude Opus 4.8 | EvoSuite | commons-codec | `PercentCodec_ESTest#test08` | 16 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |
| 5 | Test B | Claude Sonnet 4.6 | EvoSuite | commons-text | `RandomStringGenerator_ESTest#test00` | 18 (medium) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |
| 6 | Test B | Claude Opus 4.8 | developer-written | commons-codec | `BinaryCodecTest#testToByteArrayFromString` | 120 (long) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Line Comment Deleted, Javadoc Deleted, Blank-Line Separation Added, Add Variable Modifier, Extract Method, Remove Thrown Exception Type, Change Attribute Access Modifier |

## Survey 8

### Part 1 — six pairs shown as twelve Likert items

| pair | screens (I = improved, O = original) | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | 7O, 12I | GPT-5.5 | developer-written | threeten-extra | `TestMinutes#test_plus_int` | 17 (medium) | Variable Rename, Line Comment Deleted, Blank-Line Separation Added, Extract Variable |
| 2 | 5I, 11O | GPT-5.5 | EvoSuite | commons-codec | `Base16_ESTest#test02` | 20 (medium) | Variable Rename, Blank-Line Separation Added |
| 3 | 6I, 14O | Claude Opus 4.8 | developer-written | threeten-extra | `TestDayOfYear#test_of_int_tooLow` | 21 (medium) | Variable Rename, Javadoc Added/Updated, Line Comment Deleted, Extract Variable |
| 4 | 4O, 8I | Claude Sonnet 4.6 | EvoSuite | jsoup | `Tag_ESTest#test32` | 13 (short) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Extract Variable |
| 5 | 3O, 13I | Claude Opus 4.8 | EvoSuite | commons-codec | `PercentCodec_ESTest#test11` | 13 (short) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Extract Variable |
| 6 | 2I, 9O | Claude Sonnet 4.6 | developer-written | commons-cli | `OptionFormatterTest#testDefaultSyntaxFormat` | 46 (long) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |

### Part 2 — six side-by-side comparisons

| pair | improved version is | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | Test A | Claude Sonnet 4.6 | developer-written | commons-compress | `CodecEncodingTest#testGetSpecifier` | 24 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Extract Attribute, Modify Method Annotation |
| 2 | Test A | Claude Opus 4.8 | developer-written | commons-compress | `LZMAUtilsTest#testGetCompressedFilename` | 16 (medium) | Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Extract Method, Remove Method Annotation |
| 3 | Test B | GPT-5.5 | EvoSuite | spatial4j | `GeohashUtils_ESTest#test1` | 9 (short) | Variable Rename, Method Rename, Blank-Line Separation Added, Extract Attribute |
| 4 | Test A | Claude Opus 4.8 | EvoSuite | commons-io | `SequenceReader_ESTest#test3` | 19 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Line Comment Deleted, Blank-Line Separation Added, Inline Variable |
| 5 | Test B | GPT-5.5 | developer-written | jsoup | `ValidateTest#testNotNull` | 14 (short) | Line Comment Deleted, Remove Class Annotation |
| 6 | Test B | Claude Sonnet 4.6 | developer-written | commons-collections | `BoundedIteratorTest#testRemoveUnsupported` | 42 (long) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Javadoc Deleted, Blank-Line Separation Added, Attribute Rename, Change Variable Type, Change Attribute Type, Add Attribute Modifier, Remove Method Annotation, Remove Thrown Exception Type |

## Survey 9

### Part 1 — six pairs shown as twelve Likert items

| pair | screens (I = improved, O = original) | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | 7O, 12I | GPT-5.5 | EvoSuite | commons-io | `ReaderInputStream_ESTest#test04` | 15 (short) | Variable Rename, Blank-Line Separation Added, Extract Attribute |
| 2 | 5I, 11O | Claude Opus 4.8 | EvoSuite | commons-codec | `Base58_ESTest#test03` | 16 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Line Comment Deleted, Blank-Line Separation Added, Extract Variable |
| 3 | 6I, 14O | GPT-5.5 | developer-written | commons-cli | `OptionsTest#testAddNonConflictingOptions` | 33 (long) | Variable Rename, Line Comment Deleted, Blank-Line Separation Added, Remove Class Annotation |
| 4 | 4O, 8I | Claude Sonnet 4.6 | developer-written | jsoup | `RegexTest#testRegexDelegates` | 22 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Line Comment Deleted, Blank-Line Separation Added, Modify Method Annotation |
| 5 | 3O, 13I | Claude Opus 4.8 | developer-written | commons-text | `RandomStringGeneratorTest#testChangeOfFilter` | 15 (short) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Attribute Rename, Change Variable Type, Extract Attribute |
| 6 | 2I, 9O | Claude Sonnet 4.6 | EvoSuite | jackson-annotations | `JsonAutoDetect_ESTest#test22` | 16 (medium) | Variable Rename, Javadoc Added/Updated, Blank-Line Separation Added |

### Part 2 — six side-by-side comparisons

| pair | improved version is | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | Test A | Claude Opus 4.8 | developer-written | commons-lang | `EnumUtilsTest#testGenerateBitVectorsFromArray` | 23 (medium) | Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |
| 2 | Test A | Claude Sonnet 4.6 | EvoSuite | commons-lang | `CharRange_ESTest#test10` | 15 (short) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |
| 3 | Test B | GPT-5.5 | EvoSuite | commons-csv | `CSVRecord_ESTest#test00` | 21 (medium) | Variable Rename, Blank-Line Separation Added, Extract Variable, Add Variable Modifier |
| 4 | Test A | Claude Opus 4.8 | EvoSuite | spatial4j | `SpatialContextFactory_ESTest#test00` | 16 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Line Comment Deleted, Blank-Line Separation Added, Change Variable Type |
| 5 | Test B | GPT-5.5 | developer-written | commons-collections | `BoundedIteratorTest#testRemoveWithoutCallingNext` | 35 (long) | Javadoc Added/Updated, Javadoc Deleted, Blank-Line Separation Added, Attribute Rename, Change Variable Type, Extract Attribute, Change Attribute Type, Add Attribute Modifier, Remove Method Annotation, Remove Thrown Exception Type |
| 6 | Test B | Claude Sonnet 4.6 | developer-written | commons-io | `SequenceReaderTest#testReadClosedReader` | 15 (short) | Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Extract Attribute, Add Method Annotation |

## Survey 10

### Part 1 — six pairs shown as twelve Likert items

| pair | screens (I = improved, O = original) | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | 7O, 12I | Claude Opus 4.8 | developer-written | jsoup | `StreamParserTest#closedOnStreamDrained` | 32 (long) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Line Comment Deleted, Blank-Line Separation Added, Extract Attribute, Replace Variable With Attribute, Change Method Access Modifier |
| 2 | 5I, 11O | GPT-5.5 | developer-written | commons-codec | `URLCodecTest#testDecodeInvalidContent` | 27 (medium) | Variable Rename, Method Rename, Blank-Line Separation Added, Attribute Rename, Change Attribute Access Modifier |
| 3 | 6I, 14O | GPT-5.5 | EvoSuite | commons-codec | `Base16_ESTest#test00` | 14 (short) | Variable Rename, Blank-Line Separation Added, Extract Attribute |
| 4 | 4O, 8I | Claude Sonnet 4.6 | EvoSuite | commons-csv | `CSVRecord_ESTest#test01` | 20 (medium) | Variable Rename, Line Comment Added/Updated, Block Comment Added/Updated, Blank-Line Separation Added, Extract Variable |
| 5 | 3O, 13I | Claude Sonnet 4.6 | developer-written | commons-text | `LevenshteinDetailedDistanceTest#testHashCode` | 16 (medium) | Variable Rename, Line Comment Added/Updated, Blank-Line Separation Added, Add Method Annotation |
| 6 | 2I, 9O | Claude Opus 4.8 | EvoSuite | commons-lang | `CharRange_ESTest#test23` | 13 (short) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Extract Variable |

### Part 2 — six side-by-side comparisons

| pair | improved version is | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | Test A | Claude Sonnet 4.6 | developer-written | commons-io | `BoundedReaderTest#testLineNumberReaderAndFileReaderLastLineEolYes` | 26 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Attribute Rename, Change Method Access Modifier |
| 2 | Test A | Claude Sonnet 4.6 | EvoSuite | jackson-annotations | `JacksonInject_ESTest#test13` | 14 (short) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Inline Variable |
| 3 | Test B | Claude Opus 4.8 | EvoSuite | jackson-annotations | `JsonTypeInfo_ESTest#test38` | 15 (short) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Extract Variable, Inline Variable, Change Variable Type |
| 4 | Test A | GPT-5.5 | EvoSuite | commons-lang | `NumericEntityUnescaper_ESTest#test0` | 17 (medium) | Variable Rename, Blank-Line Separation Added |
| 5 | Test B | GPT-5.5 | developer-written | commons-codec | `URLCodecTest#testSafeCharEncodeDecode` | 24 (medium) | Line Comment Added/Updated, Blank-Line Separation Added, Extract Attribute, Replace Variable With Attribute |
| 6 | Test B | Claude Opus 4.8 | developer-written | commons-collections | `BoundedIteratorTest#testForEachRemaining` | 36 (long) | Variable Rename, Method Rename, Javadoc Added/Updated, Blank-Line Separation Added, Attribute Rename, Extract Variable, Change Variable Type, Extract Attribute, Change Attribute Type, Add Attribute Modifier, Change Method Access Modifier, Remove Method Annotation, Change Return Type, Remove Thrown Exception Type |

## Survey 11

### Part 1 — six pairs shown as twelve Likert items

| pair | screens (I = improved, O = original) | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | 7O, 12I | Claude Opus 4.8 | developer-written | commons-codec | `StringUtilsTest#testNewStringUtf16Le` | 24 (medium) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Attribute Rename, Replace Variable With Attribute, Inline Method |
| 2 | 5I, 11O | GPT-5.5 | EvoSuite | commons-codec | `XXHash32_ESTest#test2` | 14 (short) | Variable Rename, Blank-Line Separation Added, Extract Attribute |
| 3 | 6I, 14O | Claude Sonnet 4.6 | developer-written | commons-text | `AlphabetConverterTest#testEquals` | 51 (long) | Variable Rename, Line Comment Added/Updated, Blank-Line Separation Added |
| 4 | 4O, 8I | Claude Opus 4.8 | EvoSuite | commons-csv | `CSVRecord_ESTest#test15` | 21 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Line Comment Deleted, Blank-Line Separation Added, Extract Variable, Add Variable Modifier |
| 5 | 3O, 13I | GPT-5.5 | developer-written | commons-compress | `ByteUtilsTest#testToLittleEndianToDataOutputUnsignedInt32` | 14 (short) | Variable Rename, Blank-Line Separation Added, Extract Attribute, Replace Variable With Attribute |
| 6 | 2I, 9O | Claude Sonnet 4.6 | EvoSuite | commons-csv | `CSVRecord_ESTest#test09` | 21 (medium) | Variable Rename, Line Comment Added/Updated, Blank-Line Separation Added, Extract Variable |

### Part 2 — six side-by-side comparisons

| pair | improved version is | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | Test A | Claude Sonnet 4.6 | developer-written | commons-lang | `TimedSemaphoreTest#testAcquireLimit` | 30 (medium) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Line Comment Deleted, Javadoc Deleted, Blank-Line Separation Added, Extract Variable |
| 2 | Test A | Claude Sonnet 4.6 | EvoSuite | commons-lang | `TimedSemaphore_ESTest#test17` | 16 (medium) | Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Line Comment Deleted, Blank-Line Separation Added, Inline Variable |
| 3 | Test B | GPT-5.5 | developer-written | commons-codec | `Base16Test#testBase16AtBufferMiddle` | 31 (long) | Variable Rename, Method Rename, Extract Attribute, Replace Variable With Attribute |
| 4 | Test A | Claude Opus 4.8 | developer-written | commons-lang | `LookupTranslatorTest#testSupplementaryKey` | 13 (short) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Extract Attribute, Replace Variable With Attribute |
| 5 | Test B | GPT-5.5 | EvoSuite | commons-lang | `NumericEntityUnescaper_ESTest#test4` | 16 (medium) | Variable Rename, Blank-Line Separation Added, Extract Attribute |
| 6 | Test B | Claude Opus 4.8 | EvoSuite | commons-compress | `X5455_ExtendedTimestamp_ESTest#test19` | 12 (short) | Variable Rename, Method Rename, Javadoc Added/Updated, Blank-Line Separation Added, Inline Variable |

## Survey 12

### Part 1 — six pairs shown as twelve Likert items

| pair | screens (I = improved, O = original) | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | 7O, 12I | Claude Sonnet 4.6 | EvoSuite | commons-collections | `FilterListIterator_ESTest#test01` | 20 (medium) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |
| 2 | 5I, 11O | Claude Sonnet 4.6 | developer-written | commons-codec | `URLCodecTest#testUnsafeEncodeDecode` | 24 (medium) | Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Extract Attribute, Replace Variable With Attribute |
| 3 | 6I, 14O | Claude Opus 4.8 | developer-written | commons-compress | `LZMAUtilsTest#testCachingIsEnabledByDefaultAndLZMAIsPresent` | 7 (short) | Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |
| 4 | 4O, 8I | Claude Opus 4.8 | EvoSuite | jackson-annotations | `JsonAutoDetect_ESTest#test04` | 16 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Inline Variable, Change Variable Type |
| 5 | 3O, 13I | GPT-5.5 | developer-written | commons-collections | `BoundedIteratorTest#testOffsetGreaterThanSize` | 34 (long) | Javadoc Added/Updated, Javadoc Deleted, Blank-Line Separation Added, Attribute Rename, Change Variable Type, Change Attribute Type, Add Attribute Modifier, Remove Method Annotation, Remove Thrown Exception Type |
| 6 | 2I, 9O | GPT-5.5 | EvoSuite | jsoup | `Printer_ESTest#test08` | 15 (short) | Variable Rename, Blank-Line Separation Added, Parameterize Variable, Extract Attribute, Extract Method |

### Part 2 — six side-by-side comparisons

| pair | improved version is | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | Test A | Claude Opus 4.8 | EvoSuite | commons-compress | `LZMAUtils_ESTest#test02` | 10 (short) | Variable Rename, Method Rename, Javadoc Added/Updated, Blank-Line Separation Added, Extract Variable |
| 2 | Test A | GPT-5.5 | EvoSuite | commons-text | `LevenshteinDistance_ESTest#test06` | 17 (medium) | Variable Rename, Line Comment Added/Updated, Blank-Line Separation Added |
| 3 | Test B | Claude Opus 4.8 | EvoSuite | commons-lang | `CharRange_ESTest#test14` | 16 (medium) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |
| 4 | Test A | Claude Opus 4.8 | developer-written | spatial4j | `TestGeohashUtils#testDecodeEncode` | 18 (medium) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Attribute Rename, Extract Variable, Add Variable Modifier, Remove Variable Modifier, Extract Attribute, Add Attribute Modifier, Change Attribute Access Modifier |
| 5 | Test B | Claude Sonnet 4.6 | EvoSuite | commons-collections | `SparseBloomFilter_ESTest#test05` | 18 (medium) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |
| 6 | Test B | GPT-5.5 | developer-written | jackson-annotations | `JacksonInjectTest#testEmpty` | 12 (short) | Line Comment Deleted, Attribute Rename, Extract Method |

## Survey 13

### Part 1 — six pairs shown as twelve Likert items

| pair | screens (I = improved, O = original) | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | 7O, 12I | GPT-5.5 | developer-written | commons-io | `ByteOrderMarkTest#testGetInt` | 14 (short) | Javadoc Added/Updated, Blank-Line Separation Added, Attribute Rename, Extract Method |
| 2 | 5I, 11O | Claude Opus 4.8 | developer-written | commons-codec | `QCodecTest#testBasicEncodeDecode` | 21 (medium) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Extract Variable, Inline Variable |
| 3 | 6I, 14O | Claude Opus 4.8 | EvoSuite | jackson-annotations | `JsonAutoDetect_ESTest#test01` | 20 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |
| 4 | 4O, 8I | Claude Opus 4.8 | EvoSuite | commons-compress | `SeekableInMemoryByteChannel_ESTest#test02` | 16 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Extract Variable |
| 5 | 3O, 13I | Claude Sonnet 4.6 | EvoSuite | jackson-annotations | `JsonSetter_ESTest#test16` | 14 (short) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Inline Variable |
| 6 | 2I, 9O | Claude Sonnet 4.6 | developer-written | commons-codec | `Base58Test#testTestVectors` | 35 (long) | Variable Rename, Line Comment Added/Updated, Blank-Line Separation Added, Inline Variable, Change Variable Type, Extract Method |

### Part 2 — six side-by-side comparisons

| pair | improved version is | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | Test A | GPT-5.5 | developer-written | commons-io | `UnsynchronizedByteArrayInputStreamTest#testMarkReset` | 45 (long) | Variable Rename, Line Comment Added/Updated, Blank-Line Separation Added, Parameterize Variable, Extract Attribute, Extract Method, Remove Variable Annotation |
| 2 | Test A | Claude Opus 4.8 | developer-written | commons-math | `MullerSolverTest#testSinFunction` | 24 (medium) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Inline Variable, Change Variable Type, Add Variable Modifier, Extract Attribute, Replace Variable With Attribute, Extract Method |
| 3 | Test B | Claude Sonnet 4.6 | developer-written | commons-collections | `BoundedIteratorTest#testRemoveFirst` | 47 (long) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Attribute Rename, Change Variable Type, Change Attribute Type, Add Attribute Modifier, Remove Method Annotation, Remove Thrown Exception Type |
| 4 | Test A | GPT-5.5 | EvoSuite | commons-cli | `OptionFormatter_ESTest#test00` | 10 (short) | Variable Rename, Method Rename, Blank-Line Separation Added, Extract Attribute |
| 5 | Test B | Claude Sonnet 4.6 | developer-written | commons-io | `SequenceReaderTest#testReadCharArray` | 23 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Attribute Rename |
| 6 | Test B | Claude Sonnet 4.6 | EvoSuite | commons-lang | `AppendableJoiner_ESTest#test06` | 12 (short) | Variable Rename, Line Comment Added/Updated, Blank-Line Separation Added |

## Survey 14

### Part 1 — six pairs shown as twelve Likert items

| pair | screens (I = improved, O = original) | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | 7O, 12I | Claude Opus 4.8 | EvoSuite | commons-collections | `IndexedCollection_ESTest#test10` | 18 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Change Variable Type, Add Variable Modifier |
| 2 | 5I, 11O | GPT-5.5 | developer-written | commons-lang | `IEEE754rUtilsTest#testEnforceExceptions` | 13 (short) | Extract Attribute, Extract Method |
| 3 | 6I, 14O | GPT-5.5 | EvoSuite | jackson-annotations | `JsonIgnoreProperties_ESTest#test00` | 25 (medium) | Variable Rename, Blank-Line Separation Added |
| 4 | 4O, 8I | Claude Sonnet 4.6 | EvoSuite | jsoup | `Validate_ESTest#test09` | 16 (medium) | Variable Rename, Method Rename, Javadoc Added/Updated, Line Comment Deleted, Blank-Line Separation Added |
| 5 | 3O, 13I | Claude Sonnet 4.6 | developer-written | jsoup | `TagSetTest#canRetrieveNewTagsInsensitive` | 32 (long) | Line Comment Added/Updated, Blank-Line Separation Added |
| 6 | 2I, 9O | Claude Opus 4.8 | developer-written | commons-compress | `LZMAUtilsTest#testTurningOnCachingReEvaluatesAvailability` | 13 (short) | Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |

### Part 2 — six side-by-side comparisons

| pair | improved version is | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | Test A | Claude Opus 4.8 | developer-written | commons-collections | `ObjectGraphIteratorTest#testIteration_IteratorOfIteratorsWithEmptyIterators` | 61 (long) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Attribute Rename, Add Attribute Modifier, Change Attribute Access Modifier |
| 2 | Test A | GPT-5.5 | EvoSuite | commons-collections | `SparseBloomFilter_ESTest#test02` | 19 (medium) | Variable Rename, Blank-Line Separation Added |
| 3 | Test B | Claude Sonnet 4.6 | EvoSuite | commons-codec | `Base16_ESTest#test03` | 16 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Blank-Line Separation Added, Extract Variable, Extract Attribute |
| 4 | Test A | Claude Opus 4.8 | EvoSuite | itextpdf | `GroupedRandomAccessSource_ESTest#test3` | 24 (medium) | Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |
| 5 | Test B | GPT-5.5 | developer-written | jackson-annotations | `JsonSetterTest#testEmpty` | 11 (short) | Attribute Rename, Extract Method |
| 6 | Test B | Claude Sonnet 4.6 | developer-written | commons-text | `UnicodeEscaperTest#testBetween` | 9 (short) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Extract Attribute |

## Survey 15

### Part 1 — six pairs shown as twelve Likert items

| pair | screens (I = improved, O = original) | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | 7O, 12I | Claude Sonnet 4.6 | EvoSuite | commons-io | `CircularByteBuffer_ESTest#test27` | 16 (medium) | Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |
| 2 | 5I, 11O | Claude Sonnet 4.6 | developer-written | commons-text | `RandomStringGeneratorTest#testGenerateMinMaxLengthInvalidLength` | 14 (short) | Line Comment Added/Updated |
| 3 | 6I, 14O | GPT-5.5 | EvoSuite | commons-collections | `SimpleBloomFilter_ESTest#test03` | 14 (short) | Variable Rename, Blank-Line Separation Added, Extract Variable |
| 4 | 4O, 8I | Claude Opus 4.8 | EvoSuite | commons-cli | `PosixParser_ESTest#test02` | 16 (medium) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Extract Variable |
| 5 | 3O, 13I | GPT-5.5 | developer-written | commons-lang | `CharRangeTest#testHashCode` | 20 (medium) | Variable Rename, Blank-Line Separation Added, Extract Attribute, Extract Method |
| 6 | 2I, 9O | Claude Opus 4.8 | developer-written | commons-csv | `CSVRecordTest#testGetStringInconsistentRecord` | 36 (long) | Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Extract Variable |

### Part 2 — six side-by-side comparisons

| pair | improved version is | model | origin | project | test | LOC | features in the improved version |
|--:|---|---|---|---|---|--:|---|
| 1 | Test A | Claude Opus 4.8 | developer-written | commons-lang | `RandomUtilsTest#testNextLongRandomResult` | 13 (short) | Variable Rename, Javadoc Added/Updated, Blank-Line Separation Added |
| 2 | Test A | GPT-5.5 | EvoSuite | jackson-annotations | `JsonIgnoreProperties_ESTest#test09` | 16 (medium) | Variable Rename, Blank-Line Separation Added, Change Variable Type |
| 3 | Test B | Claude Sonnet 4.6 | EvoSuite | itextpdf | `GroupedRandomAccessSource_ESTest#test8` | 19 (medium) | Variable Rename, Method Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added |
| 4 | Test A | Claude Sonnet 4.6 | developer-written | commons-compress | `SeekableInMemoryByteChannelTest#testThrowWhenTruncatingToIncorrectSize` | 16 (medium) | Variable Rename, Line Comment Added/Updated, Blank-Line Separation Added |
| 5 | Test B | Claude Opus 4.8 | EvoSuite | commons-io | `FileAlterationObserver_ESTest#test19` | 11 (short) | Variable Rename, Method Rename, Javadoc Added/Updated, Blank-Line Separation Added |
| 6 | Test B | Claude Opus 4.8 | developer-written | jsoup | `NodeIteratorTest#canIterateFirstEmptySibling` | 42 (long) | Variable Rename, Line Comment Added/Updated, Javadoc Added/Updated, Blank-Line Separation Added, Inline Variable |
