(ns euler.p024.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest read-string format]]
   [euler.p024.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn remove-at
  "Returns the vector v without the element at position i"
  [v i]
  (let [m (min i (count v))
        n (min (inc m) (count v))]
    (into (subvec v 0 m) (subvec v n))))

(defn permutate [as xs]
  (if (seq xs)
    (map (fn [i]
           (permutate
            (conj as (xs i))
            (remove-at xs i)))
         (range (count xs)))
    (reduce str as)))

(defn solve []
  (->> (flatten (permutate [] [0 1 2 3 4 5 6 7 8 9]))
       (drop 999999)
       (first)))

(deftest tests
  (is (= ["012" "021" "102" "120" "201" "210"]
         (flatten (permutate [] [0 1 2]))))
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests)
)
