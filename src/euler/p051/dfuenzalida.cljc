(ns euler.p051.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest primes md5]]
   [euler.p051.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(def primes-set
  (delay
   (->> (primes) (take-while #(< % 1000000)) set)))

(def pows-10 [10 100 1000 10000 100000]) ;; powers of ten for extracting digits

;; Given a prime, pick 3 distinct places where digits will be replaced

(defn gen-sets [n]
  (for [d1 (take-while #(< % n) pows-10)
        d2 (take-while #(< % n) pows-10)
        d3 (take-while #(< % n) pows-10)
        :when (< d1 d2 d3)]
    (for [i (range 1 10)
          :let [x (+ n
                     (* -1 d1 (rem (quot n d1) 10))
                     (* -1 d2 (rem (quot n d2) 10))
                     (* -1 d3 (rem (quot n d3) 10))
                     (* i (+ d1 d2 d3)))]]
      x)))

(defn valid-set [xs]
  (let [ps (filter @primes-set xs)]
    (when (> (count ps) 7)
      ps)))

(defn solve []
  (->> (range 10000 3000000)
       (mapcat gen-sets)
       (map valid-set)
       (filter some?)
       ffirst))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
