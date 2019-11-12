(ns euler.p124.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 primes]]
   [euler.p124.data :refer [answer]]
   [clojure.test :as t :refer [is]]))

(def primes-100k
  (delay (take-while #(< % 100000) (primes))))

(defn factors [n]
  (loop [n n, fs #{}]
    (if (= 1 n)
      fs
      (let [factor (->> (take-while #(<= % n) @primes-100k)
                        (filter #(zero? (mod n %)))
                        first)]
        (recur (quot n factor) (conj fs factor))))))

(defn rad [n]
  (reduce * (factors n)))

(defn solve []
  (let [sorted (->> (range 1 (inc 100000))
                    (map (juxt identity rad))
                    (sort-by second))]
    (first (nth sorted (dec 10000)))))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
