(ns euler.p034.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest parse-int]]
   [euler.p034.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn fact [n]
  (if (< n 2) 1 (reduce * (range 2 (inc n)))))

(def fact-m (memoize fact))

(defn curious? [n]
  (= n
     (->> n str (map (comp fact-m parse-int str)) (reduce +))))

(defn solve []
  (reduce + (filter curious? (range 11 100000))))

(deftest tests
  (is (curious? 145))
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests)
)
