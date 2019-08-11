(ns euler.p036.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest]]
   [euler.p036.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn reverse-digits [base n]
  (loop [ds [] n n]
    (if (pos? n)
      (recur (conj ds (rem n base)) (quot n base))
      ds)))

(defn to-number [base ds]
  (reduce (fn [acc d] (+ (* acc base) d)) ds))

(defn palin-base? [base n]
  (= n (to-number base (reverse-digits base n))))

(defn two-base-palin? [n]
  (and (palin-base? 2 n) (palin-base? 10 n)))

(defn solve []
  (reduce + (filter two-base-palin? (range 1 1000000))))

(deftest tests
  (is (two-base-palin? 585))
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests)
)
