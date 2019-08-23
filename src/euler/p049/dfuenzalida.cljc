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

(defn solve []
  (let [ps (->> (primes)
                (drop-while #(<= % 1000))
                (take-while #(<= % 10000)))]  ;; 4-digit primes
    (->> (group-by (comp sort str) ps)        ;; grouped by digits
         vals                                 ;; groups having same digits
         (sort-by first)
         (filter #(<= 3 (count %)))           ;; groups of at least 3
         (mapcat (partial partition 3 1))     ;; break into triplets
         (filter increasing?)
         last
         (apply str)
         read-string)))

(deftest tests
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests))
