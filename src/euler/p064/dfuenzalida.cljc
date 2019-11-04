(ns euler.p064.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 read-string format]]
   [euler.p064.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

;; Method for computing coeficients from
;; http://en.wikipedia.org/wiki/Methods_of_computing_square_roots
;; #Continued_fraction_expansion

(defn a0 [n]
  (last (filterv #(< (* % %) n) (range 1 n))))

(defn coeficients [x]
  (loop [m 0 d 1 a (a0 x) coefs []]
    (if (= 20 (count coefs)) ;; up to 400 digits
      coefs
      (let [m2 (- (* d a) m)
            d2 (quot (- x (* m2 m2)) d)
            a2 (quot (+ (a0 x) m2) d2)]
        (recur m2 d2 a2 (conj coefs a2))))))

;; compare two copies of the period digits, find where they are equal
(defn period-length [digits]
  (loop [length 1 d1, (drop 1 digits), d2 (butlast digits)]
    (if (seq d1)
      (if (= d1 d2)
        length
        (recur (inc length) (drop 1 d1) (butlast d2)))
      length)))

(def first-200-sqs
  (delay (set (map #(* % %) (range 1 200)))))

(defn solve [n]
  (->> (filter (comp nil? @first-200-sqs) (range 1 (inc n)))
       (filter (comp odd? period-length coeficients))
       count))

(deftest tests
  (is (= 4 (solve 13)))
  (is (= (str answer)
         (md5 (str (solve 10000))))))

;;;; Scratch

(comment

  ;; Flame graphs
  (require '[flames.core :as flames])
  (def flames (flames/start! {:port 54321, :host "localhost"}))
  ;; http://localhost:54321/flames.svg
  (time (solve 10000))
  (flames/stop! flames)

  (t/run-tests)
)
