(ns euler.p020.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest to-bigint parse-int md5]]
   [euler.p020.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn solve []
  (->> (range 1 101)              ;; 1 to 100
       (map to-bigint)            ;; as bigints
       (reduce *)                 ;; 100! as bigint
       str
       (map (comp parse-int str)) ;; digits
       (reduce +)))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
