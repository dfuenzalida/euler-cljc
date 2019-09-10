(ns euler.p049.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest read-string primes]]
   [euler.p049.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))
 
(defn increasing? [xs] ;; is it a sequence increasing at fixed rate?
  (let [x1   (first xs)
        step (- (second xs) x1)
        xn   (+ x1 (* (count xs) step))]
    (= xs (range x1 xn step))))
 
(defn upto3 [xs] ;; given a collection, return collections of up to 3 elems
  (for [c (range (count xs))
        b (range c)
        a (range b)
        :when (< a b c)]
    [(nth xs a) (nth xs b) (nth xs c)]))
 
(defn solve [f]
  (->> (primes)
       (drop-while #(<= % 1000))         ;; drop 3-digit numbers
       (take-while #(<= % 10000))        ;; take only 4-digit numbers
       (group-by (comp sort str))        ;; grouped by having same digits in asc order
       vals                              ;; take the groups
       (sort-by first)
       (filter #(<= 3 (count %)))        ;; keep only groups of at least 3 elems
       (mapcat upto3)                    ;; break into triplets
       (filter increasing?)
       f ;; first or last
       (apply str)
       read-string))
 
(deftest tests
  (is (= (->> [1487 4817 8147] (apply str) read-string)
         (solve first)))
  (is (= (str answer)
         (str (solve last)))))
 
;;;; Scratch
 
(comment
  (t/run-tests)
  )
