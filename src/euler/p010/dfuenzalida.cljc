(ns euler.p010.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest primes]]
   [euler.p010.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn solve []
  (->> (primes)
       (take-while #(< % 2000000))
       (reduce +)))

(deftest part-1
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests)
)
