(ns euler.p005.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest read-string format]]
   [euler.p005.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(def primes [2 3 5 7 11 13 17 19])

(defn pow
  "Returns n^m"
  [n m]
  (reduce * (repeat m n)))

(defn factors-map
  "Returns a map with prime factors as keys and exponents as keys.
  (factors-map 12) returns {2 2, 3 1} because 12 is 2^2 * 3^1"
  ([n] (factors-map n {}))
  ([n m]
   (if (< n 2)
     m
     ;; Find `divisor`, the first prime that divides `n`
     (let [divisor (first (filter #(zero? (rem n %)) primes))]
       ;; Iterate dividing the original number and keeping count of the factor
       (recur (/ n divisor) (merge-with + m {divisor 1}))))))

(defn product-from-map [m]
  (->> (map #(apply pow %) m)
       (reduce *)))

(defn solve []
  (->> (range 1 21)
       (map factors-map)
       (reduce (partial merge-with max))
       product-from-map))

(deftest part-1
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests)
)
