# Feature change analysis

Generated `2026-09-29T14:50:49`  
Data root: `/Users/tenghaha/Projects/Final_Agent_Understandability/data/improved`

| Tool | Owns |
|---|---|
| gumtree | comment changes only, post-processed at comment-unit level: Line Comment / Javadoc / Block Comment, each Added/Updated or Deleted |
| refactoringminer | semantic refactorings |
| javaparser | 'Blank-Line Separation Added' when the @Test-body block count INCREASES |

Tests: **17190**, complete (all three tools OK): **17186**

## Feature frequency (share of tests with the feature)

| Model | Src | Granularity | Feature | Tests | Total | Rate |
|---|---|---|---|---:|---:|---:|
| gpt-5.5 | auto | ALL | Variable Rename | 2820 | 2884 | 0.98 |
| gpt-5.5 | auto | ALL | Method Rename | 296 | 2884 | 0.10 |
| gpt-5.5 | auto | ALL | Line Comment Added/Updated | 194 | 2884 | 0.07 |
| gpt-5.5 | auto | ALL | Line Comment Deleted | 409 | 2884 | 0.14 |
| gpt-5.5 | auto | ALL | Blank-Line Separation Added | 2638 | 2884 | 0.91 |
| gpt-5.5 | auto | ALL | Extract Variable | 443 | 2884 | 0.15 |
| gpt-5.5 | auto | ALL | Inline Variable | 12 | 2884 | 0.00 |
| gpt-5.5 | auto | ALL | Change Variable Type | 6 | 2884 | 0.00 |
| gpt-5.5 | auto | ALL | Add Variable Modifier | 216 | 2884 | 0.07 |
| gpt-5.5 | auto | ALL | Parameterize Variable | 11 | 2884 | 0.00 |
| gpt-5.5 | auto | ALL | Split Variable | 1 | 2884 | 0.00 |
| gpt-5.5 | auto | ALL | Extract Attribute | 428 | 2884 | 0.15 |
| gpt-5.5 | auto | ALL | Replace Variable With Attribute | 9 | 2884 | 0.00 |
| gpt-5.5 | auto | ALL | Extract Method | 27 | 2884 | 0.01 |
| gpt-5.5 | auto | ALL | Split Method | 1 | 2884 | 0.00 |
| gpt-5.5 | auto | ALL | Add Method Annotation | 5 | 2884 | 0.00 |
| gpt-5.5 | auto | ALL | Remove Method Annotation | 1 | 2884 | 0.00 |
| gpt-5.5 | auto | ALL | Remove Thrown Exception Type | 1 | 2884 | 0.00 |
| gpt-5.5 | auto | ALL | Add Variable Annotation | 3 | 2884 | 0.00 |
| gpt-5.5 | auto | testcases | Variable Rename | 2694 | 2756 | 0.98 |
| gpt-5.5 | auto | testcases | Method Rename | 193 | 2756 | 0.07 |
| gpt-5.5 | auto | testcases | Line Comment Added/Updated | 186 | 2756 | 0.07 |
| gpt-5.5 | auto | testcases | Line Comment Deleted | 348 | 2756 | 0.13 |
| gpt-5.5 | auto | testcases | Blank-Line Separation Added | 2638 | 2756 | 0.96 |
| gpt-5.5 | auto | testcases | Extract Variable | 443 | 2756 | 0.16 |
| gpt-5.5 | auto | testcases | Inline Variable | 11 | 2756 | 0.00 |
| gpt-5.5 | auto | testcases | Change Variable Type | 6 | 2756 | 0.00 |
| gpt-5.5 | auto | testcases | Add Variable Modifier | 215 | 2756 | 0.08 |
| gpt-5.5 | auto | testcases | Parameterize Variable | 7 | 2756 | 0.00 |
| gpt-5.5 | auto | testcases | Split Variable | 1 | 2756 | 0.00 |
| gpt-5.5 | auto | testcases | Extract Attribute | 402 | 2756 | 0.15 |
| gpt-5.5 | auto | testcases | Replace Variable With Attribute | 9 | 2756 | 0.00 |
| gpt-5.5 | auto | testcases | Extract Method | 10 | 2756 | 0.00 |
| gpt-5.5 | auto | testcases | Add Method Annotation | 3 | 2756 | 0.00 |
| gpt-5.5 | auto | testcases | Add Variable Annotation | 3 | 2756 | 0.00 |
| gpt-5.5 | auto | testsuites | Variable Rename | 126 | 128 | 0.98 |
| gpt-5.5 | auto | testsuites | Method Rename | 103 | 128 | 0.80 |
| gpt-5.5 | auto | testsuites | Line Comment Added/Updated | 8 | 128 | 0.06 |
| gpt-5.5 | auto | testsuites | Line Comment Deleted | 61 | 128 | 0.48 |
| gpt-5.5 | auto | testsuites | Inline Variable | 1 | 128 | 0.01 |
| gpt-5.5 | auto | testsuites | Add Variable Modifier | 1 | 128 | 0.01 |
| gpt-5.5 | auto | testsuites | Parameterize Variable | 4 | 128 | 0.03 |
| gpt-5.5 | auto | testsuites | Extract Attribute | 26 | 128 | 0.20 |
| gpt-5.5 | auto | testsuites | Extract Method | 17 | 128 | 0.13 |
| gpt-5.5 | auto | testsuites | Split Method | 1 | 128 | 0.01 |
| gpt-5.5 | auto | testsuites | Add Method Annotation | 2 | 128 | 0.02 |
| gpt-5.5 | auto | testsuites | Remove Method Annotation | 1 | 128 | 0.01 |
| gpt-5.5 | auto | testsuites | Remove Thrown Exception Type | 1 | 128 | 0.01 |
| gpt-5.5 | manual | ALL | Variable Rename | 1290 | 2723 | 0.47 |
| gpt-5.5 | manual | ALL | Method Rename | 219 | 2723 | 0.08 |
| gpt-5.5 | manual | ALL | Line Comment Added/Updated | 203 | 2723 | 0.07 |
| gpt-5.5 | manual | ALL | Javadoc Added/Updated | 165 | 2723 | 0.06 |
| gpt-5.5 | manual | ALL | Block Comment Added/Updated | 6 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Line Comment Deleted | 1061 | 2723 | 0.39 |
| gpt-5.5 | manual | ALL | Javadoc Deleted | 219 | 2723 | 0.08 |
| gpt-5.5 | manual | ALL | Block Comment Deleted | 27 | 2723 | 0.01 |
| gpt-5.5 | manual | ALL | Blank-Line Separation Added | 1645 | 2723 | 0.60 |
| gpt-5.5 | manual | ALL | Attribute Rename | 211 | 2723 | 0.08 |
| gpt-5.5 | manual | ALL | Class Rename | 7 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Extract Variable | 395 | 2723 | 0.15 |
| gpt-5.5 | manual | ALL | Inline Variable | 62 | 2723 | 0.02 |
| gpt-5.5 | manual | ALL | Change Variable Type | 87 | 2723 | 0.03 |
| gpt-5.5 | manual | ALL | Add Variable Modifier | 63 | 2723 | 0.02 |
| gpt-5.5 | manual | ALL | Remove Variable Modifier | 5 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Parameterize Variable | 102 | 2723 | 0.04 |
| gpt-5.5 | manual | ALL | Merge Variable | 1 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Extract Attribute | 522 | 2723 | 0.19 |
| gpt-5.5 | manual | ALL | Inline Attribute | 13 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Change Attribute Type | 43 | 2723 | 0.02 |
| gpt-5.5 | manual | ALL | Add Attribute Modifier | 59 | 2723 | 0.02 |
| gpt-5.5 | manual | ALL | Remove Attribute Modifier | 4 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Parameterize Attribute | 12 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Move Attribute | 7 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Replace Variable With Attribute | 180 | 2723 | 0.07 |
| gpt-5.5 | manual | ALL | Replace Attribute With Variable | 9 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Extract Method | 435 | 2723 | 0.16 |
| gpt-5.5 | manual | ALL | Inline Method | 21 | 2723 | 0.01 |
| gpt-5.5 | manual | ALL | Split Method | 1 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Merge Method | 1 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Change Method Access Modifier | 85 | 2723 | 0.03 |
| gpt-5.5 | manual | ALL | Add Method Modifier | 10 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Remove Method Modifier | 23 | 2723 | 0.01 |
| gpt-5.5 | manual | ALL | Add Method Annotation | 12 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Remove Method Annotation | 17 | 2723 | 0.01 |
| gpt-5.5 | manual | ALL | Modify Method Annotation | 36 | 2723 | 0.01 |
| gpt-5.5 | manual | ALL | Add Parameter | 14 | 2723 | 0.01 |
| gpt-5.5 | manual | ALL | Remove Parameter | 16 | 2723 | 0.01 |
| gpt-5.5 | manual | ALL | Split Parameter | 1 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Localize Parameter | 6 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Change Parameter Type | 12 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Add Parameter Modifier | 20 | 2723 | 0.01 |
| gpt-5.5 | manual | ALL | Change Return Type | 49 | 2723 | 0.02 |
| gpt-5.5 | manual | ALL | Add Thrown Exception Type | 2 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Remove Thrown Exception Type | 53 | 2723 | 0.02 |
| gpt-5.5 | manual | ALL | Change Thrown Exception Type | 3 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Assert Throws | 16 | 2723 | 0.01 |
| gpt-5.5 | manual | ALL | Extract Class | 7 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Move Class | 1 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Change Class Access Modifier | 3 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Remove Class Annotation | 113 | 2723 | 0.04 |
| gpt-5.5 | manual | ALL | Modify Class Annotation | 86 | 2723 | 0.03 |
| gpt-5.5 | manual | ALL | Remove Variable Annotation | 10 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Add Attribute Annotation | 2 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Modify Attribute Annotation | 1 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Move Code | 6 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Invert Condition | 1 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Merge Conditional | 2 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Replace Anonymous With Class | 3 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Parameterize Test | 3 | 2723 | 0.00 |
| gpt-5.5 | manual | ALL | Change Attribute Access Modifier | 69 | 2723 | 0.03 |
| gpt-5.5 | manual | testcases | Variable Rename | 1218 | 2595 | 0.47 |
| gpt-5.5 | manual | testcases | Method Rename | 203 | 2595 | 0.08 |
| gpt-5.5 | manual | testcases | Line Comment Added/Updated | 177 | 2595 | 0.07 |
| gpt-5.5 | manual | testcases | Javadoc Added/Updated | 125 | 2595 | 0.05 |
| gpt-5.5 | manual | testcases | Block Comment Added/Updated | 3 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Line Comment Deleted | 993 | 2595 | 0.38 |
| gpt-5.5 | manual | testcases | Javadoc Deleted | 208 | 2595 | 0.08 |
| gpt-5.5 | manual | testcases | Block Comment Deleted | 20 | 2595 | 0.01 |
| gpt-5.5 | manual | testcases | Blank-Line Separation Added | 1645 | 2595 | 0.63 |
| gpt-5.5 | manual | testcases | Attribute Rename | 201 | 2595 | 0.08 |
| gpt-5.5 | manual | testcases | Class Rename | 4 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Extract Variable | 383 | 2595 | 0.15 |
| gpt-5.5 | manual | testcases | Inline Variable | 48 | 2595 | 0.02 |
| gpt-5.5 | manual | testcases | Change Variable Type | 83 | 2595 | 0.03 |
| gpt-5.5 | manual | testcases | Add Variable Modifier | 53 | 2595 | 0.02 |
| gpt-5.5 | manual | testcases | Remove Variable Modifier | 2 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Parameterize Variable | 73 | 2595 | 0.03 |
| gpt-5.5 | manual | testcases | Merge Variable | 1 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Extract Attribute | 470 | 2595 | 0.18 |
| gpt-5.5 | manual | testcases | Inline Attribute | 13 | 2595 | 0.01 |
| gpt-5.5 | manual | testcases | Change Attribute Type | 42 | 2595 | 0.02 |
| gpt-5.5 | manual | testcases | Add Attribute Modifier | 51 | 2595 | 0.02 |
| gpt-5.5 | manual | testcases | Remove Attribute Modifier | 4 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Parameterize Attribute | 5 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Move Attribute | 6 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Replace Variable With Attribute | 162 | 2595 | 0.06 |
| gpt-5.5 | manual | testcases | Replace Attribute With Variable | 7 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Extract Method | 357 | 2595 | 0.14 |
| gpt-5.5 | manual | testcases | Inline Method | 21 | 2595 | 0.01 |
| gpt-5.5 | manual | testcases | Split Method | 1 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Merge Method | 1 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Change Method Access Modifier | 85 | 2595 | 0.03 |
| gpt-5.5 | manual | testcases | Add Method Modifier | 7 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Remove Method Modifier | 23 | 2595 | 0.01 |
| gpt-5.5 | manual | testcases | Add Method Annotation | 10 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Remove Method Annotation | 17 | 2595 | 0.01 |
| gpt-5.5 | manual | testcases | Modify Method Annotation | 31 | 2595 | 0.01 |
| gpt-5.5 | manual | testcases | Add Parameter | 13 | 2595 | 0.01 |
| gpt-5.5 | manual | testcases | Remove Parameter | 14 | 2595 | 0.01 |
| gpt-5.5 | manual | testcases | Split Parameter | 1 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Localize Parameter | 6 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Change Parameter Type | 12 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Add Parameter Modifier | 12 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Change Return Type | 48 | 2595 | 0.02 |
| gpt-5.5 | manual | testcases | Add Thrown Exception Type | 1 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Remove Thrown Exception Type | 53 | 2595 | 0.02 |
| gpt-5.5 | manual | testcases | Change Thrown Exception Type | 2 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Assert Throws | 13 | 2595 | 0.01 |
| gpt-5.5 | manual | testcases | Extract Class | 6 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Move Class | 1 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Change Class Access Modifier | 3 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Remove Class Annotation | 113 | 2595 | 0.04 |
| gpt-5.5 | manual | testcases | Modify Class Annotation | 86 | 2595 | 0.03 |
| gpt-5.5 | manual | testcases | Remove Variable Annotation | 9 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Add Attribute Annotation | 2 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Move Code | 6 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Invert Condition | 1 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Merge Conditional | 2 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Replace Anonymous With Class | 2 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Parameterize Test | 3 | 2595 | 0.00 |
| gpt-5.5 | manual | testcases | Change Attribute Access Modifier | 63 | 2595 | 0.02 |
| gpt-5.5 | manual | testsuites | Variable Rename | 72 | 128 | 0.56 |
| gpt-5.5 | manual | testsuites | Method Rename | 16 | 128 | 0.12 |
| gpt-5.5 | manual | testsuites | Line Comment Added/Updated | 26 | 128 | 0.20 |
| gpt-5.5 | manual | testsuites | Javadoc Added/Updated | 40 | 128 | 0.31 |
| gpt-5.5 | manual | testsuites | Block Comment Added/Updated | 3 | 128 | 0.02 |
| gpt-5.5 | manual | testsuites | Line Comment Deleted | 68 | 128 | 0.53 |
| gpt-5.5 | manual | testsuites | Javadoc Deleted | 11 | 128 | 0.09 |
| gpt-5.5 | manual | testsuites | Block Comment Deleted | 7 | 128 | 0.05 |
| gpt-5.5 | manual | testsuites | Attribute Rename | 10 | 128 | 0.08 |
| gpt-5.5 | manual | testsuites | Class Rename | 3 | 128 | 0.02 |
| gpt-5.5 | manual | testsuites | Extract Variable | 12 | 128 | 0.09 |
| gpt-5.5 | manual | testsuites | Inline Variable | 14 | 128 | 0.11 |
| gpt-5.5 | manual | testsuites | Change Variable Type | 4 | 128 | 0.03 |
| gpt-5.5 | manual | testsuites | Add Variable Modifier | 10 | 128 | 0.08 |
| gpt-5.5 | manual | testsuites | Remove Variable Modifier | 3 | 128 | 0.02 |
| gpt-5.5 | manual | testsuites | Parameterize Variable | 29 | 128 | 0.23 |
| gpt-5.5 | manual | testsuites | Extract Attribute | 52 | 128 | 0.41 |
| gpt-5.5 | manual | testsuites | Change Attribute Type | 1 | 128 | 0.01 |
| gpt-5.5 | manual | testsuites | Add Attribute Modifier | 8 | 128 | 0.06 |
| gpt-5.5 | manual | testsuites | Parameterize Attribute | 7 | 128 | 0.05 |
| gpt-5.5 | manual | testsuites | Move Attribute | 1 | 128 | 0.01 |
| gpt-5.5 | manual | testsuites | Replace Variable With Attribute | 18 | 128 | 0.14 |
| gpt-5.5 | manual | testsuites | Replace Attribute With Variable | 2 | 128 | 0.02 |
| gpt-5.5 | manual | testsuites | Extract Method | 78 | 128 | 0.61 |
| gpt-5.5 | manual | testsuites | Add Method Modifier | 3 | 128 | 0.02 |
| gpt-5.5 | manual | testsuites | Add Method Annotation | 2 | 128 | 0.02 |
| gpt-5.5 | manual | testsuites | Modify Method Annotation | 5 | 128 | 0.04 |
| gpt-5.5 | manual | testsuites | Add Parameter | 1 | 128 | 0.01 |
| gpt-5.5 | manual | testsuites | Remove Parameter | 2 | 128 | 0.02 |
| gpt-5.5 | manual | testsuites | Add Parameter Modifier | 8 | 128 | 0.06 |
| gpt-5.5 | manual | testsuites | Change Return Type | 1 | 128 | 0.01 |
| gpt-5.5 | manual | testsuites | Add Thrown Exception Type | 1 | 128 | 0.01 |
| gpt-5.5 | manual | testsuites | Change Thrown Exception Type | 1 | 128 | 0.01 |
| gpt-5.5 | manual | testsuites | Assert Throws | 3 | 128 | 0.02 |
| gpt-5.5 | manual | testsuites | Extract Class | 1 | 128 | 0.01 |
| gpt-5.5 | manual | testsuites | Remove Variable Annotation | 1 | 128 | 0.01 |
| gpt-5.5 | manual | testsuites | Modify Attribute Annotation | 1 | 128 | 0.01 |
| gpt-5.5 | manual | testsuites | Replace Anonymous With Class | 1 | 128 | 0.01 |
| gpt-5.5 | manual | testsuites | Change Attribute Access Modifier | 6 | 128 | 0.05 |
| opus-4.8 | auto | ALL | Variable Rename | 2771 | 2889 | 0.96 |
| opus-4.8 | auto | ALL | Method Rename | 2711 | 2889 | 0.94 |
| opus-4.8 | auto | ALL | Line Comment Added/Updated | 1175 | 2889 | 0.41 |
| opus-4.8 | auto | ALL | Javadoc Added/Updated | 2856 | 2889 | 0.99 |
| opus-4.8 | auto | ALL | Block Comment Added/Updated | 78 | 2889 | 0.03 |
| opus-4.8 | auto | ALL | Line Comment Deleted | 414 | 2889 | 0.14 |
| opus-4.8 | auto | ALL | Blank-Line Separation Added | 2690 | 2889 | 0.93 |
| opus-4.8 | auto | ALL | Extract Variable | 643 | 2889 | 0.22 |
| opus-4.8 | auto | ALL | Inline Variable | 540 | 2889 | 0.19 |
| opus-4.8 | auto | ALL | Change Variable Type | 152 | 2889 | 0.05 |
| opus-4.8 | auto | ALL | Add Variable Modifier | 45 | 2889 | 0.02 |
| opus-4.8 | auto | ALL | Parameterize Variable | 3 | 2889 | 0.00 |
| opus-4.8 | auto | ALL | Split Variable | 2 | 2889 | 0.00 |
| opus-4.8 | auto | ALL | Extract Attribute | 42 | 2889 | 0.01 |
| opus-4.8 | auto | ALL | Replace Variable With Attribute | 3 | 2889 | 0.00 |
| opus-4.8 | auto | ALL | Extract Method | 11 | 2889 | 0.00 |
| opus-4.8 | auto | ALL | Modify Method Annotation | 1 | 2889 | 0.00 |
| opus-4.8 | auto | ALL | Add Parameter Modifier | 1 | 2889 | 0.00 |
| opus-4.8 | auto | ALL | Remove Thrown Exception Type | 3 | 2889 | 0.00 |
| opus-4.8 | auto | ALL | Add Variable Annotation | 17 | 2889 | 0.01 |
| opus-4.8 | auto | testcases | Variable Rename | 2642 | 2759 | 0.96 |
| opus-4.8 | auto | testcases | Method Rename | 2584 | 2759 | 0.94 |
| opus-4.8 | auto | testcases | Line Comment Added/Updated | 1082 | 2759 | 0.39 |
| opus-4.8 | auto | testcases | Javadoc Added/Updated | 2754 | 2759 | 1.00 |
| opus-4.8 | auto | testcases | Block Comment Added/Updated | 14 | 2759 | 0.01 |
| opus-4.8 | auto | testcases | Line Comment Deleted | 348 | 2759 | 0.13 |
| opus-4.8 | auto | testcases | Blank-Line Separation Added | 2690 | 2759 | 0.97 |
| opus-4.8 | auto | testcases | Extract Variable | 639 | 2759 | 0.23 |
| opus-4.8 | auto | testcases | Inline Variable | 479 | 2759 | 0.17 |
| opus-4.8 | auto | testcases | Change Variable Type | 146 | 2759 | 0.05 |
| opus-4.8 | auto | testcases | Add Variable Modifier | 44 | 2759 | 0.02 |
| opus-4.8 | auto | testcases | Split Variable | 2 | 2759 | 0.00 |
| opus-4.8 | auto | testcases | Extract Attribute | 15 | 2759 | 0.01 |
| opus-4.8 | auto | testcases | Extract Method | 1 | 2759 | 0.00 |
| opus-4.8 | auto | testcases | Remove Thrown Exception Type | 3 | 2759 | 0.00 |
| opus-4.8 | auto | testcases | Add Variable Annotation | 14 | 2759 | 0.01 |
| opus-4.8 | auto | testsuites | Variable Rename | 129 | 130 | 0.99 |
| opus-4.8 | auto | testsuites | Method Rename | 127 | 130 | 0.98 |
| opus-4.8 | auto | testsuites | Line Comment Added/Updated | 93 | 130 | 0.72 |
| opus-4.8 | auto | testsuites | Javadoc Added/Updated | 102 | 130 | 0.78 |
| opus-4.8 | auto | testsuites | Block Comment Added/Updated | 64 | 130 | 0.49 |
| opus-4.8 | auto | testsuites | Line Comment Deleted | 66 | 130 | 0.51 |
| opus-4.8 | auto | testsuites | Extract Variable | 4 | 130 | 0.03 |
| opus-4.8 | auto | testsuites | Inline Variable | 61 | 130 | 0.47 |
| opus-4.8 | auto | testsuites | Change Variable Type | 6 | 130 | 0.05 |
| opus-4.8 | auto | testsuites | Add Variable Modifier | 1 | 130 | 0.01 |
| opus-4.8 | auto | testsuites | Parameterize Variable | 3 | 130 | 0.02 |
| opus-4.8 | auto | testsuites | Extract Attribute | 27 | 130 | 0.21 |
| opus-4.8 | auto | testsuites | Replace Variable With Attribute | 3 | 130 | 0.02 |
| opus-4.8 | auto | testsuites | Extract Method | 10 | 130 | 0.08 |
| opus-4.8 | auto | testsuites | Modify Method Annotation | 1 | 130 | 0.01 |
| opus-4.8 | auto | testsuites | Add Parameter Modifier | 1 | 130 | 0.01 |
| opus-4.8 | auto | testsuites | Add Variable Annotation | 3 | 130 | 0.02 |
| opus-4.8 | manual | ALL | Variable Rename | 1484 | 2920 | 0.51 |
| opus-4.8 | manual | ALL | Method Rename | 879 | 2920 | 0.30 |
| opus-4.8 | manual | ALL | Line Comment Added/Updated | 2127 | 2920 | 0.73 |
| opus-4.8 | manual | ALL | Javadoc Added/Updated | 2864 | 2920 | 0.98 |
| opus-4.8 | manual | ALL | Block Comment Added/Updated | 8 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Line Comment Deleted | 864 | 2920 | 0.30 |
| opus-4.8 | manual | ALL | Javadoc Deleted | 84 | 2920 | 0.03 |
| opus-4.8 | manual | ALL | Block Comment Deleted | 6 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Blank-Line Separation Added | 2230 | 2920 | 0.76 |
| opus-4.8 | manual | ALL | Attribute Rename | 279 | 2920 | 0.10 |
| opus-4.8 | manual | ALL | Class Rename | 5 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Extract Variable | 1012 | 2920 | 0.35 |
| opus-4.8 | manual | ALL | Inline Variable | 155 | 2920 | 0.05 |
| opus-4.8 | manual | ALL | Change Variable Type | 114 | 2920 | 0.04 |
| opus-4.8 | manual | ALL | Add Variable Modifier | 76 | 2920 | 0.03 |
| opus-4.8 | manual | ALL | Remove Variable Modifier | 10 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Parameterize Variable | 35 | 2920 | 0.01 |
| opus-4.8 | manual | ALL | Merge Variable | 2 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Extract Attribute | 239 | 2920 | 0.08 |
| opus-4.8 | manual | ALL | Inline Attribute | 32 | 2920 | 0.01 |
| opus-4.8 | manual | ALL | Change Attribute Type | 40 | 2920 | 0.01 |
| opus-4.8 | manual | ALL | Add Attribute Modifier | 71 | 2920 | 0.02 |
| opus-4.8 | manual | ALL | Remove Attribute Modifier | 14 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Parameterize Attribute | 1 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Move Attribute | 5 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Replace Variable With Attribute | 97 | 2920 | 0.03 |
| opus-4.8 | manual | ALL | Replace Attribute With Variable | 22 | 2920 | 0.01 |
| opus-4.8 | manual | ALL | Extract Method | 129 | 2920 | 0.04 |
| opus-4.8 | manual | ALL | Inline Method | 134 | 2920 | 0.05 |
| opus-4.8 | manual | ALL | Move And Rename Method | 1 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Extract And Move Method | 1 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Split Method | 1 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Merge Method | 6 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Change Method Access Modifier | 140 | 2920 | 0.05 |
| opus-4.8 | manual | ALL | Add Method Modifier | 25 | 2920 | 0.01 |
| opus-4.8 | manual | ALL | Remove Method Modifier | 26 | 2920 | 0.01 |
| opus-4.8 | manual | ALL | Add Method Annotation | 19 | 2920 | 0.01 |
| opus-4.8 | manual | ALL | Remove Method Annotation | 29 | 2920 | 0.01 |
| opus-4.8 | manual | ALL | Modify Method Annotation | 64 | 2920 | 0.02 |
| opus-4.8 | manual | ALL | Add Parameter | 20 | 2920 | 0.01 |
| opus-4.8 | manual | ALL | Remove Parameter | 46 | 2920 | 0.02 |
| opus-4.8 | manual | ALL | Reorder Parameter | 7 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Localize Parameter | 25 | 2920 | 0.01 |
| opus-4.8 | manual | ALL | Change Parameter Type | 8 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Add Parameter Modifier | 9 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Change Return Type | 49 | 2920 | 0.02 |
| opus-4.8 | manual | ALL | Add Thrown Exception Type | 1 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Remove Thrown Exception Type | 96 | 2920 | 0.03 |
| opus-4.8 | manual | ALL | Change Thrown Exception Type | 1 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Assert Throws | 22 | 2920 | 0.01 |
| opus-4.8 | manual | ALL | Extract Class | 6 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Move Class | 9 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Change Class Access Modifier | 5 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Remove Class Annotation | 107 | 2920 | 0.04 |
| opus-4.8 | manual | ALL | Remove Variable Annotation | 2 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Move Code | 18 | 2920 | 0.01 |
| opus-4.8 | manual | ALL | Invert Condition | 1 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Replace Generic With Diamond | 2 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Try With Resources | 1 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Parameterize Test | 2 | 2920 | 0.00 |
| opus-4.8 | manual | ALL | Change Attribute Access Modifier | 99 | 2920 | 0.03 |
| opus-4.8 | manual | testcases | Variable Rename | 1386 | 2786 | 0.50 |
| opus-4.8 | manual | testcases | Method Rename | 846 | 2786 | 0.30 |
| opus-4.8 | manual | testcases | Line Comment Added/Updated | 1997 | 2786 | 0.72 |
| opus-4.8 | manual | testcases | Javadoc Added/Updated | 2736 | 2786 | 0.98 |
| opus-4.8 | manual | testcases | Block Comment Added/Updated | 3 | 2786 | 0.00 |
| opus-4.8 | manual | testcases | Line Comment Deleted | 822 | 2786 | 0.29 |
| opus-4.8 | manual | testcases | Javadoc Deleted | 81 | 2786 | 0.03 |
| opus-4.8 | manual | testcases | Block Comment Deleted | 5 | 2786 | 0.00 |
| opus-4.8 | manual | testcases | Blank-Line Separation Added | 2230 | 2786 | 0.80 |
| opus-4.8 | manual | testcases | Attribute Rename | 264 | 2786 | 0.09 |
| opus-4.8 | manual | testcases | Class Rename | 4 | 2786 | 0.00 |
| opus-4.8 | manual | testcases | Extract Variable | 979 | 2786 | 0.35 |
| opus-4.8 | manual | testcases | Inline Variable | 119 | 2786 | 0.04 |
| opus-4.8 | manual | testcases | Change Variable Type | 107 | 2786 | 0.04 |
| opus-4.8 | manual | testcases | Add Variable Modifier | 65 | 2786 | 0.02 |
| opus-4.8 | manual | testcases | Remove Variable Modifier | 6 | 2786 | 0.00 |
| opus-4.8 | manual | testcases | Parameterize Variable | 29 | 2786 | 0.01 |
| opus-4.8 | manual | testcases | Merge Variable | 1 | 2786 | 0.00 |
| opus-4.8 | manual | testcases | Extract Attribute | 199 | 2786 | 0.07 |
| opus-4.8 | manual | testcases | Inline Attribute | 31 | 2786 | 0.01 |
| opus-4.8 | manual | testcases | Change Attribute Type | 39 | 2786 | 0.01 |
| opus-4.8 | manual | testcases | Add Attribute Modifier | 67 | 2786 | 0.02 |
| opus-4.8 | manual | testcases | Remove Attribute Modifier | 14 | 2786 | 0.01 |
| opus-4.8 | manual | testcases | Move Attribute | 4 | 2786 | 0.00 |
| opus-4.8 | manual | testcases | Replace Variable With Attribute | 90 | 2786 | 0.03 |
| opus-4.8 | manual | testcases | Replace Attribute With Variable | 21 | 2786 | 0.01 |
| opus-4.8 | manual | testcases | Extract Method | 98 | 2786 | 0.04 |
| opus-4.8 | manual | testcases | Inline Method | 133 | 2786 | 0.05 |
| opus-4.8 | manual | testcases | Split Method | 1 | 2786 | 0.00 |
| opus-4.8 | manual | testcases | Merge Method | 6 | 2786 | 0.00 |
| opus-4.8 | manual | testcases | Change Method Access Modifier | 136 | 2786 | 0.05 |
| opus-4.8 | manual | testcases | Add Method Modifier | 23 | 2786 | 0.01 |
| opus-4.8 | manual | testcases | Remove Method Modifier | 25 | 2786 | 0.01 |
| opus-4.8 | manual | testcases | Add Method Annotation | 15 | 2786 | 0.01 |
| opus-4.8 | manual | testcases | Remove Method Annotation | 25 | 2786 | 0.01 |
| opus-4.8 | manual | testcases | Modify Method Annotation | 60 | 2786 | 0.02 |
| opus-4.8 | manual | testcases | Add Parameter | 17 | 2786 | 0.01 |
| opus-4.8 | manual | testcases | Remove Parameter | 44 | 2786 | 0.02 |
| opus-4.8 | manual | testcases | Reorder Parameter | 6 | 2786 | 0.00 |
| opus-4.8 | manual | testcases | Localize Parameter | 25 | 2786 | 0.01 |
| opus-4.8 | manual | testcases | Change Parameter Type | 8 | 2786 | 0.00 |
| opus-4.8 | manual | testcases | Add Parameter Modifier | 7 | 2786 | 0.00 |
| opus-4.8 | manual | testcases | Change Return Type | 48 | 2786 | 0.02 |
| opus-4.8 | manual | testcases | Add Thrown Exception Type | 1 | 2786 | 0.00 |
| opus-4.8 | manual | testcases | Remove Thrown Exception Type | 94 | 2786 | 0.03 |
| opus-4.8 | manual | testcases | Change Thrown Exception Type | 1 | 2786 | 0.00 |
| opus-4.8 | manual | testcases | Assert Throws | 17 | 2786 | 0.01 |
| opus-4.8 | manual | testcases | Extract Class | 4 | 2786 | 0.00 |
| opus-4.8 | manual | testcases | Move Class | 9 | 2786 | 0.00 |
| opus-4.8 | manual | testcases | Change Class Access Modifier | 5 | 2786 | 0.00 |
| opus-4.8 | manual | testcases | Remove Class Annotation | 107 | 2786 | 0.04 |
| opus-4.8 | manual | testcases | Remove Variable Annotation | 2 | 2786 | 0.00 |
| opus-4.8 | manual | testcases | Move Code | 18 | 2786 | 0.01 |
| opus-4.8 | manual | testcases | Replace Generic With Diamond | 1 | 2786 | 0.00 |
| opus-4.8 | manual | testcases | Parameterize Test | 1 | 2786 | 0.00 |
| opus-4.8 | manual | testcases | Change Attribute Access Modifier | 94 | 2786 | 0.03 |
| opus-4.8 | manual | testsuites | Variable Rename | 98 | 134 | 0.73 |
| opus-4.8 | manual | testsuites | Method Rename | 33 | 134 | 0.25 |
| opus-4.8 | manual | testsuites | Line Comment Added/Updated | 130 | 134 | 0.97 |
| opus-4.8 | manual | testsuites | Javadoc Added/Updated | 128 | 134 | 0.96 |
| opus-4.8 | manual | testsuites | Block Comment Added/Updated | 5 | 134 | 0.04 |
| opus-4.8 | manual | testsuites | Line Comment Deleted | 42 | 134 | 0.31 |
| opus-4.8 | manual | testsuites | Javadoc Deleted | 3 | 134 | 0.02 |
| opus-4.8 | manual | testsuites | Block Comment Deleted | 1 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Attribute Rename | 15 | 134 | 0.11 |
| opus-4.8 | manual | testsuites | Class Rename | 1 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Extract Variable | 33 | 134 | 0.25 |
| opus-4.8 | manual | testsuites | Inline Variable | 36 | 134 | 0.27 |
| opus-4.8 | manual | testsuites | Change Variable Type | 7 | 134 | 0.05 |
| opus-4.8 | manual | testsuites | Add Variable Modifier | 11 | 134 | 0.08 |
| opus-4.8 | manual | testsuites | Remove Variable Modifier | 4 | 134 | 0.03 |
| opus-4.8 | manual | testsuites | Parameterize Variable | 6 | 134 | 0.04 |
| opus-4.8 | manual | testsuites | Merge Variable | 1 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Extract Attribute | 40 | 134 | 0.30 |
| opus-4.8 | manual | testsuites | Inline Attribute | 1 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Change Attribute Type | 1 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Add Attribute Modifier | 4 | 134 | 0.03 |
| opus-4.8 | manual | testsuites | Parameterize Attribute | 1 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Move Attribute | 1 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Replace Variable With Attribute | 7 | 134 | 0.05 |
| opus-4.8 | manual | testsuites | Replace Attribute With Variable | 1 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Extract Method | 31 | 134 | 0.23 |
| opus-4.8 | manual | testsuites | Inline Method | 1 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Move And Rename Method | 1 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Extract And Move Method | 1 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Change Method Access Modifier | 4 | 134 | 0.03 |
| opus-4.8 | manual | testsuites | Add Method Modifier | 2 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Remove Method Modifier | 1 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Add Method Annotation | 4 | 134 | 0.03 |
| opus-4.8 | manual | testsuites | Remove Method Annotation | 4 | 134 | 0.03 |
| opus-4.8 | manual | testsuites | Modify Method Annotation | 4 | 134 | 0.03 |
| opus-4.8 | manual | testsuites | Add Parameter | 3 | 134 | 0.02 |
| opus-4.8 | manual | testsuites | Remove Parameter | 2 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Reorder Parameter | 1 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Add Parameter Modifier | 2 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Change Return Type | 1 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Remove Thrown Exception Type | 2 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Assert Throws | 5 | 134 | 0.04 |
| opus-4.8 | manual | testsuites | Extract Class | 2 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Invert Condition | 1 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Replace Generic With Diamond | 1 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Try With Resources | 1 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Parameterize Test | 1 | 134 | 0.01 |
| opus-4.8 | manual | testsuites | Change Attribute Access Modifier | 5 | 134 | 0.04 |
| sonnet-4.6 | auto | ALL | Variable Rename | 2704 | 2870 | 0.94 |
| sonnet-4.6 | auto | ALL | Method Rename | 1760 | 2870 | 0.61 |
| sonnet-4.6 | auto | ALL | Line Comment Added/Updated | 2037 | 2870 | 0.71 |
| sonnet-4.6 | auto | ALL | Javadoc Added/Updated | 1326 | 2870 | 0.46 |
| sonnet-4.6 | auto | ALL | Block Comment Added/Updated | 53 | 2870 | 0.02 |
| sonnet-4.6 | auto | ALL | Line Comment Deleted | 383 | 2870 | 0.13 |
| sonnet-4.6 | auto | ALL | Block Comment Deleted | 14 | 2870 | 0.00 |
| sonnet-4.6 | auto | ALL | Blank-Line Separation Added | 1732 | 2870 | 0.60 |
| sonnet-4.6 | auto | ALL | Extract Variable | 319 | 2870 | 0.11 |
| sonnet-4.6 | auto | ALL | Inline Variable | 334 | 2870 | 0.12 |
| sonnet-4.6 | auto | ALL | Change Variable Type | 45 | 2870 | 0.02 |
| sonnet-4.6 | auto | ALL | Add Variable Modifier | 1 | 2870 | 0.00 |
| sonnet-4.6 | auto | ALL | Parameterize Variable | 1 | 2870 | 0.00 |
| sonnet-4.6 | auto | ALL | Merge Variable | 2 | 2870 | 0.00 |
| sonnet-4.6 | auto | ALL | Split Variable | 3 | 2870 | 0.00 |
| sonnet-4.6 | auto | ALL | Extract Attribute | 141 | 2870 | 0.05 |
| sonnet-4.6 | auto | ALL | Replace Variable With Attribute | 4 | 2870 | 0.00 |
| sonnet-4.6 | auto | ALL | Extract Method | 3 | 2870 | 0.00 |
| sonnet-4.6 | auto | ALL | Add Method Annotation | 1 | 2870 | 0.00 |
| sonnet-4.6 | auto | ALL | Remove Thrown Exception Type | 1 | 2870 | 0.00 |
| sonnet-4.6 | auto | ALL | Add Variable Annotation | 4 | 2870 | 0.00 |
| sonnet-4.6 | auto | ALL | Replace Generic With Diamond | 8 | 2870 | 0.00 |
| sonnet-4.6 | auto | ALL | Extract Fixture | 2 | 2870 | 0.00 |
| sonnet-4.6 | auto | testcases | Variable Rename | 2578 | 2744 | 0.94 |
| sonnet-4.6 | auto | testcases | Method Rename | 1649 | 2744 | 0.60 |
| sonnet-4.6 | auto | testcases | Line Comment Added/Updated | 1932 | 2744 | 0.70 |
| sonnet-4.6 | auto | testcases | Javadoc Added/Updated | 1282 | 2744 | 0.47 |
| sonnet-4.6 | auto | testcases | Block Comment Added/Updated | 30 | 2744 | 0.01 |
| sonnet-4.6 | auto | testcases | Line Comment Deleted | 327 | 2744 | 0.12 |
| sonnet-4.6 | auto | testcases | Blank-Line Separation Added | 1732 | 2744 | 0.63 |
| sonnet-4.6 | auto | testcases | Extract Variable | 314 | 2744 | 0.11 |
| sonnet-4.6 | auto | testcases | Inline Variable | 289 | 2744 | 0.11 |
| sonnet-4.6 | auto | testcases | Change Variable Type | 42 | 2744 | 0.02 |
| sonnet-4.6 | auto | testcases | Add Variable Modifier | 1 | 2744 | 0.00 |
| sonnet-4.6 | auto | testcases | Merge Variable | 1 | 2744 | 0.00 |
| sonnet-4.6 | auto | testcases | Split Variable | 3 | 2744 | 0.00 |
| sonnet-4.6 | auto | testcases | Extract Attribute | 133 | 2744 | 0.05 |
| sonnet-4.6 | auto | testcases | Replace Variable With Attribute | 2 | 2744 | 0.00 |
| sonnet-4.6 | auto | testcases | Remove Thrown Exception Type | 1 | 2744 | 0.00 |
| sonnet-4.6 | auto | testcases | Add Variable Annotation | 2 | 2744 | 0.00 |
| sonnet-4.6 | auto | testcases | Replace Generic With Diamond | 5 | 2744 | 0.00 |
| sonnet-4.6 | auto | testsuites | Variable Rename | 126 | 126 | 1.00 |
| sonnet-4.6 | auto | testsuites | Method Rename | 111 | 126 | 0.88 |
| sonnet-4.6 | auto | testsuites | Line Comment Added/Updated | 105 | 126 | 0.83 |
| sonnet-4.6 | auto | testsuites | Javadoc Added/Updated | 44 | 126 | 0.35 |
| sonnet-4.6 | auto | testsuites | Block Comment Added/Updated | 23 | 126 | 0.18 |
| sonnet-4.6 | auto | testsuites | Line Comment Deleted | 56 | 126 | 0.44 |
| sonnet-4.6 | auto | testsuites | Block Comment Deleted | 14 | 126 | 0.11 |
| sonnet-4.6 | auto | testsuites | Extract Variable | 5 | 126 | 0.04 |
| sonnet-4.6 | auto | testsuites | Inline Variable | 45 | 126 | 0.36 |
| sonnet-4.6 | auto | testsuites | Change Variable Type | 3 | 126 | 0.02 |
| sonnet-4.6 | auto | testsuites | Parameterize Variable | 1 | 126 | 0.01 |
| sonnet-4.6 | auto | testsuites | Merge Variable | 1 | 126 | 0.01 |
| sonnet-4.6 | auto | testsuites | Extract Attribute | 8 | 126 | 0.06 |
| sonnet-4.6 | auto | testsuites | Replace Variable With Attribute | 2 | 126 | 0.02 |
| sonnet-4.6 | auto | testsuites | Extract Method | 3 | 126 | 0.02 |
| sonnet-4.6 | auto | testsuites | Add Method Annotation | 1 | 126 | 0.01 |
| sonnet-4.6 | auto | testsuites | Add Variable Annotation | 2 | 126 | 0.02 |
| sonnet-4.6 | auto | testsuites | Replace Generic With Diamond | 3 | 126 | 0.02 |
| sonnet-4.6 | auto | testsuites | Extract Fixture | 2 | 126 | 0.02 |
| sonnet-4.6 | manual | ALL | Variable Rename | 949 | 2900 | 0.33 |
| sonnet-4.6 | manual | ALL | Method Rename | 195 | 2900 | 0.07 |
| sonnet-4.6 | manual | ALL | Line Comment Added/Updated | 2162 | 2900 | 0.75 |
| sonnet-4.6 | manual | ALL | Javadoc Added/Updated | 1398 | 2900 | 0.48 |
| sonnet-4.6 | manual | ALL | Block Comment Added/Updated | 15 | 2900 | 0.01 |
| sonnet-4.6 | manual | ALL | Line Comment Deleted | 770 | 2900 | 0.27 |
| sonnet-4.6 | manual | ALL | Javadoc Deleted | 107 | 2900 | 0.04 |
| sonnet-4.6 | manual | ALL | Block Comment Deleted | 2 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Blank-Line Separation Added | 1427 | 2900 | 0.49 |
| sonnet-4.6 | manual | ALL | Attribute Rename | 88 | 2900 | 0.03 |
| sonnet-4.6 | manual | ALL | Class Rename | 5 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Extract Variable | 581 | 2900 | 0.20 |
| sonnet-4.6 | manual | ALL | Inline Variable | 66 | 2900 | 0.02 |
| sonnet-4.6 | manual | ALL | Change Variable Type | 91 | 2900 | 0.03 |
| sonnet-4.6 | manual | ALL | Add Variable Modifier | 22 | 2900 | 0.01 |
| sonnet-4.6 | manual | ALL | Remove Variable Modifier | 36 | 2900 | 0.01 |
| sonnet-4.6 | manual | ALL | Parameterize Variable | 15 | 2900 | 0.01 |
| sonnet-4.6 | manual | ALL | Extract Attribute | 196 | 2900 | 0.07 |
| sonnet-4.6 | manual | ALL | Inline Attribute | 27 | 2900 | 0.01 |
| sonnet-4.6 | manual | ALL | Change Attribute Type | 33 | 2900 | 0.01 |
| sonnet-4.6 | manual | ALL | Add Attribute Modifier | 24 | 2900 | 0.01 |
| sonnet-4.6 | manual | ALL | Parameterize Attribute | 1 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Encapsulate Attribute | 1 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Replace Variable With Attribute | 43 | 2900 | 0.01 |
| sonnet-4.6 | manual | ALL | Replace Attribute With Variable | 12 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Extract Method | 44 | 2900 | 0.02 |
| sonnet-4.6 | manual | ALL | Inline Method | 59 | 2900 | 0.02 |
| sonnet-4.6 | manual | ALL | Move Method | 4 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Move And Rename Method | 2 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Split Method | 35 | 2900 | 0.01 |
| sonnet-4.6 | manual | ALL | Change Method Access Modifier | 70 | 2900 | 0.02 |
| sonnet-4.6 | manual | ALL | Add Method Modifier | 6 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Remove Method Modifier | 23 | 2900 | 0.01 |
| sonnet-4.6 | manual | ALL | Add Method Annotation | 198 | 2900 | 0.07 |
| sonnet-4.6 | manual | ALL | Remove Method Annotation | 32 | 2900 | 0.01 |
| sonnet-4.6 | manual | ALL | Modify Method Annotation | 28 | 2900 | 0.01 |
| sonnet-4.6 | manual | ALL | Add Parameter | 11 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Remove Parameter | 22 | 2900 | 0.01 |
| sonnet-4.6 | manual | ALL | Reorder Parameter | 6 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Localize Parameter | 5 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Change Parameter Type | 10 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Add Parameter Modifier | 7 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Change Return Type | 45 | 2900 | 0.02 |
| sonnet-4.6 | manual | ALL | Add Thrown Exception Type | 1 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Remove Thrown Exception Type | 60 | 2900 | 0.02 |
| sonnet-4.6 | manual | ALL | Change Thrown Exception Type | 7 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Assert Throws | 19 | 2900 | 0.01 |
| sonnet-4.6 | manual | ALL | Extract Class | 5 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Add Class Annotation | 16 | 2900 | 0.01 |
| sonnet-4.6 | manual | ALL | Remove Class Annotation | 94 | 2900 | 0.03 |
| sonnet-4.6 | manual | ALL | Modify Class Annotation | 15 | 2900 | 0.01 |
| sonnet-4.6 | manual | ALL | Remove Variable Annotation | 4 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Move Code | 1 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Invert Condition | 2 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Merge Conditional | 6 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Replace Conditional With Ternary | 2 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Replace Anonymous With Class | 2 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Parameterize Test | 14 | 2900 | 0.00 |
| sonnet-4.6 | manual | ALL | Change Attribute Access Modifier | 21 | 2900 | 0.01 |
| sonnet-4.6 | manual | ALL | Extract Fixture | 1 | 2900 | 0.00 |
| sonnet-4.6 | manual | testcases | Variable Rename | 868 | 2770 | 0.31 |
| sonnet-4.6 | manual | testcases | Method Rename | 151 | 2770 | 0.05 |
| sonnet-4.6 | manual | testcases | Line Comment Added/Updated | 2042 | 2770 | 0.74 |
| sonnet-4.6 | manual | testcases | Javadoc Added/Updated | 1302 | 2770 | 0.47 |
| sonnet-4.6 | manual | testcases | Block Comment Added/Updated | 11 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Line Comment Deleted | 727 | 2770 | 0.26 |
| sonnet-4.6 | manual | testcases | Javadoc Deleted | 105 | 2770 | 0.04 |
| sonnet-4.6 | manual | testcases | Block Comment Deleted | 2 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Blank-Line Separation Added | 1427 | 2770 | 0.52 |
| sonnet-4.6 | manual | testcases | Attribute Rename | 78 | 2770 | 0.03 |
| sonnet-4.6 | manual | testcases | Class Rename | 1 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Extract Variable | 561 | 2770 | 0.20 |
| sonnet-4.6 | manual | testcases | Inline Variable | 48 | 2770 | 0.02 |
| sonnet-4.6 | manual | testcases | Change Variable Type | 86 | 2770 | 0.03 |
| sonnet-4.6 | manual | testcases | Add Variable Modifier | 16 | 2770 | 0.01 |
| sonnet-4.6 | manual | testcases | Remove Variable Modifier | 34 | 2770 | 0.01 |
| sonnet-4.6 | manual | testcases | Parameterize Variable | 12 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Extract Attribute | 169 | 2770 | 0.06 |
| sonnet-4.6 | manual | testcases | Inline Attribute | 27 | 2770 | 0.01 |
| sonnet-4.6 | manual | testcases | Change Attribute Type | 33 | 2770 | 0.01 |
| sonnet-4.6 | manual | testcases | Add Attribute Modifier | 23 | 2770 | 0.01 |
| sonnet-4.6 | manual | testcases | Parameterize Attribute | 1 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Encapsulate Attribute | 1 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Replace Variable With Attribute | 40 | 2770 | 0.01 |
| sonnet-4.6 | manual | testcases | Replace Attribute With Variable | 11 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Extract Method | 38 | 2770 | 0.01 |
| sonnet-4.6 | manual | testcases | Inline Method | 58 | 2770 | 0.02 |
| sonnet-4.6 | manual | testcases | Split Method | 32 | 2770 | 0.01 |
| sonnet-4.6 | manual | testcases | Change Method Access Modifier | 67 | 2770 | 0.02 |
| sonnet-4.6 | manual | testcases | Add Method Modifier | 5 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Remove Method Modifier | 23 | 2770 | 0.01 |
| sonnet-4.6 | manual | testcases | Add Method Annotation | 181 | 2770 | 0.07 |
| sonnet-4.6 | manual | testcases | Remove Method Annotation | 30 | 2770 | 0.01 |
| sonnet-4.6 | manual | testcases | Modify Method Annotation | 27 | 2770 | 0.01 |
| sonnet-4.6 | manual | testcases | Add Parameter | 9 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Remove Parameter | 21 | 2770 | 0.01 |
| sonnet-4.6 | manual | testcases | Reorder Parameter | 6 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Localize Parameter | 5 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Change Parameter Type | 10 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Add Parameter Modifier | 5 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Change Return Type | 44 | 2770 | 0.02 |
| sonnet-4.6 | manual | testcases | Add Thrown Exception Type | 1 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Remove Thrown Exception Type | 59 | 2770 | 0.02 |
| sonnet-4.6 | manual | testcases | Change Thrown Exception Type | 7 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Assert Throws | 16 | 2770 | 0.01 |
| sonnet-4.6 | manual | testcases | Add Class Annotation | 14 | 2770 | 0.01 |
| sonnet-4.6 | manual | testcases | Remove Class Annotation | 94 | 2770 | 0.03 |
| sonnet-4.6 | manual | testcases | Modify Class Annotation | 15 | 2770 | 0.01 |
| sonnet-4.6 | manual | testcases | Remove Variable Annotation | 4 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Move Code | 1 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Invert Condition | 1 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Merge Conditional | 5 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Replace Conditional With Ternary | 1 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Replace Anonymous With Class | 1 | 2770 | 0.00 |
| sonnet-4.6 | manual | testcases | Parameterize Test | 14 | 2770 | 0.01 |
| sonnet-4.6 | manual | testcases | Change Attribute Access Modifier | 18 | 2770 | 0.01 |
| sonnet-4.6 | manual | testsuites | Variable Rename | 81 | 130 | 0.62 |
| sonnet-4.6 | manual | testsuites | Method Rename | 44 | 130 | 0.34 |
| sonnet-4.6 | manual | testsuites | Line Comment Added/Updated | 120 | 130 | 0.92 |
| sonnet-4.6 | manual | testsuites | Javadoc Added/Updated | 96 | 130 | 0.74 |
| sonnet-4.6 | manual | testsuites | Block Comment Added/Updated | 4 | 130 | 0.03 |
| sonnet-4.6 | manual | testsuites | Line Comment Deleted | 43 | 130 | 0.33 |
| sonnet-4.6 | manual | testsuites | Javadoc Deleted | 2 | 130 | 0.02 |
| sonnet-4.6 | manual | testsuites | Attribute Rename | 10 | 130 | 0.08 |
| sonnet-4.6 | manual | testsuites | Class Rename | 4 | 130 | 0.03 |
| sonnet-4.6 | manual | testsuites | Extract Variable | 20 | 130 | 0.15 |
| sonnet-4.6 | manual | testsuites | Inline Variable | 18 | 130 | 0.14 |
| sonnet-4.6 | manual | testsuites | Change Variable Type | 5 | 130 | 0.04 |
| sonnet-4.6 | manual | testsuites | Add Variable Modifier | 6 | 130 | 0.05 |
| sonnet-4.6 | manual | testsuites | Remove Variable Modifier | 2 | 130 | 0.02 |
| sonnet-4.6 | manual | testsuites | Parameterize Variable | 3 | 130 | 0.02 |
| sonnet-4.6 | manual | testsuites | Extract Attribute | 27 | 130 | 0.21 |
| sonnet-4.6 | manual | testsuites | Add Attribute Modifier | 1 | 130 | 0.01 |
| sonnet-4.6 | manual | testsuites | Replace Variable With Attribute | 3 | 130 | 0.02 |
| sonnet-4.6 | manual | testsuites | Replace Attribute With Variable | 1 | 130 | 0.01 |
| sonnet-4.6 | manual | testsuites | Extract Method | 6 | 130 | 0.05 |
| sonnet-4.6 | manual | testsuites | Inline Method | 1 | 130 | 0.01 |
| sonnet-4.6 | manual | testsuites | Move Method | 4 | 130 | 0.03 |
| sonnet-4.6 | manual | testsuites | Move And Rename Method | 2 | 130 | 0.02 |
| sonnet-4.6 | manual | testsuites | Split Method | 3 | 130 | 0.02 |
| sonnet-4.6 | manual | testsuites | Change Method Access Modifier | 3 | 130 | 0.02 |
| sonnet-4.6 | manual | testsuites | Add Method Modifier | 1 | 130 | 0.01 |
| sonnet-4.6 | manual | testsuites | Add Method Annotation | 17 | 130 | 0.13 |
| sonnet-4.6 | manual | testsuites | Remove Method Annotation | 2 | 130 | 0.02 |
| sonnet-4.6 | manual | testsuites | Modify Method Annotation | 1 | 130 | 0.01 |
| sonnet-4.6 | manual | testsuites | Add Parameter | 2 | 130 | 0.02 |
| sonnet-4.6 | manual | testsuites | Remove Parameter | 1 | 130 | 0.01 |
| sonnet-4.6 | manual | testsuites | Add Parameter Modifier | 2 | 130 | 0.02 |
| sonnet-4.6 | manual | testsuites | Change Return Type | 1 | 130 | 0.01 |
| sonnet-4.6 | manual | testsuites | Remove Thrown Exception Type | 1 | 130 | 0.01 |
| sonnet-4.6 | manual | testsuites | Assert Throws | 3 | 130 | 0.02 |
| sonnet-4.6 | manual | testsuites | Extract Class | 5 | 130 | 0.04 |
| sonnet-4.6 | manual | testsuites | Add Class Annotation | 2 | 130 | 0.02 |
| sonnet-4.6 | manual | testsuites | Invert Condition | 1 | 130 | 0.01 |
| sonnet-4.6 | manual | testsuites | Merge Conditional | 1 | 130 | 0.01 |
| sonnet-4.6 | manual | testsuites | Replace Conditional With Ternary | 1 | 130 | 0.01 |
| sonnet-4.6 | manual | testsuites | Replace Anonymous With Class | 1 | 130 | 0.01 |
| sonnet-4.6 | manual | testsuites | Change Attribute Access Modifier | 3 | 130 | 0.02 |
| sonnet-4.6 | manual | testsuites | Extract Fixture | 1 | 130 | 0.01 |

## Incomplete tests (left out of the frequencies)

- `gpt-5.5/jackson-annotations/auto/testsuites/JsonFormat_ESTest`: {'gumtree': 'OK', 'refactoringminer': 'ERROR', 'javaparser': 'OK', 'comments': 'OK'}
- `opus-4.8/jackson-annotations/auto/testsuites/JsonFormat_ESTest`: {'gumtree': 'OK', 'refactoringminer': 'ERROR', 'javaparser': 'OK', 'comments': 'OK'}
- `sonnet-4.6/commons-codec/manual/testsuites/BinaryCodecTest`: {'gumtree': 'OK', 'refactoringminer': 'ERROR', 'javaparser': 'OK', 'comments': 'OK'}
- `sonnet-4.6/jackson-annotations/auto/testsuites/JsonFormat_ESTest`: {'gumtree': 'OK', 'refactoringminer': 'ERROR', 'javaparser': 'OK', 'comments': 'OK'}
