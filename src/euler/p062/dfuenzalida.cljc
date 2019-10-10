(ns euler.p062.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 to-bigint format]]
   [euler.p062.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn solve []
  (loop [n 1
         perms {}] ;; map from "01234566" to [345 384 405]
    (let [bn   (to-bigint n)
          cube (* bn bn bn)
          k  (apply str (sort (str cube))) ;; compute as string, sort digits
          nums (get perms k [])]
      (if (= (count nums) 4) ;; 4 existing + 1 new
        (let [x (to-bigint (first nums))]
          (* x x x)) ;; return the cube of the first element
        (recur
         (inc n)
         (conj perms [k (into nums [n])]))))))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
