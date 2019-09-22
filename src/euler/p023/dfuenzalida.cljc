(ns euler.p023.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5]]
   [euler.p023.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn divisors [n]
  (let [divrange (take-while #(<= (* % %) n) (rest (range))) ;; 1 to ~sqrt(n)
        divs1    (filter #(zero? (rem n %)) divrange)]       ;; divisors upto sqrt(n)
    (-> (set divs1)
        (into (map #(quot n %) (rest divs1))))))

(defn abundant? [n]
  (< n (reduce + (divisors n))))

(defn abundant-set [n]
  (filter abundant? (range 1 (inc n))))

(defn abundant-sum [n]
  (let [summands (abundant-set n)]
    (for [x summands
          y summands
          :let [s (+ x y)]
          :when (<= s n)]
      s)))

(defn non-abundant-sums [n]
  (let [all (set (range 1 (inc n)))]
    (->> (reduce disj all (abundant-sum n))
         (reduce +))))

(defn solve []
  (non-abundant-sums 28123))

(deftest tests
  (is (abundant? 12))
  (is (= (reduce + (range 1 24)) (non-abundant-sums 24)))
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
