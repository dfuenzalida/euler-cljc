(ns euler.p046.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest primes]]
   [euler.p046.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(def primes-1m (delay (take-while #(<= % 1000000) (primes))))
(def primes-set (delay (set @primes-1m)))

(defn square [n] (* n n))

(defn composites? [n]
  (first
   (for [p (take-while #(<= % n) @primes-1m)
         x (take-while #(<= (* 2 % %) n) (rest (range)))
         :when (= n (+ p (* 2 x x)))]
     n)))

(defn solve []
  (->> (drop 2 (range 10000))
       (filter odd?)
       (remove @primes-set)
       (remove composites?)
       first))

(deftest tests
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests)
)
