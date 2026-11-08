# DAA Assignment 4 — City Search & Spell-Check

**Student:** Abdubek Alina  
**Group:** SE-2516  
**Course:** Design and Analysis of Algorithms

Java 17+ / Maven project for dynamic programming and string matching.

## Algorithms
- `RecursiveDistance`: basic un-memoized Levenshtein recursion.
- `TableDistance`: bottom-up dynamic programming with a full table.
- `TwoRowsDistance`: bottom-up dynamic programming with O(min(n,m)) extra memory.
- `NaiveSearch`: all pattern matches, including overlaps.
- `KmpSearch`: LPS preprocessing and linear scanning; `public int[] lps(String pattern)`.

Each edit-distance version implements `EditDistance.distance(String,String)`; search versions implement `StringSearch.search(String,String)`. Algorithm counters are exposed by `operations()` or `comparisons()`. Empty patterns throw `IllegalArgumentException`.

## Build and test

```bash
mvn clean test
```

## Re-run experiment

```bash
mvn exec:java
```

The experiment overwrites `results/results.csv`, producing 200 data rows (five individually timed runs for every configuration). Inputs use a fixed seed, so results and measured counters repeat; wall-clock times can differ between runs and computers. The generated four PNG charts are included in `results/plots/`, and the readable regeneration script is `make_plots.py` (requires Python 3 and Matplotlib):

```bash
python3 make_plots.py
```

## Repository layout

```text
src/main/java/edu/sdu/daa/    Algorithms, generator, experiment
src/test/java/edu/sdu/daa/    JUnit 5 reference and randomized tests
results/results.csv           Each measured run
results/plots/                Four PNG plots
REPORT.md                     Design card, interpretation, conclusions
make_plots.py                 Plot regeneration script
```

## Git / submission

No Git commits or tag are included in this ZIP. After uploading the folder to your own repository on the `main` branch, create the required tag `v1.0` yourself, and add your GitHub repository link to the Moodle submission. Upload the archive named `DAA_Assignment4_Abdubek_Alina_SE-2516.zip`.

## AI-use acknowledgement

AI assistance was used to help implement the Java algorithms, JUnit tests, experimental runner, plotting script, and initial report draft. The included code was compiled with Java 21, the experiment was executed, and reference values and randomized agreement were checked with a standalone verification harness. Maven/JUnit execution was not available in the preparation environment, so `mvn clean test` still needs to be run in an environment with Maven installed.
