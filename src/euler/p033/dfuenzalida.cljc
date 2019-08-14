(ns euler.p033.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest]]
   [euler.p033.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn curious? [[num den]]
  (let [n1 (quot num 10)
        n2 (rem  num 10)
        d1 (quot den 10)
        d2 (rem  den 10)]
    (or
     (and (pos? d2) (= n2 d1) (= (/ num den) (/ n1 d2)))
     (and (pos? d1) (= n1 d2) (= (/ num den) (/ n2 d1))))))

(defn solve []
  (let [ratios (filter curious? (for [x (range 10 100), y (range 10 x)] [y x]))
        n-prod (reduce * (map first ratios))
        d-prod (reduce * (map second ratios))]
    (quot d-prod n-prod)))

(deftest tests
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests)
)
