(ns euler.p009.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest]]
   [euler.p009.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn solve []
  (first 
   (for [x (range 1 1000)
         y (range x 1000)
         :let [z (- 1000 x y)]
         :when (= (+ (* x x) (* y y)) (* z z))]
     (* x y z))))

(deftest part-1
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests)
)
