(ns euler.p018.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest parse-int]]
   [euler.p018.data :refer [small-input input answer]]
   [clojure.test :as t :refer [is testing]]))

(defn to-vectors [strings]
  (mapv #(mapv parse-int (re-seq #"\d+" %)) strings))

;; Given a sequence of vectors and a path, lookup the number in a position
;; by its index, then use the last bit of the path to move the index to the
;; right or not, until no vectors remain, returning the sequence of numbers found

(defn numbers-seq
  ([vectors path] (numbers-seq vectors 0 path))
  ([vectors index path]
   (when-let [v (first vectors)]
     (cons (v index)
           (numbers-seq (rest vectors)
                        (+ index (rem path 2))
                        (quot path 2))))))

(defn find-max [vectors]
  (let [num-paths (reduce * (repeat (dec (count vectors)) 2))]
    (->> (range num-paths)                   ;; for every route
         (map (partial numbers-seq vectors)) ;; compute the sequences
         (map #(reduce + %))                 ;; compute their sum
         (reduce max))))                     ;; find maximum

(defn solve [strings]
  (find-max (to-vectors strings)))

(deftest part-1
  (is (= (to-vectors small-input) [[3] [7 4] [2 4 6] [8 5 9 3]]))
  (is (= (numbers-seq (to-vectors small-input) 0) [3 7 2 8])) ;; leftmost branch
  (is (= (numbers-seq (to-vectors small-input) 7) [3 4 6 3])) ;; rightmost branch
  (is (= 23 (solve small-input)))
  (is (= (str answer)
         (str (solve input)))))

;;;; Scratch

(comment
  (t/run-tests)
)
