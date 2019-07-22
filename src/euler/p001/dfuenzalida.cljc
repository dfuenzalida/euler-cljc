(ns euler.p001.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest read-string format]]
   [euler.p001.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn solve []
  (->> (range 1000)
       (filter #(or (zero? (rem % 3)) (zero? (rem % 5))))
       (reduce +)))

(deftest part-1
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests)
)
