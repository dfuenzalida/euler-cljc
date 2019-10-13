(ns euler.p077.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 primes]]
   [euler.p077.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(declare waysm)

(defn ways [cs n]
  (if (seq cs)
    (if (zero? n)
      1
      (if (neg? n)
        0
        (+ (waysm cs (- n (first cs)))
           (waysm (rest cs) n))))
    0))

(def waysm (memoize ways))

(defn under-5k-ways? [n]
  (> 5000
     (let [ps (take-while #(< % n) (primes))]
       (ways ps n))))

(defn solve []
  (->> (drop 2 (range))
       (drop-while under-5k-ways?)
       first))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
