(ns euler.p043.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5]]
   [euler.p043.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn unique-digits? [n]
  (= #{1} (set (vals (frequencies (str n))))))

(defn find-pandigitals [candidate divisors digits-divisor]
  (if (seq divisors)
    (let [divisor       (first divisors)
          last-digits   (quot candidate digits-divisor)
          subcandidates (->> (range 10)
                             (map #(+ (* 100 digits-divisor %) candidate))           ;; add new digit on the front
                             (filter #(zero? (rem (quot % digits-divisor) divisor))) ;; divides correctly
                             (filter unique-digits?))]                               ;; not repeating digits
      (when (seq subcandidates)
        (mapcat #(find-pandigitals % (rest divisors) (* 10 digits-divisor)) subcandidates)))
    (when (= 10 (count (str candidate))) ;; when it's a 10-digit pandigital
      [candidate])))

(defn solve []
  (->> (mapcat
        #(find-pandigitals % [13 11 7 5 3 2 1] 10)
        (rest (range 0 1000 17))) ;; kick off with all multiples of 17 up to 1000
       (reduce +)))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
