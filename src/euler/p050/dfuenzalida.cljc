(ns euler.p050.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest primes]]
   [euler.p050.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn solve [n]
  (let [primes-seq (vec (take-while #(< % n) (primes)))
        primes-set (set primes-seq)
        num-primes (count primes-seq)]
    (loop [ps primes-seq, sum 2, numprimes 1] ;; start with the prime 2
      (if (seq ps)
        (let [candidates (->> (reductions + ps)
                              (take-while #(< % n))
                              (map vector (rest (range)))
                              (drop numprimes) ;; remove smaller pairs
                              (filter (comp primes-set second)))]
          (if (seq candidates)
            (let [[numprimes' sum'] (last candidates)]
              (if (> numprimes' numprimes)
                (recur (rest ps) sum' numprimes')
                (recur (rest ps) sum numprimes)))
            (recur (rest ps) sum numprimes)))
        [sum numprimes]
        ))))

(deftest tests
  (is (= [41 6] (solve 100)))
  (is (= [953 21] (solve 1000)))
  (is (= (str answer)
         (str (first (solve 1000000))))))

;;;; Scratch

(comment
  (t/run-tests)
)
