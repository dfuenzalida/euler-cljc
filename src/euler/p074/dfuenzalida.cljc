(ns euler.p074.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 read-string format]]
   [euler.p074.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(def fact-map ;; note that 0! = 1 in this case
  {\0 1, \1 1, \2 2, \3 6, \4 24, \5 120, \6 720, \7 5040, \8 40320, \9 362880})

(defn fact-sum [n]
  (reduce + (map fact-map (str n))))

(defn add-entry [m n]
  (if (m n)
    m
    (let [n' (fact-sum n)]
      (recur (assoc m n n') n'))))

(defn make-map [] ;; takes ~2 secs to compute
  (reduce add-entry {} (range 1000000)))

(defn exact-length? [m limit start] ;; exactly `limit` elements starting on `start`
  (loop [start start visited #{}]
    (if (visited start)
      (= limit (count visited))
      (recur (m start) (conj visited start)))))

(defn solve [n]
  (->> (range n)
       (filter (partial exact-length? (make-map) 60))
       count))

(deftest tests
  (is (exact-length? (add-entry (sorted-map) 69) 5 69))
  (is (= (str answer)
         (md5 (str (solve 1000000))))))

;;;; Scratch

(comment
  (t/run-tests)
)
