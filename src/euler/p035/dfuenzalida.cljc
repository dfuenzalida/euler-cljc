(ns euler.p035.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest parse-int primes]]
   [euler.p035.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn primes-under [n]
  (set (take-while #(<= % n) (primes))))

(defn rotations [n]
  (let [num-digits (count (str n))]
    (->> (map str (str n))
         cycle
         (take (+ num-digits (dec num-digits)))
         (partition num-digits 1)
         (map #(apply str %))
         (map parse-int))))

(defn prime-rotations? [ps n]
  (every? ps (rotations n)))

(defn solve [n]
  (let [ps (primes-under n)]
    (count (filter (partial prime-rotations? ps) (range 2 (inc n))))))

(deftest tests
  (is (= 13 (solve 100)))
  (is (= (str answer)
         (str (solve 1000000)))))

;;;; Scratch

(comment
  (t/run-tests)
)
