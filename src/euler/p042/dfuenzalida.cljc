(ns euler.p042.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5]]
   [euler.p042.data :refer [input answer]]
   [clojure.test :as t :refer [is testing]]))

(defn triangle-numbers [n] ;; compute triangle numbers less or equal to n
  (->> (map #(/ (* % (inc %)) 2) (rest (range)))
       (take-while #(<= % n))))

(defn char-score [c]
  #?(:clj (inc (- (int c) (int \A)))
     :cljs (inc (- (.charCodeAt c 0) (.charCodeAt "A" 0)))))

(defn word-score [word]
  (reduce + (map char-score word)))

(defn triangle-word? [triangles word]
  (triangles word))

(defn solve []
  (let [longest-word (apply max-key count input)
        score-limit  (reduce + (repeat (count longest-word) (word-score "Z")))
        triangles    (set (triangle-numbers score-limit))]
    (->> (map word-score input)
         (filter (partial triangle-word? triangles))
         count)))

(deftest tests
  (is (= [1, 3, 6, 10, 15, 21, 28, 36, 45, 55] (triangle-numbers 55)))
  (is (= 55 (word-score "SKY")))
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
