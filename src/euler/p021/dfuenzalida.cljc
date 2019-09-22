(ns euler.p021.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5]]
   [euler.p021.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn divisors [n] ;; naive version, unused
  (filter #(zero? (rem n %)) (range 1 (inc (quot n 2))))) ;; from 1 to (n/2 + 1)

(defn divisors' [n]
  (let [divrange (take-while #(<= (* % %) n) (rest (range))) ;; 1 to ~sqrt(n)
        divs1    (filter #(zero? (rem n %)) divrange)]       ;; divisors upto sqrt(n)
    (-> (set divs1)
        (into (map #(quot n %) (rest divs1))))))

(defn amicable? [n]
  (let [sum-n (reduce + (divisors' n))
        sum-m (reduce + (divisors' sum-n))]
    (and (not= n sum-n)
         (= sum-m n))))

(defn solve []
  (reduce + (filter amicable? (range 1 10001))))

(deftest tests
  (is (amicable? 220))
  (is (amicable? 284))
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
