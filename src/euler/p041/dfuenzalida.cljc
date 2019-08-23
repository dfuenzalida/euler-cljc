(ns euler.p041.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest primes read-string]]
   [euler.p041.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn remove-at [v i]
  (let [m (min i (count v))
        n (min (inc m) (count v))]
    (into (subvec v 0 m) (subvec v n))))

(defn permutate [as xs]
  (if (seq xs)
    (map (fn [i]
           (permutate
            (conj as (xs i))
            (remove-at xs i)))
         (range (count xs)))
    (reduce str "" as)))

(defn prime? [ps n] ;; no divisors for n in ps from 2 up to ~sqrt(n)
  (empty? (filter #(zero? (rem n %)) (take-while #(<= (* % %) n) ps))))

(defn solve []
  (let [ps    (take-while #(<= % 32000) (primes)) ;; 32000^2 >= 987654321
        perms (mapcat #(flatten (permutate [] (vec (range 1 %)))) (range 2 11))]
    (->> perms
         (map read-string)
         (filter (partial prime? ps))
         last)))

(deftest tests
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests))
