(ns euler.p015.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest to-bigint md5]]
   [euler.p015.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn factorial [n]
  (let [bigints (map to-bigint (range 1 (inc n)))]
    (reduce * bigints)))

(defn solve [n]

  ;; In a grid of NxN, valid movements are sequences of 2N movements where N of these
  ;; are moving downwards and the other N are forcibly to the right. So, we choose from
  ;; N permutations of 2N movements.

  (/ (factorial (* 2 n)) (* (factorial n) (factorial n))))

(deftest tests
  (is (= (to-bigint 720) (factorial 6)))
  (is (= (to-bigint 6)   (solve 2)))
  (is (= (to-bigint 20)  (solve 3)))
  (is (= (str answer)
         (md5 (str (solve 20))))))

;;;; Scratch

(comment
  (t/run-tests)
)
