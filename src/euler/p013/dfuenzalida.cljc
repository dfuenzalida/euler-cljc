(ns euler.p013.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest]]
   [euler.p013.data :refer [input answer]]
   [clojure.test :as t :refer [is testing]]))

(defn to-bigint [x]
  #?(:clj  x
     :cljs (js/BigInt x)))

(defn solve []
  (let [numbers (map to-bigint input)]
    (-> (reduce + numbers)
        str
        (subs 0 10))))

(deftest part-1
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests)
  )
