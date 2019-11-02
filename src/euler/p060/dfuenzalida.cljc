(ns euler.p060.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 primes parse-int read-string]]
   [euler.p060.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

;; There's no quick solution to this problem. It takes about 2 minutes
;; on my laptop in the JVM and about 3 on Node.

(def limit 10000)

(def primes-1m
  (->> (primes) (take-while #(< % limit)) rest vec delay)) ;; rest drops '2'

(defn prime? [n]
  (let [factors (take-while #(<= (* % %) n) @primes-1m)]
    (empty?
     (filter #(zero? (rem n %)) factors))))

(defn concat-nums [a b]
  (parse-int (str a b)))

(defn find-group [group-size ps]
  (loop [pgs [] upgs [] ps ps] ;; processed groups, unprocessed groups, primes
    (if (seq ps)
      (let [p (first ps)]
        (if (seq upgs)
          (let [upg (first upgs)
                upg-cc (concat (map #(concat-nums p %) upg)
                               (map #(concat-nums % p) upg))]
            (if (every? prime? upg-cc)
              (let [newg (conj upg p)]
                (if (= (count newg) group-size)
                  newg ;; found, return
                  (recur (into [newg upg] pgs) (rest upgs) ps))) ;; group not large enough
              ;; p doesn't belong in the group
              (recur (conj pgs upg) (rest upgs) ps)))
          ;; p didn't belong to any group, goes into it's own group
          (recur [] (conj pgs [p]) (rest ps))))
      (filter #(<= 4 (count %)) upgs) ;; nothing found, report the best candidates
      )))

(defn solve [group-size ps]
  (->> (find-group group-size ps)
       (reduce +)))

(deftest tests
  (is (= 792 (solve 4 @primes-1m)))
  (is (= (str answer)
         (md5 (str (solve 5 @primes-1m))))))

;;;; Scratch

(comment
  (t/run-tests)
  )
