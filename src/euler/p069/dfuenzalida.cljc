(ns euler.p069.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 primes]]
   [euler.p069.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(def primes-1m
  (delay (take-while #(<= % 1000000) (primes))))

(def primes-set
  (delay (set (take-while #(<= % 1000000) (primes)))))

(defn prime-factors [n]
  (loop [acc #{} n n]
    (if (= n 1)
      acc
      (let [factor (first (filter #(zero? (mod n %)) @primes-1m))]
        (recur (conj acc factor) (quot n factor))))))

(defn prime? [n]
  (some? (@primes-set n)))

(defn totent-ratio [n]
  (if (prime? n)
    (/ (* 1.0 n) (dec n))
    (reduce * (map totent-ratio (prime-factors n)))))

(defn solve-naive [] ;; brute-force, takes ~300 seconds in my laptop
  (apply max-key totent-ratio (range 2 1000001)))

(defn solve []
  (->> (reductions * (primes))
       (take-while #(<= % 1000000))
       last))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
