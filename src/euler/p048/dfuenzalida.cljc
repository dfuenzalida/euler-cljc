(ns euler.p048.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5]]
   [euler.p048.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(def ten-zeros 10000000000)

(defn clamp-pow [n e]
  (reduce (fn [acc x] (rem (* acc x) ten-zeros)) (repeat e n)))

(defn solve [n]
  (rem (reduce + (map #(clamp-pow % %) (range 1 (inc n))))
       ten-zeros))

(deftest tests
  (is (= (rem 10405071317 ten-zeros) (solve 10)))
  (is (= (str answer)
         (md5 (str (solve 1000))))))

;;;; Scratch

(comment
  (t/run-tests)
)
