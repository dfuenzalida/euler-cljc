(ns euler.p052.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest read-string format]]
   [euler.p052.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn same-digits? [n]
  (->> (mapv (comp frequencies str (partial * n)) [2 3 4 5 6])
       (apply =)))

(defn solve []
  (first (filter same-digits? (rest (range)))))

(deftest tests
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests)
)
