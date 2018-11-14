#!/bin/bash
../../trec_eval.9.0/trec_eval -q -m Rprec train.pages.cbor-article.qrels U-L.runfile > U-L.Rprec.out
../../trec_eval.9.0/trec_eval -q -m map train.pages.cbor-article.qrels U-L.runfile > U-L.map.out
../../trec_eval.9.0/trec_eval -q -m all_trec train.pages.cbor-article.qrels U-L.runfile | grep -w "ndcg_cut_20" > U-L.ndcg20.out
../../trec_eval.9.0/trec_eval -q -m Rprec train.pages.cbor-article.qrels U-JM.runfile > U-JM.Rprec.out
../../trec_eval.9.0/trec_eval -q -m map train.pages.cbor-article.qrels U-JM.runfile > U-JM.map.out
../../trec_eval.9.0/trec_eval -q -m all_trec train.pages.cbor-article.qrels U-JM.runfile | grep -w "ndcg_cut_20" > U-JM.ndcg20.out
../../trec_eval.9.0/trec_eval -q -m Rprec train.pages.cbor-article.qrels U-DS.runfile > U-DS.Rprec.out
../../trec_eval.9.0/trec_eval -q -m map train.pages.cbor-article.qrels U-DS.runfile > U-DS.map.out
../../trec_eval.9.0/trec_eval -q -m all_trec train.pages.cbor-article.qrels U-DS.runfile | grep -w "ndcg_cut_20" > U-DS.ndcg20.out
