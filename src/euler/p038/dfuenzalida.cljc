(ns euler.p038.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest parse-int md5]]
   [euler.p038.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn best-product [n] ;; find the concatenation of products closer to 9 digits
  (->> (map #(str (* n %)) (range 1 10))
       (reductions str)
       (take-while #(<= (count %) 9))
       last
       parse-int))

(def all-digits (set "123456789"))

(defn pandigital? [n]
  (let [s (str n)]
    (and (= 9 (count s))
         (= (set s) all-digits))))

(defn solve []
  (->> (map best-product (range 1 10000)) ;; 10000 already has 5 digits
       (filter pandigital?)
       (reduce max)))

(deftest tests
  (is (= 192384576 (best-product 192)))
  (is (pandigital? (best-product 192)))
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
