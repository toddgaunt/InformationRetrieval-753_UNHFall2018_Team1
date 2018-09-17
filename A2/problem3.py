#!/usr/bin/env python3

import sys

def gen_query(results, R):
    # R is number of relevant documents
    return {"results": results, "R": R}

def compute_p_at_r(qrel_name, results_name):
    qrel = {};
    queries = {};

    # Add queries, and count relevant results

    with open(qrel_name, "r") as fp:
        for line in fp:
            words = line.split()
            qrel[(words[0], words[2])] = int(words[3])
            if words[0] not in queries:
                queries[words[0]] = gen_query(0, 1);
            else:
                queries[words[0]]["R"] += 1;

    # Count how many results per query are relevant results

    with open(results_name, "r") as fp:
        for line in fp:
            words = line.split()
            if (words[0], words[2]) in qrel:
                queries[words[0]]["results"] += 1

    # Compute the Precision @ R for each query

    p_at_r = {}

    for query in queries:
        results = queries[query]["results"]
        R = queries[query]["R"];
        p_at_r[query] = float(results) / float(R)

    # Compute the mean Precision @ R

    mean = 0.0
    for k in p_at_r:
        print(k + ": " + str(p_at_r[k]))
        mean += p_at_r[k]

    mean = mean / float(len(p_at_r))
    print(results_name)
    print("Rprec: " + str(mean))

if (len(sys.argv) != 4):
    print("problem2.py <qrel> <default.runfile> <custom.runfile>")
    exit(-1)

compute_p_at_r(sys.argv[1], sys.argv[2])
compute_p_at_r(sys.argv[1], sys.argv[3])
