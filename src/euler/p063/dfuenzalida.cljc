(ns euler.p063.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 to-bigint]]
   [euler.p063.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(def LIMIT 60)

(defn solve []
  (->> (for [base (range 1 LIMIT)
             power (range 1 LIMIT)
             :when (= power (count (str (reduce * (repeat power (to-bigint base))))))]
         true)
       (filter true?)
       count))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
