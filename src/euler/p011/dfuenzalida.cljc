(ns euler.p011.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest parse-int]]
   [euler.p011.data :refer [input answer]]
   [clojure.test :as t :refer [is testing]]))

(defn read-input-line [s]
  (->> (re-seq #"\d+" s)
       (mapv parse-int)))

(defn read-input []
  (mapv read-input-line input))

(defn number-groups [vs xmin xmax ymin ymax dx dy]
  (for [x (range xmin xmax)
        y (range ymin ymax)]
    (for [i (range 0 4)]
      (get-in vs [(+ y (* i dy)) (+ x (* i dx))]))))

(defn solve []
  (let [grid   (read-input)
        groups (concat (number-groups grid 0 16 0 20  1 0)   ;; horizontal
                       (number-groups grid 0 20 0 16  0 1)   ;; vertical
                       (number-groups grid 0 16 0 16  1 1)   ;; southeast
                       (number-groups grid 4 20 0 16 -1 1))] ;; southwest
    (->> (map #(apply * %) groups)
         (reduce max))))

(deftest part-1
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests)
)
