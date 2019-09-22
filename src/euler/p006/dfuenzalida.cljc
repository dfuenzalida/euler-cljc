(ns euler.p006.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5]]
   [euler.p006.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn solve [n]
  (let [squares-sum (reduce + (map #(* % %) (range (inc n))))
        sum-squared (#(* % %) (reduce + (range (inc n))))]
    (- sum-squared squares-sum)))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve 100))))))

;;;; Scratch

(comment
  (t/run-tests)
)
