(ns euler.p031.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest read-string format]]
   [euler.p031.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(def coins [1 2 5 10 20 50 100 200])

(defn ways [cs n]
  (if (seq cs)
    (if (zero? n)
      1
      (if (neg? n)
        0
        (+ (ways cs (- n (first cs)))
           (ways (rest cs) n))))
    0))

(defn solve [n]
  (ways coins n))

(deftest tests
  (is (= 1 (solve 1)))
  (is (= 2 (solve 2)))
  (is (= (str answer)
         (str (solve 200)))))

;;;; Scratch

(comment
  (t/run-tests)
)
