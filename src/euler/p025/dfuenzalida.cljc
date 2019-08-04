(ns euler.p025.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest to-bigint]]
   [euler.p025.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn fibs []
  (let [big-one (to-bigint 1)]
    (map first (iterate (fn [[a b]] [b (+ a b)]) [big-one big-one]))))

(defn solve [n]
  (->> (map vector (fibs) (rest (range)))
       (drop-while (fn [[x i]] (< (count (str x)) n)))
       first    ;; first new pair of [value, index]
       second)) ;; index

(deftest tests
  (is (= 12 (solve 3)))
  (is (= (str answer)
         (str (solve 1000)))))

;;;; Scratch

(comment
  (t/run-tests)
)
