(ns euler.p037.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest parse-int primes md5]]
   [euler.p037.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn sub-nums [n]
  (let [s      (map str (str n))
        taking (map #(reduce str (take % s)) (range 1 (count s)))
        dropin (map #(reduce str (drop % s)) (range (count s)))]
    (map parse-int (concat taking dropin))))

(defn interesting? [ps n]
  (every? ps (sub-nums n)))

(defn solve []
  (let [ps     (take-while #(<= % 1000000) (primes)) ;; precalc primes under 1M
        ps-set (set ps)]
    (->> (drop-while #(<= % 7) ps) ;; primes up to 7 are not considered
         (filter (partial interesting? ps-set))
         (reduce +))))

(deftest tests
  (is (interesting? (set (take 1000 (primes))) 3797))
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
