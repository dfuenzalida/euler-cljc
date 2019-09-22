(ns euler.p007.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest primes md5]]
   [euler.p007.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn solve [n]
  (nth (primes) (dec n))) ;; `dec` because indexes start at 0

(deftest tests
  (is (= 13 (solve 6)))
  (is (= (str answer)
         (md5 (str (solve 10001))))))

;;;; Scratch

(comment
  (t/run-tests)
)
