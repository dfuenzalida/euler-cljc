(ns euler.p059.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 read-string format]]
   [euler.p059.data :refer [input answer]]
   [clojure.test :as t :refer [is testing]]))

(defn char->int [c]
  #?(:clj (int c)
     :cljs (.charCodeAt c 0)))

(defn int->char [n]
  #?(:clj (char n)
     :cljs (String/fromCharCode n)))

(defn build-score-map [] ;; See https://en.wikipedia.org/wiki/Etaoin_shrdlu
  (let [freq-phrase (apply str (reverse "ETAOIN SHRDLU"))
        upper-freqs (map vector freq-phrase (rest (range)))
        lower-freqs (map vector (.toLowerCase freq-phrase) (rest (range)))]
    (into {} (concat upper-freqs lower-freqs))))

(defn bytes-score
  [score-map bytes mask]
  (->> (map bit-xor bytes (cycle mask))
       (map (comp score-map int->char))
       (remove nil?)
       (reduce +)))

(defn solve []
  (let [score-map  (build-score-map)
        char-range (range (char->int \a) (inc (char->int \z)))
        masks      (for [a char-range b char-range c char-range] [a b c])
        mask       (apply max-key (partial bytes-score score-map input) masks)]
    (->> (map bit-xor input (cycle mask))
         (reduce +))))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
