(ns euler.p097.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 read-string format]]
   [euler.p097.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn ten-digits [n]
  (rem n 10000000000))

;; Compute the last 10 digits of 28433×2^7830457+1
(defn solve []
  (let [powerof2 (reduce * (repeat 32 2)) ;; multiplying by these powers of 2 at a time
        [q r]    ((juxt quot rem) 7830457 powerof2)]
    (->> (concat [28433] (repeat r 2) (repeat q powerof2))
         (reduce (comp ten-digits *))
         inc
         ten-digits)))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
