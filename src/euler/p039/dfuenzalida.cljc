(ns euler.p039.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest read-string format]]
   [euler.p039.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn triangles [p] ;; return only right angle triangles
  (for [b (range 1 (quot p 2))
        a (range 1 b)
        :let [c (- p a b)]
        :when (= (* c c) (+ (* a a) (* b b)))]
    [a b c]))

(defn solve []
  (->> (map (juxt identity (comp count triangles)) (range 12 1001)) ;; eg. [120 3]
       (reduce conj {})
       (apply max-key val)
       key))

(deftest tests
  (is (= #{[20 48 52] [24 45 51] [30 40 50]}
         (set (triangles 120))))
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests)
)
