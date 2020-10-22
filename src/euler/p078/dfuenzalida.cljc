(ns euler.p078.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 to-bigint]]
   [euler.p078.data :refer [answer]]
   [clojure.test :as t :refer [is]]))

;; https://en.wikipedia.org/wiki/Partition_function_(number_theory)#Recurrence_relations

(def alternating ;; 1, -1, 2, -2, 3, -3 ...
  (drop 2 (interleave (range) (map (partial * -1) (range)))))

(defn penta [n]
  (to-bigint (/ (* n (dec (* 3 n))) 2)))

(defn not-neg? [n]
  (>= n 0))

(defn quot' [a b]
  #?(:clj  (quot a b)
     :cljs (/ (to-bigint a) (to-bigint b))))

(defn mod' [a b]
  (let [[a' b'] (map to-bigint [a b])]
    (- a' (* (quot' a' b') b'))))

(def big0  (to-bigint 0))
(def big1  (to-bigint 1))
(def big-1 (to-bigint -1))

(declare waysm)

(defn ways [n]
  (let [bign (to-bigint n)]
    (cond
      (< bign big0) big0
      (= big0 bign) big1
      :else (let [ks (map (comp to-bigint penta) alternating)
                  xs (take-while not-neg? (map (partial - bign) ks))
                  ws (map * (map waysm xs) (cycle [big1 big1 big-1 big-1]))]
              (reduce + ws)))))

(def waysm (memoize ways))

(defn solve [n]
  (let [bign (to-bigint n)]
    (->> (drop 2 (range))
         (map (juxt identity ways))
         (filter #(= big0 (mod' (second %) bign)))
         ffirst)))

(deftest tests
  (is (= "7" (str (ways 5))))
  (is (= (str answer)
         (md5 (str (solve 1000000))))))

;;;; Scratch

(comment
  (t/run-tests)
)
