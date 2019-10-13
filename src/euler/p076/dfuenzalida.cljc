(ns euler.p076.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 read-string format]]
   [euler.p076.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

;; This is essentually problem 31 with different coins and memoization

(declare waysm)

(defn ways [cs n]
  (if (seq cs)
    (if (zero? n)
      1
      (if (neg? n)
        0
        (+ (waysm cs (- n (first cs)))
           (waysm (rest cs) n))))
    0))

(def waysm (memoize ways))

(defn solve [n]
  (ways (range 1 n) n))

(deftest tests
  (is (= 6 (solve 5)))
  (is (= (str answer)
         (md5 (str (solve 100))))))

;;;; Scratch

(comment
  (t/run-tests)
)
