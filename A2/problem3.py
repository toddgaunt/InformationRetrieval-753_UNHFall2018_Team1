#!/usr/bin/env python3

import sys

def gen_query(results, R):
    # R is number of relevant documents
    return {"results": results, "R": R}

def compute_p_at_r(qrel_name, results_name):
    qrel = {};
    queries = {};

    with open(qrel_name, "r") as fp:
        for line in fp:
            words = line.split()
            qrel[(words[0], words[2])] = int(words[3])
            if words[0] not in queries:
                queries[words[0]] = gen_query([], 0);
            else:
                queries[words[0]]["R"] += 1;

    with open(results_name, "r") as fp:
        for line in fp:
            words = line.split()
            if words[0] in queries:
                if (words[0], words[2]) not in qrel:
                    qrel[(words[0], words[2])] = 0
                queries[words[0]]["results"].append(qrel[(words[0], words[2])])
            else:
                queries[words[0]] = gen_query([], 0);
                if (words[0], words[2]) not in qrel:
                    qrel[(words[0], words[2])] = 0
                queries[words[0]]["results"].append(qrel[(words[0], words[2])])

    p_at_r = {}

    for query in queries:
        R = queries[query]["R"];
        if R == 0:
            p_at_r[query] = 0.0
        else:
            p_at_r[query] = float(sum(queries[query]["results"])) / float(R)

    mean = 0
    for k in p_at_r:
        mean += p_at_r[k]

    mean /= len(p_at_r)
    print(results_name)
    print("Rprec: " + str(mean))

if (len(sys.argv) != 4):
    print("problem2.py <qrel> <default.runfile> <custom.runfile>")
    exit(-1)

compute_p_at_r(sys.argv[1], sys.argv[2])
compute_p_at_r(sys.argv[1], sys.argv[3])
