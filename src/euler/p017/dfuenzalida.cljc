(ns euler.p017.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest read-string format]]
   [euler.p017.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(def smaller-numbers
  (mapv name '[zero one two three four five six seven eight
               nine ten eleven twelve thirteen fourteen fifteen
               sixteen seventeen eighteen nineteen]))
(def tens
  (mapv name '[zero ten twenty thirty forty fifty
               sixty seventy eighty ninety]))

(defn cardinal [n] ;; See https://en.wikipedia.org/wiki/Cardinal_numeral
  (cond
    (= n 1000)    "one thousand"
    (< 99 n 1000) (str
                   (smaller-numbers (quot n 100)) " hundred"
                   (if (= 0 (rem n 100)) "" " and ") ;; any 100-remainder?
                   (cardinal (rem n 100)))

    (< 19 n 100)  (str (tens (quot n 10)) "-" (cardinal (rem n 10)))
    (< 0 n 20)    (smaller-numbers n)
    :else ""))

(defn count-letters [s]
  (count (re-seq #"\w" s)))

(defn solve [n]
  (->> (range 1 (inc n))
       (map (comp count-letters cardinal))
       (reduce +)))

(deftest misc-tests
  (is (= "three hundred and forty-two" (cardinal 342)))
  (is (= 23 (count-letters (cardinal 342))))
  (is (= "one hundred and fifteen" (cardinal 115)))
  (is (= 20 (count-letters (cardinal 115))))
  (is (= 19 (solve 5)))
  (is (= (str answer)
         (str (solve 1000)))))

;;;; Scratch

(comment
  (t/run-tests)
)
