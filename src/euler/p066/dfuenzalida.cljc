(ns euler.p066.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 read-string to-bigint]]
   [euler.p066.data :refer [answer]]
   [clojure.test :as t :refer [is]]))

;; The overall solution can be computed as:
;; - For a given D, good candidates for X and Y are the numerator and denominator
;;   for x/y that are the convergents of computing the square root of D
;; See https://en.wikipedia.org/wiki/Pell%27s_equation

(defn not-squares ;; Lazy sequence of all numbers that are not a square
  ([] (not-squares 0 (range)))
  ([i xs] (let [n (- (* i i) (* (dec i) (dec i)))]
            (lazy-cat (take (dec n) (rest xs))
                      (not-squares (inc i) (drop n xs))))))

;; See https://en.wikipedia.org/wiki/Methods_of_computing_square_roots#Algorithm

(defn coeficients ;; lazy seq of a[n] in a[0] + (1/(a[1] + (1/a[2] + ( 1/a[3] ...
  ([s] (let [a (int (Math/sqrt s))]
         (coeficients s 0 1 a a)))
  ([s m d a a0]
   (cons a
         (lazy-seq
          (let [m' (- (* d a) m)
                d' (/ (- s (* m' m')) d)
                a' (long (/ (+ a0 m') d'))]
            (coeficients s m' d' a' a0))))))

(defn gcd [a b]
  (if (= (to-bigint 0) b) a (recur b (mod a b))))

(defn quot' [a b]
  (to-bigint (/ (to-bigint a) (to-bigint b))))

(defn gcd-frac [num den]
  (let [g (gcd num den)]
    [(quot' num g) (quot' den g)]))

(defn frac+ [[a b] [c d]]
  (let [[a b c d] (map to-bigint [a b c d])]
    (gcd-frac (+ (* a d) (* b c)) (* b d))))

(defn recip [[a b]] [b a])

(defn as-frac [coefs] ;; given a list of coeficients, returns the fraction as a pair
  (let [coefs (map to-bigint coefs)
        n (dec (count coefs))]
    (loop [n n, x [(nth coefs n) 1]]
      (if (zero? n)
        x
        (recur (dec n)
               (frac+ [(nth coefs (dec n)) 1] (recip x)))))))

(defn good-pair? [x y n]
  (let [[x y n] (map to-bigint [x y n])]
    (= (to-bigint 1) (- (* x x) (* n y y)))))

(defn solve-for [n]
  (loop [i 1]
    (let [[x y] (as-frac (take i (coeficients n)))]
      (if (or (good-pair? x y n) (> i 1000))
        [n x y i]
        (recur (inc i))))))

(defn solve [n]
  (->> (take-while #(<= % n) (not-squares))
       (map solve-for)
       (apply max-key second)
       first str read-string))

(deftest tests
  (is (= 5 (solve 7)))
  (is (= (str answer)
         (md5 (str (solve 1000))))))

;;;; Scratch

(comment
  (t/run-tests)
)
