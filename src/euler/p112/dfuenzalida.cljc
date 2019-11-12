(ns euler.p112.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5]]
   [euler.p112.data :refer [answer]]
   [clojure.test :as t :refer [is]]))
 
(defn monotone? [f n] ;; eg. 134468
  (loop [n n, prev (mod n 10)]
    (if (zero? n)
      true
      (let [digit (mod n 10)]
        (if (f digit prev)
          (recur (quot n 10) digit)
          false)))))
 
(defn increasing? [n] (monotone? <= n))
(defn decreasing? [n] (monotone? >= n))
(defn bouncy? [n] (not (or (increasing? n) (decreasing? n))))
 
(defn solve [goal]
  (reduce
   (fn [acc n]
     (if (bouncy? n)
       (if (>= (/ (inc acc) n) goal)
         (reduced n)
         (inc acc))
       acc))
   (range)))
 
(deftest tests
  (is (increasing? 134468))
  (is (increasing? 12))
  (is (decreasing? 66420))
  (is (bouncy? 155349))
  (is (= 538 (solve 0.5)))
  (is (= 21780 (solve 0.9)))
  (is (= (str answer)
           (md5 (str (solve 0.99))))))
 
;;;; Scratch
 
(comment
  (t/run-tests))
