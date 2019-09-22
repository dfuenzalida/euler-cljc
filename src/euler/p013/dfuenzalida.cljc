(ns euler.p013.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest to-bigint md5]]
   [euler.p013.data :refer [input answer]]
   [clojure.test :as t :refer [is testing]]))

(defn solve []
  (let [numbers (map to-bigint input)]
    (-> (reduce + numbers)
        str
        (subs 0 10))))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
  )
