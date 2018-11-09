import json
import os

import matplotlib.pyplot as plt

results_json = json.load(open('results.json', encoding='utf-8'))  # type: dict
save_dir = "graphs/"
print(json.dumps(results_json, indent=4))

heuristic_names = {'RANDOM_MAX_GLOBAL_STEPS': 'RANDOM',
                   'RANDOM_MAX_GLOBAL_STEPS_IGNORE_COMPLETE_THREADS': 'RANDOM_IGNORE_COMPLETE',
                   'PROBABILISTIC_MOST_STORES_STATIC_SHUFFLE': 'PROB_STORES_SHUFFLE',
                   'PROBABILISTIC_MOST_STORES_STATIC': 'PROB_STORES',
                   'PROBABILISTIC_MOST_STORES_AND_BRANCHES_STATIC': 'PROB_STORES_BRANCHES'}

if not os.path.isdir(save_dir):
    os.makedirs(save_dir)

for program_name, v in results_json.items():
    scores = v  # type: dict
    for score_method, heuristic in scores.items():
        plt.clf()
        plt.figure(figsize=(12, 5))
        x = [heuristic_names[x] for x in list(heuristic.keys())]
        y = list(heuristic.values())
        plt.bar(x, y, align='center', alpha=0.5)
        plt.ylabel("Score")
        plt.xticks([heuristic_names[x] for x in list(heuristic.keys())])
        plt.title(str(program_name) + " " + str(score_method))
        name = str(program_name) + " " + str(score_method) + ".png"
        name = name.replace("/", "-").replace("<", "-").replace(">", "-")

        for a, b in zip(x, y):
            plt.text(a, b, str(b))

        plt.savefig(save_dir + name, bbox_inches='tight')

# first_prog = results_json[list(results_json.keys())[1]]
# first_scorer = first_prog[list(first_prog.keys())[0]]
#
# pprint(first_prog)
#
# objects = list(first_scorer.keys())
#
#
# plt.bar(list(first_scorer.keys()), list(first_scorer.values()), align='center', alpha=0.5)
# plt.ylabel("Score")
# plt.xticks(objects)
# plt.title(str(list(results_json.keys())[0]) + str(list(first_prog.keys())[0]))
#
# plt.show()
