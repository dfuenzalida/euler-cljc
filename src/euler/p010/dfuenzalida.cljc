(ns euler.p010.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest primes md5]]
   [euler.p010.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn solve []
  (->> (primes)
       (take-while #(< % 2000000))
       (reduce +)))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
