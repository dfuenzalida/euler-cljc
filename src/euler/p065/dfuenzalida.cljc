(ns euler.p065.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 read-string to-bigint]]
   [euler.p065.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn sqrt2 []
  (map to-bigint (cons 1 (repeat 2))))

(defn coefs-e [] ;; [2 1 2, 1 1 4 1 1 6 1 1 8 1 1 10 ... ]
  (map to-bigint
       (concat [2 1 2]
               (mapcat #(vector 1 1 %)
                       (->> (range) (drop 2) (map (partial * 2)))))))

(defn quot' [a b]
  #?(:clj  (quot a b)
     :cljs (/ (to-bigint a) (to-bigint b))))

(defn mod' [a b]
  (let [[a' b'] (map to-bigint [a b])]
    (- a' (* (quot' a' b') b'))))

(defn gcd [a b]
  (if (= (to-bigint 0) b) a (recur b (mod' a b))))

(def reciprocal (juxt second first))

(defn frac+ [[a b] [c d]] ;; a/b + c/d = (ad + cb)/bd
  (let [numer (+ (* a d) (* c b))
        denom (* b d)
        cdiv  (gcd numer denom)]
    [(quot' numer cdiv) (quot' denom cdiv)]))

(defn convergent [xs]
  (if (seq xs)
    (frac+ [(first xs) (to-bigint 1)] (reciprocal (convergent (rest xs))))
    (mapv to-bigint [1 0]))) ;; [1 0]'s reciprocal is 0/1 (useful for edge cases)

(defn solve []
  (->> (convergent (take 100 (coefs-e)))
       first
       str
       (map (comp read-string str))
       (reduce +)))

(deftest tests
  (is (= [3363 2378] (mapv (comp read-string str) (convergent (take 10 (sqrt2))))))
  (is (= [1457 536]  (mapv (comp read-string str) (convergent (take 10 (coefs-e))))))
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
