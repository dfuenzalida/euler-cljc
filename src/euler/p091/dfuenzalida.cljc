(ns euler.p091.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 primes]]
   [euler.p091.data :refer [answer]]
   [clojure.test :as t :refer [is]]))

(defn slope [xa ya xb yb]
  [(- yb ya) (- xb xa)]) ;; not division, numerator over denominator

(defn right-angle? [x1 y1 x2 y2 x3 y3] ;; (x2, y2) is the vertex of the angle
  (cond
    (and (= x1 x2) (= y2 y3)) true
    (and (= y1 y2) (= x2 x3)) true
    (or  (= x1 x2) (= x2 x3)) false
    :else (let [[s1a s1b] (slope x1 y1 x2 y2)
                [s2a s2b] (slope x2 y2 x3 y3)]
            (= (* -1 s1a s2a) (* s1b s2b)))))

(defn right-triangle? [[x1 y1] [x2 y2] [x3 y3]]
  (->> (cycle [x1 y1 x2 y2 x3 y3])
       (partition 6 2)
       (take 3)
       (map (partial apply right-angle?))
       (some true?)))

(defn div2 [n] (quot n 2))

(defn solve [n]
  (->> (for [x1 (range 0 (inc n))
             y1 (range 0 (inc n))
             x2 (range 0 (inc n))
             y2 (range 0 (inc n))]
         (set [[0 0] [x1 y1] [x2 y2]]))
       (filter #(= 3 (count %))) ;; 3 different points
       ;; set ;; A triangle as a set of 3 points on any order: triangle BCA == triangle ABC
       (filter #(apply right-triangle? %))
       count
       div2))

(deftest tests
  (is (= 14 (solve 2)))
  (is (right-triangle? [0 0] [1 1] [0 2]))
  (is (= (str answer)
         (md5 (str (solve 50))))))

;;;; Scratch
 
(comment
  ; (time (solve 50))
  ; "Elapsed time: 41760.5671 msecs"
  (t/run-tests)
  )

