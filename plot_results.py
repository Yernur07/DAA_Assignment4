import csv
import os
import matplotlib.pyplot as plt
from collections import defaultdict
CSV_FILE = "results/results.csv"
PLOTS_DIR = "results/plots"
os.makedirs(PLOTS_DIR, exist_ok=True)
data = []
with open(CSV_FILE, "r", newline="") as file:
    reader = csv.DictReader(file)
    for row in reader:
        row["n"] = int(row["n"])
        row["time_ms"] = float(row["time_ms"])
        row["operations"] = int(row["operations"])
        data.append(row)
edit_data = [row for row in data
             if row["algorithm"] in ["Recursive", "Table", "TwoRows"]]
search_data = [row for row in data
               if row["algorithm"] in ["Naive", "KMP"]]
def create_edit_plot(metric, ylabel, filename, logarithmic=False):
    groups = defaultdict(list)
    for row in edit_data:
        key = (row["algorithm"], row["input_type"])
        groups[key].append(row)
    plt.figure(figsize=(10, 6))
    for key, rows in groups.items():
        rows.sort(key=lambda row: row["n"])
        x = [row["n"] for row in rows]
        y = [row[metric] for row in rows]
        plt.plot(x, y, marker="o",
                 label=f"{key[0]} - {key[1]}")
    if logarithmic:
        plt.yscale("log")
    plt.xlabel("Input size (n)")
    plt.ylabel(ylabel)
    plt.title(ylabel + " - Edit Distance")
    plt.legend()
    plt.grid(True)
    plt.tight_layout()
    plt.savefig(os.path.join(PLOTS_DIR, filename), dpi=300)
    plt.close()
create_edit_plot(
    "operations",
    "Number of operations",
    "edit_distance_operations.png",
    True
)
create_edit_plot(
    "time_ms",
    "Execution time (ms)",
    "edit_distance_time.png"
)
groups = defaultdict(list)
for row in search_data:
    key = (row["algorithm"], row["input_type"])
    groups[key].append(row)
plt.figure(figsize=(10, 6))
for key, rows in groups.items():
    rows.sort(key=lambda row: row["n"])
    x = [row["n"] for row in rows]
    y = [row["operations"] for row in rows]
    plt.plot(x, y, marker="o",
             label=f"{key[0]} - {key[1]}")
plt.yscale("log")
plt.xlabel("Text length (n)")
plt.ylabel("Character comparisons")
plt.title("Naive vs KMP - Character Comparisons")
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig(
    os.path.join(PLOTS_DIR, "string_search_comparisons.png"),
    dpi=300
)
plt.close()
print("All plots generated successfully.")
print("Saved in:", PLOTS_DIR)