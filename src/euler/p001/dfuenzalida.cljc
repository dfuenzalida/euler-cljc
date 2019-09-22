(ns euler.p001.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest read-string format md5]]
   [euler.p001.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn solve []
  (->> (range 1000)
       (filter #(or (zero? (rem % 3)) (zero? (rem % 5))))
       (reduce +)))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
