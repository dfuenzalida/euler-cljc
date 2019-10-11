(ns euler.p057.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 to-bigint]]
   [euler.p057.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn terms []
  (iterate (fn [[a b]] [(+ a b b) (+ a b)]) [(to-bigint 3) (to-bigint 2)]))

(defn large-numerator? [[a b]]
  (> (count (str a)) (count (str b))))

(defn solve []
  (->> (take 1000 (terms))
       (filter large-numerator?)
       count))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
