(ns euler.p058.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 primes]]
   [euler.p058.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(def primes-1m
  (delay (take-while #(< % 1000000) (primes))))

(defn prime? [n]
  (let [factors (take-while #(<= (* % %) n) @primes-1m)]
    (empty?
     (filter #(zero? (rem n %)) factors))))

(defn diags []
  (mapcat (fn [n]
            [(+ (* n n) (* -3 n) 3)
             (+ (* n n) (* -2 n) 2)
             (+ (* n n) (* -1 n) 1)])
          (map (comp inc (partial * 2)) (rest (range)))))

(defn prime-reducer [num-primes n]
  (let [ps (->> [(+ (* n n) (* -3 n) 3)
                 (+ (* n n) (* -2 n) 2)
                 (+ (* n n) (* -1 n) 1)]
                (filter prime?) count)]
    (if (< (/ (+ num-primes ps) (dec (* 2 n))) 0.1)
      (reduced n)
      (+ num-primes ps))))

(defn solve []
  (let [odd-nums (map (comp inc (partial * 2)) (rest (range)))]
    (reduce prime-reducer 0 odd-nums)))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
