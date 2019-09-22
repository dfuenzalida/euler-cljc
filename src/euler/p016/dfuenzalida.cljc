(ns euler.p016.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest to-bigint parse-int md5]]
   [euler.p016.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn two-power [n]
  (reduce * (repeat n (to-bigint 2))))

;; (two-power 15)

(defn solve [n]
  (->> (two-power n)
       str
       (map (comp parse-int str))
       (reduce +)))

(deftest tests
  (is (= 26 (solve 15)))
  (is (= (str answer)
         (md5 (str (solve 1000))))))

;;;; Scratch

(comment
  (t/run-tests)
)
