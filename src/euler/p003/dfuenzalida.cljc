(ns euler.p003.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest primes md5]]
   [euler.p003.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn solve [n]
  (let [divisor (first (filter #(zero? (rem n %)) (primes)))]
    (if (= divisor n)
      n
      (recur (/ n divisor)))))

(deftest tests
  (is (= 29 (solve 13195)))
  (is (= (str answer)
         (md5 (str (solve 600851475143))))))

;;;; Scratch

(comment
  (t/run-tests)
)
