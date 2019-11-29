(ns euler.p094.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5]]
   [euler.p094.data :refer [answer]]
   [clojure.test :as t :refer [is]]))

;; See https://en.wikipedia.org/wiki/Integer_triangle#Isosceles_Heronian_triangles

(defn triangles1 [n]
  (for [u (range 2 (int (Math/sqrt n)))
        v (range (if (even? u) 1 2) u 2)
        :let [a  (* 2 (- (* u u) (* v v)))
              bc (+ (* u u) (* v v))]
        :when (#{-1 1} (- a bc))]
    [a bc bc]))

(defn triangles2 [n]
  (for [u (range 2 (int (Math/sqrt n)))
        v (range (if (even? u) 1 2) u 2)
        :let [a  (* 4 u v)
              bc (+ (* u u) (* v v))]
        :when (#{-1 1} (- a bc))]
    [a bc bc]))

(defn solve []
  (let [one-billion (* 1000 1000 1000)]
    (->> (concat (triangles1 one-billion) (triangles2 one-billion))
         (filter (fn [[a b c]] (< (+ a b c) one-billion)))
         (mapcat seq)
         (reduce +))))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
