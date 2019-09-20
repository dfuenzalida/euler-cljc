(ns euler.p055.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest read-string to-bigint]]
   [euler.p055.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))


(defn palin? [n]
  (= (seq (str n)) (reverse (seq (str n)))))

(defn lychrel? [i n]
  (let [sum (+ (to-bigint n) (->> n str reverse (apply str) to-bigint))]
    (if (palin? sum)
      false
      (if (>= i 50)
        true
        (recur (inc i) sum)))))

(defn solve []
  (count (filter (partial lychrel? 1) (range 1 10000))))

(deftest tests
  (is (lychrel? 1 196))
  (is (lychrel? 1 4994))
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests)
)
