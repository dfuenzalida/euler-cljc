(ns euler.p014.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5]]
   [euler.p014.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(declare collatz-memo)

(defn collatz [n]
  (if (= 1 n)
    1
    (if (even? n)
      (inc (collatz-memo (/ n 2)))
      (inc (collatz-memo (inc (* 3 n)))))))

(def collatz-memo (memoize collatz))

(defn max-second [[a b] [c d]]
  (if (> b d) [a b] [c d]))

(defn solve []
  (let [pairs (map (juxt identity collatz-memo) (range 1 1000001))]
    (first
     (reduce max-second pairs))))

(deftest part-1
  (is (= 10 (collatz-memo 13)))
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
