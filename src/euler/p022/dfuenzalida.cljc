(ns euler.p022.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5]]
   [euler.p022.data :refer [input answer]]
   [clojure.test :as t :refer [is testing]]))

(defn char-score [c]
  #?(:clj (inc (- (int c) (int \A)))
     :cljs (inc (- (.charCodeAt c 0) (.charCodeAt "A" 0)))))

(defn word-score [s]
  (reduce + (map char-score s)))

(defn solve []
  (let [word-scores (->> (sort input) (map word-score))]
    (->>
     (map * word-scores (rest (range))) ;; multiply each score by their order
     (reduce +))))

(deftest tests
  (is (= 1 (char-score \A)))
  (is (= 26 (char-score \Z)))
  (is (= 53 (word-score "COLIN")))
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
