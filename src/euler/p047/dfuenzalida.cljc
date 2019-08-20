(ns euler.p047.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest primes]]
   [euler.p047.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn consecutives? [xs]
  (let [x (first xs)]
    (= xs (range x (+ x (count xs))))))

(def primes-1m (delay (take-while #(<= % 1000000) (primes))))

(defn enough-factors? [num-factors n]
  (loop [factors #{} n n]
    (let [enough-factors? (= (count factors) num-factors)]
      (if (or (= 1 n) enough-factors?)
        enough-factors?
        (let [factor (first (filter #(zero? (rem n %)) @primes-1m))]
          (recur (conj factors factor) (quot n factor)))))))

(defn solve [n]
  (->> (rest (range))
       (filter (partial enough-factors? n))
       (partition n 1)
       (filter consecutives?)
       ffirst))

(deftest tests
  (is (consecutives? [644 645 646]))
  (is (= 14 (solve 2)))
  (is (= 644 (solve 3)))
  (is (= (str answer)
         (str (solve 4)))))

;;;; Scratch

(comment
  (t/run-tests)
)
