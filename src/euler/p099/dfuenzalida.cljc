(ns euler.p099.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5]]
   [euler.p099.data :refer [input answer]]
   [clojure.test :as t :refer [is testing]]))

(defn logtimes [base exponent]
  (* (Math/log base) exponent))

(defn solve []
  (->> (partition 2 input) ;; [[base exp] ...]
       (map (partial apply logtimes))
       (map-indexed vector)
       (apply max-key second)
       first
       inc)) ;; index needs to be 1-based

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
