(ns euler.p044.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5]]
   [euler.p044.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn pentagonals []
  (map (fn [n] (/ (* n (dec (* 3 n))) 2)) (rest (range))))

;; limit found increasing by powers of ten through different rounds.
;; Looping over it in the code caused Node to crash, unable to GC enough memory.
;; The JVM did fine though.

(def limit 10000000)

(defn solve []
  (let [pentagonals-seq (take-while #(<= % limit) (pentagonals))
        pentagonals-set (set pentagonals-seq)]
    (first
     (for [x pentagonals-seq
           y (take-while #(< % x) pentagonals-seq)
           :let [diff (- x y)]
           :when (every? pentagonals-set [(+ x y) diff])]
       diff))))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests))
