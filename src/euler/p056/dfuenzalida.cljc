(ns euler.p056.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 to-bigint read-string format]]
   [euler.p056.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn sum-digits [s]
  (reduce + (map (comp read-string str) (str s))))

(defn pow [a b]
  (reduce * (repeat b (to-bigint a))))

(defn solve []
  (reduce max
          (for [a (range 2 100)
                b (range 2 100)]
            (sum-digits (pow a b)))))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
