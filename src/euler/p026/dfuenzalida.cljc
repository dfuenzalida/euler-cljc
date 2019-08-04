(ns euler.p026.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest read-string format]]
   [euler.p026.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

;; this number needs to be large enough for numbers with large periodic seqs
(def limit 3000)

(defn decimals
  ([divisor] (decimals 1 divisor))
  ([remainder divisor]
   (let [digit      (quot (* 10 remainder) divisor)
         remainder2 (rem  (* 10 remainder) divisor)]
     (if (zero? remainder2)
       [digit]
       (cons digit
             (lazy-seq (decimals remainder2 divisor)))))))

(defn decimals-vector [divisor]
  (vec (take limit (decimals divisor)))) ;; up to `limit` decimals

(defn max-period
  "Checks if decs-string contains a period of more than maxlength digits.
  If so, keeps track of number of digits and index."
  [[maxlength index] [decs-string i]]
  (let [finder (re-find #"([0-9]*?)([0-9]+?)\2\2([0-9]+?)" decs-string)
        period (finder 2)] ;; second group in the regex
    (if (> (count period) maxlength)
      [(count period) i]
      [maxlength index])))

(defn solve [n]
    (->> (range 2 (inc n))
         (map (juxt decimals-vector identity)) ;; [[decimal index] ...]
         (remove #(not= limit (count (first %)))) ;; drop pairs w/o enough decs
         (map (fn [[decs i]] [(reduce str decs) i]))
         (reduce max-period [0 0]) ;; find the best pair of [maxlength index]
         second))

(deftest part-1
  (is (= 7 (solve 10))) ;; 1/7 has the largest period from 1/2 to 1/10
  (is (= (str answer)
         (str (solve 1000)))))

;;;; Scratch

(comment
  (t/run-tests)
)
