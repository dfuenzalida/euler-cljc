(ns euler.p073.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5]]
   [euler.p073.data :refer [answer]]
   [clojure.test :as t :refer [is]]))

(defn gcd [a b]
  (if (zero? b)
    a
    (recur b (mod a b))))

(defn proper-fraction? [a b]
  (= 1 (gcd a b)))

(defn between? [n d] ;;  (< 1/3 n/d 1/2) without actual ratios
  (< (* 2 n) d (* 3 n)))

(defn solve [n]
  (->> (for [d (range 4 (inc n))
             i (range (int (/ d 3)) (inc (int (/ d 2))))]
         [i d])
       (filter (partial apply between?))
       (filter (partial apply proper-fraction?))
       count))

(deftest tests
  (is (= 3 (solve 8)))
  (is (= (str answer)
         (md5 (str (solve 12000))))))

;;;; Scratch

(comment
  (t/run-tests)
)
