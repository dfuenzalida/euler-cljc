(ns euler.p125.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 read-string format]]
   [euler.p125.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn palindrome? [n]
  (= (seq (str n)) (reverse (seq (str n)))))

(defn squares-up-to [n]
  (filter #(<= % n) (map #(* % %) (rest (range n)))))

(defn sums-of-squares [n]
  (let [sums (->> (squares-up-to n) (reductions +) (into [0]))] ;; leading 0
    (->> (for [i (range 2 (count sums)) ;; at least the sum of 2 squares
               j (range (dec i))]       ;; remove up to the (i-2)th sum
           (- (sums i) (sums j)))
         (filter #(<= % n)))))

(defn solve [n]
  (->> (sums-of-squares n)
       (filter palindrome?)
       set ;; IMPORTANT!
       (reduce +)))

(deftest tests
  (is (= 4164 (solve 1000)))
  (is (= (str answer)
         (md5 (str (solve 100000000))))))

;;;; Scratch

(comment
  (t/run-tests)
)
