# DAA Assignment 4 — Dynamic Programming and String Algorithms
## Overview
This project implements and compares dynamic programming and string matching algorithms using Java 21.
### Implemented algorithms
**Edit Distance**
- Recursive Edit Distance without memoization
- Edit Distance using a 2D DP table
- Space-optimized Edit Distance using two rows
**String Searching**
- Naive String Search
- Knuth-Morris-Pratt (KMP)

## Project Structure
```text
src/
├── main/java/
│   ├── editdistance/
│   ├── stringsearch/
│   ├── metrics/
│   └── benchmark/
└── test/java/
    ├── editdistance/
    └── stringsearch/

results/
├── results.csv
└── plots/
```

## Requirements
- Java 21
- Apache Maven 3.10.0
- Python 3
- Matplotlib

## Running the Tests
Run the following command from the project root:
```bash
mvn clean test
```

## Running the Benchmark
Run the `benchmark.BenchmarkMain` class from IntelliJ IDEA.
The benchmark measures execution time and algorithm-specific operations using a fixed random seed.
Results are saved to:
```text
results/results.csv
```

## Generating Plots
Install Matplotlib if necessary:
```bash
py -m pip install matplotlib
```
Generate the plots:
```bash
py plot_results.py
```
The plots are saved in:
```text
results/plots/
```

## Experimental Methodology
- One warm-up run
- Five measured runs
- Median execution time
- Fixed random seed: 42
- Multiple input types for each algorithm
- Operation counters for algorithm analysis

## Results
The CSV file contains benchmark measurements. The generated plots visualize execution time and operation counts.
See `REPORT.md` for the experiment design, analysis, and conclusions.

