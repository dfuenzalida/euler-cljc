(ns euler.p040.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest parse-int md5]]
   [euler.p040.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn all-digits
  ([] (all-digits [] 0))
  ([digits n] (if (seq digits)
                (lazy-seq (cons (first digits)
                                (all-digits (rest digits) n)))
                (recur (str n) (inc n)))))

(defn solve []
  (let [indexes #{1 10 100 1000 10000 100000 1000000}]
    (->> (map vector (range) (take 1000001 (all-digits))) ;; extra item => simpler code
         (filter #(indexes (first %)))
         (map (comp parse-int str second)) ;; extract the values
         (reduce *))))

(deftest tests
  (is (= \1 (last (take 13 (all-digits))))) ;; 13, not 12 because of extra digit
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
