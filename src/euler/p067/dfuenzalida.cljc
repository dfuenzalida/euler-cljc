(ns euler.p067.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 read-string format]]
   [euler.p067.data :refer [input small-input answer]]
   [clojure.test :as t :refer [is testing]]))

(defn compute-max [a b] ;; Computes one step by merging 2 consecutive rows a and b
  (let [a (into a [0])] ;; add a 0 for padding, solves an edge case
    (vec
     (for [i (range (count a))]
       (if (zero? i)
         (+ (a 0) (b 0))
         (+ (b i)
            (max (a (dec i)) (a i))))))))

(defn loop-max [rows]
  (loop [top (first rows)
         rows (rest rows)]
    (if (seq rows)
      (recur (compute-max top (first rows)) (rest rows))
      ;; no more rows? then just return the max of the top row
      (reduce max top))))

(defn solve []
  (loop-max input))

(deftest tests
  (is (= 23 (loop-max small-input)))
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
