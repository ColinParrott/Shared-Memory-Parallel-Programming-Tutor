import matplotlib.pyplot as plt

save_file = "graphs/gen_runtime.png"

heuristic_names = {'RANDOM_MAX_GLOBAL_STEPS': 'RANDOM',
                   'RANDOM_MAX_GLOBAL_STEPS_IGNORE_COMPLETE_THREADS': 'RANDOM_IGNORE_COMPLETE',
                   'PROBABILISTIC_MOST_STORES_STATIC_SHUFFLE': 'PROB_STORES_SHUFFLE',
                   'PROBABILISTIC_MOST_STORES_DYNAMIC': 'PROB_STORES_DYN',
                   'PROBABILISTIC_MOST_STORES_STATIC': 'PROB_STORES',
                   'PROBABILISTIC_MOST_STORES_AND_BRANCHES_STATIC': 'PROB_STORES_BRANCHES'}

results = {
    "RANDOM_MAX_GLOBAL_STEPS": 12491657,
    "RANDOM_MAX_GLOBAL_STEPS_IGNORE_COMPLETE_THREADS": 34281,
    "PROBABILISTIC_MOST_STORES_DYNAMIC": 11110,
    "PROBABILISTIC_MOST_STORES_STATIC": 1490029,
    "PROBABILISTIC_MOST_STORES_STATIC_SHUFFLE": 1372943,
    "PROBABILISTIC_MOST_STORES_AND_BRANCHES_STATIC": 1418240
}

plt.figure(figsize=(12, 5))
x = list(results.keys())
y = list(results.values())
plt.bar(x, y, align='center', alpha=0.5)
plt.ylabel("Score")
plt.xticks([heuristic_names[x] for x in list(results.keys())])
plt.title("Average number of sequences generated across 6 programs (2 seconds per program)")
plt.savefig(save_file, bbox_inches='tight')
