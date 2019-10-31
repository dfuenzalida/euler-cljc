(ns euler.p206.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest to-bigint md5]]
   [euler.p206.data :refer [answer]]
   [clojure.test :as t :refer [is]]))

;; These functions are required because the solution requires arithmetic of numbers that are not safe
;; integers in JavaScript. The performance hit for using BigInts in JS is huge, though.
;; 
;; https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Number/isSafeInteger

(defn quot' [a b]
  #?(:clj  (quot a b)
     :cljs (/ (to-bigint a) (to-bigint b))))

(defn mod' [a b]
  #?(:clj  (mod a b)
     :cljs (let [[a' b'] (map to-bigint [a b])]
             (- a' (* (quot' a' b') b')))))

(defn square [n]
  #?(:clj  (* n n)
     :cljs (let [m (to-bigint n)]
             (* m m))))

(defn valid? [n]
  (loop [n n x 9]
    (if (pos? x)
      (let [q (quot' n 100)]
        (if (= (to-bigint x) (to-bigint (mod' q 10)))
          (recur q (dec x))
          false))
      true)))

(defn solve []
  (let [a (-> (Math/sqrt 1020304050607080900) ;; minimum possible
              int (quot 10) (* 10))]
    (loop [a a, i 1]
      (let [sq (square a)]
        (if (valid? sq)
          a
          (recur (+ 10 a) (inc i)))))))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
  )
