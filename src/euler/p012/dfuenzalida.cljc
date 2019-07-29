(ns euler.p012.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest read-string format]]
   [euler.p012.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(def triangle-nums
  (reductions + (rest (range))))

(defn count-divisors [n]
  (let [divisors-range (take-while #(<= (* % %) n) (rest (range)))]
    (* 2 ;; both divisor and dividend are counted. They could be the same though.
       (count (filter #(zero? (rem n %)) divisors-range)))))

(defn solve []
  (first (drop-while #(< (count-divisors %) 500) triangle-nums)))

(deftest part-1
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests)
)
