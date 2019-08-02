(ns euler.p019.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest]]
   [euler.p019.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn leap? [year]
  (or (zero? (rem year 400))
      (and (pos? (rem year 100))
           (zero? (rem year 4)))))

(defn months [year]
  [31 (if (leap? year) 29 28) 31 30 31 30 31 31 30 31 30 31])

(defn months-seq [start-year end-year]
  (mapcat months (range start-year (inc end-year))))

(defn count-sundays [[sundays offset] n]
  (let [remainder (rem (+ offset n) 7)]
    (if (zero? remainder)
      [(inc sundays) 0]
      [sundays remainder])))

(defn solve []
  (let [;; Sundays of the year 1900 only
        sundays1900 (first
                     (reduce count-sundays [0 1] (months-seq 1900 1900)))
        ;; Sundays between 1900 and 2000
        sundays     (first
                     (reduce count-sundays [0 1] (months-seq 1900 2000)))]
    (- sundays sundays1900)))

(deftest tests
  (is (leap? 2016))
  (is (= false (leap? 1900)))
  (is (leap? 2000))
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests)
)
