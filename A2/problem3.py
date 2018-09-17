#!/usr/bin/env python3

import sys

def gen_query(results, R):
    # R is number of relevant documents
    return {"results": results, "R": R}

def compute_p_at_r(qrel_name, results_name):
    qrel = {};
    queries = {};

    qrel_fp = open(qrel_name, "r");
    results_fp = open(results_name, "r");

    for line in qrel_fp:
        words = line.split()
        qrel[(words[0], words[2])] = int(words[3])

    for line in results_fp:
        words = line.split()
        if words[0] in queries:
            if (words[0], words[2]) in qrel and qrel[(words[0], words[2])] == 1:
                queries[words[0]]["results"].append(1)
                queries[words[0]]["R"] += 1
            else:
                queries[words[0]]["results"].append(0)
        else:
            queries[words[0]] = gen_query([], 0);
            if (words[0], words[2]) in qrel and qrel[(words[0], words[2])] == 1:
                queries[words[0]]["results"].append(1)
                queries[words[0]]["R"] += 1
            else:
                queries[words[0]]["results"].append(0)

    p_at_r = {}

    for query in queries:
        tp = 0;
        fp = 0;
        for i in range(queries[query]["R"]):
            if (queries[query]["results"][i] == 1):
                tp += 1
            else:
                fp += 1
        if (tp == 0 and fp == 0):
            p_at_r[query] = 0
        else:
            p_at_r[query] = tp / (tp + fp)

    mean = 0
    for k in p_at_r:
        mean += p_at_r[k]

    mean /= len(p_at_r)
    print(results_name)
    print("RPrec: " + str(mean))

if (len(sys.argv) != 4):
    print("problem2.py <qrel> <default.runfile> <custom.runfile>")
    exit(-1)

compute_p_at_r(sys.argv[1], sys.argv[2])
compute_p_at_r(sys.argv[1], sys.argv[3])
