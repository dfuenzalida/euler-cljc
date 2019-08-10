(ns euler.p032.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest read-string format]]
   [euler.p032.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(def digits (set "123456789"))

(defn pandigital? [[prod a b]]
  (let [xs (str a b prod)]
    (and (= 9 (count xs))
         (= digits (set xs)))))

(defn solve []
  (->> (filter pandigital? (for [x (range 2 5000)
                                 y (range 2 x)] ;; 2 x 5000 is too many digits
                             [(* x y) x y]))
       (map first)
       set
       (reduce +)))

(deftest tests
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests)
)
