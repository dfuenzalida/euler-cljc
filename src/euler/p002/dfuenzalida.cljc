(ns euler.p002.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5]]
   [euler.p002.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn solve []
  (let [fibs (map first (iterate (fn [[a b]] [b (+ a b)]) [1 2]))]
    (->> fibs
         (take-while #(< % 4000000))
         (filter even?)
         (reduce +))))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
