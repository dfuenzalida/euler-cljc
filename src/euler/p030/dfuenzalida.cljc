(ns euler.p030.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest read-string md5]]
   [euler.p030.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn fourth [n]
  (* n n n n))

(defn fifth [n]
  (* n n n n n))

(defn make-sum-digits [f] ;; returns a fn that applies f to each digit, sums all
  (fn [n]
    (->> (map (comp f read-string str) (str n))
         (reduce +))))

(defn upper-bound [f]
  (let [sum-digits-fn (make-sum-digits f)]
    (->> [9 99 999 9999 99999 999999 9999999]
         (remove #(<= % (sum-digits-fn %)))
         first)))

(defn solve [f]
  (let [limit (upper-bound f)
        sum-digits (make-sum-digits f)]
    (->> (range 11 limit)
         (filter #(= % (sum-digits %)))
         (reduce +))))

(deftest tests
  (is (= 19316 (solve fourth)))
  (is (= (str answer)
         (md5 (str (solve fifth))))))

;;;; Scratch

(comment
  (t/run-tests)
)
