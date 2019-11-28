(ns euler.p075.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 read-string format]]
   [euler.p075.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

;; Produce lots of primitive Pythagorean triples with
;; https://en.wikipedia.org/wiki/Special_right_triangle#Side-based

(defn triangles [p]
  (for [m (range 2 p)
        n (range 1 m)
        :let [m2 (* m m)
              n2 (* n n)
              a  (- m2 n2)
              b  (* 2 m n)
              c  (+ m2 n2)]
        :when (> 1500000 (+ a b c))]
    [a b c]))

(defn multiples [[a b c]] ;; Finds scale copies up to the limit size
  (let [limit (quot 1500000 (max a b c))]
    (for [m (range 1 (inc limit))]
      #{(* m a) (* m b) (* m c)})))

(defn solve []
  (->> (triangles 5000)
       (mapcat multiples)
       (filter (fn [s] (> 1500000 (reduce + s))))
       set
       (map (partial apply +))
       frequencies
       vals
       (filter #{1})
       count))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
