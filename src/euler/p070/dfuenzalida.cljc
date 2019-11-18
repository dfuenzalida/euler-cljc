(ns euler.p070.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 primes]]
   [euler.p070.data :refer [answer]]
   [clojure.test :as t :refer [is]]))

(def primes-set
  (delay (apply sorted-set (take-while #(<= % 10000000) (primes)))))

(defn permutation? [a b]
  (let [sa (str a) sb (str b)]
    (and (= (count sa) (count sb))
         (= (frequencies sa) (frequencies sb)))))

(defn prime-factors [n]
  (loop [acc #{} n n]
    (if (= n 1)
      acc
      (if (@primes-set n) ;; nice speed boost
        (conj acc n)
        (let [factor (first (filter #(zero? (rem n %)) @primes-set))]
          (recur (conj acc factor) (quot n factor)))))))

;; https://en.wikipedia.org/wiki/Euler%27s_totient_function#The_function_is_multiplicative

(defn phi [n]
  (reduce * (map dec (prime-factors n))))

(defn solve [limit]
  (loop [n 2, best-n 1, best-ratio 2]
    (if (< n limit)
      (let [phi-n (phi n)
            ratio (float (/ n phi-n))]
        (if (and (< ratio best-ratio)
                 (permutation? n phi-n))
          (recur (inc n) n ratio)
          (recur (inc n) best-n best-ratio)))
      best-n)))

(deftest tests
  (is (= 79180 (phi 87109)))
  (is (= (str answer)
         (md5 (str (solve 10000000))))))

;;;; Scratch

(comment

  ;; Flame graphs
  (require '[flames.core :as flames])
  (def flames (flames/start! {:port 54321, :host "localhost"}))
  ;; http://localhost:54321/flames.svg
  (time (solve))
  (flames/stop! flames)

  (t/run-tests)
  )
