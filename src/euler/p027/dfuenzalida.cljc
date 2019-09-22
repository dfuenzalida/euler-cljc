(ns euler.p027.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest primes md5]]
   [euler.p027.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn prime? [n]
  (and (pos? n)
       (let [factors (take-while #(<= (* % %) n) (primes))]
         (empty?
          (filter #(zero? (rem n %)) factors)))))

(defn count-primes [a b] ;; return # of primes in (n^2+an+b) for n >= 0
  (count
   (take-while prime? (map #(+ (* % %) (* a %) b) (range)))))

(defn most-primes [[n a b] [m c d]]
  (if (> n m) [n a b] [m c d]))

(defn solve []
  (->> (for [a (range  -999 1000)
             b (range -1000 1001)
             :let [n (count-primes a b)]]
         [n a b])
       (reduce most-primes)
       rest
       (apply *)))

(deftest tests
  (is (= 40 (count-primes 1 41)))     ;; n^2 + n + n1 produces 40 primes
  (is (= 80 (count-primes -79 1601))) ;; n^2 - 79n + 1601 produces 80 primes
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
