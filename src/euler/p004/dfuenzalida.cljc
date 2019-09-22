(ns euler.p004.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5]]
   [euler.p004.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn solve []

  ;; convert number to seq, compare it to the seq's reverse
  (letfn [(palin? [x] (= (seq (str x)) (reverse (str x))))]

  ;; max elem on a double loop from 100 to 999 looking for palindromes
  (reduce max
    (for [x (range 100 1000) y (range 100 1000)
          :let [prod (* x y)]
          :when (palin? prod)] prod))))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
