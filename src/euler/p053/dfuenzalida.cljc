(ns euler.p053.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest to-bigint primes]]
   [euler.p053.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(def factorial
  (memoize
   (fn [n]
     (to-bigint
      (reduce * (map to-bigint (range 1 (inc n))))))))

(defn combinations [n r]
  (/ (factorial n) (* (factorial r) (factorial (- n r)))))

(defn solve []
  (let [big-1m (to-bigint 1000000)]
    (count
     (for [n (range 2 101)
           r (range 2 (inc n))
           :when (< big-1m (combinations n r))]
       true))))

(deftest tests
  (is (= "1144066" (str (combinations 23 10))))
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests)
)
