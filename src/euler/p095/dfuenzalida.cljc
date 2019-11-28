(ns euler.p095.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 primes]]
   [euler.p095.data :refer [answer]]
   [clojure.test :as t :refer [is]]))

(def primes-100k (delay (set (take-while #(<= % 100000) (primes)))))

(defn divisors [n]
  (if (@primes-100k n)
    [1]
    (loop [divs [1], i 2, limit n]
      (cond
        (> i limit) divs
        (zero? (rem n i)) (let [qi (quot n i)]
                            (recur (into divs [i qi]) (inc i) (dec qi)))
        :else (recur divs (inc i) limit)))))

(defn sum-divs [n]
  (reduce + (divisors n)))

(defn build-chain [n]
  (if (@primes-100k n)
    []
    (loop [n n, chain [n], seen #{n}]
      (let [s (sum-divs n)]
        (cond
          (> s 1000000) []
          (#{0 1} s) []
          (seen s) (if (= (first chain) s) chain [])
          :else (recur s (conj chain s) (conj seen s)))))))

(defn solve [n]
  (->> (apply max-key (comp count build-chain) (range 1 n))
       build-chain
       (reduce min)))

(deftest tests
  (is (= 28 (sum-divs 28)))
  (is (= [12496 14288 15472 14536 14264] (build-chain 12496)))
  (is (= 12496 (solve 13000)))
  (is (= (str answer)
         (md5 (str (solve 15000))))))

;;;; Scratch

(comment
  (t/run-tests)
  )
