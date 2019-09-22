(ns euler.p029.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest primes to-bigint md5]]
   [euler.p029.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Naive and fast enough but it fails on ClojureScript, will checked later
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; (defn pow [a b]
;;   (reduce * (repeat b (to-bigint a))))

;; (defn solve [n]
;;   (->> (for [a (range 2 (inc n))
;;              b (range 2 (inc n))]
;;          (pow a b))
;;        (reduce conj #{})
;;        count))

(defn factors-map [a b]
  (let [small-primes (take-while #(<= % a) (primes))]
    (loop [factors [] a a]
      (if (> a 1)
        (let [divisor (first (filter #(zero? (rem a %)) small-primes))]
          (recur (conj factors divisor) (/ a divisor)))
        (let [freqs (frequencies factors)]
          (into {}
                (map (fn [[factor power]] [factor (* power b)]) freqs)))))))

(defn solve [n]
  (->> (for [a (range 2 (inc n))
             b (range 2 (inc n))]
         (factors-map a b))
       (into #{})
       count))

(deftest tests
  (is (= 15 (solve 5)))
  (is (= (str answer)
         (md5 (str (solve 100))))))

;;;; Scratch

(comment
  (t/run-tests)
)
