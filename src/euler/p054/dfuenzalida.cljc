(ns euler.p054.dfuenzalida
  (:refer-clojure :exclude [read-string format flush])
  (:require
   [euler.utils :as u :refer [deftest md5 read-string format]]
   [euler.p054.data :refer [input answer]]
   [clojure.test :as t :refer [is testing]]))

(def card-order
  (zipmap "23456789TJQKA" (range 2 15)))

(defn high-card [cs]
  (->> cs (map (comp card-order first)) sort reverse))

(defn same-suit? [cs]
  (= 1 (->> (map second cs) set count)))

(defn one-pair [cs]
  (let [pair (->> cs (map (comp card-order first)) frequencies
                  (filter (fn [[k v]] (= 2 v))) ffirst)]
    (vector (or pair 0))))

(defn two-pairs [cs]
  (if (= {2 2, 1 1} (->> cs (map first) frequencies vals frequencies))
    (->> cs (map (comp card-order first)) frequencies
         (filter (fn [[k v]] (= 2 v))) (map first) sort reverse)
    [0 0])) ;; two zeros

(defn three-of-a-kind [cs]
  (let [tok (->> cs (map (comp card-order first)) frequencies
                 (filter (fn [[k v]] (= 3 v))) ffirst)]
    (vector (or tok 0))))

(defn straight [cs]
  (let [nums (->> (map (comp card-order first) cs) sort)
        num0 (reduce min nums)]
    (if (= (range num0 (+ num0 5)) nums) [num0] [0])))

(defn flush [cs]
  (if (same-suit? cs)
    (->> cs (map (comp card-order first)) (reduce min) vector)
    [0]))

(defn full-house [cs]
  (let [tok (three-of-a-kind cs)
        op  (one-pair cs)]
    (if (and (not= [0] tok) (not= [0] op))
      (into tok op)
      [0 0]))) ;; zero trios, zero pairs

(defn four-of-a-kind [cs]
  (let [fok (->> cs (map (comp card-order first)) frequencies
                 (filter (fn [[k v]] (= v 4))) ffirst)]
    [(or fok 0)]))

(defn straight-flush [cs]
  (let [nums (->> (map (comp card-order first) cs) sort)
        num0 (reduce min nums)]
    (if (same-suit? cs)
      (flush cs) ;; flush already returns a vector
      [0])))

(defn royal-flush [cs]
  (let [numset (->> (map first cs) set)]
    (if (and (same-suit? cs)
             (= #{\T \J \Q \K \A} numset))
      [1]
      [0])))

(defn wins? [scores1 scores2]
  (when (seq scores1)
    (let [s1 (first scores1)
          s2 (first scores2)]
      (if (> s1 s2)
        true
        (when (= s1 s2)
          (recur (rest scores1) (rest scores2)))))))

(def ranks
  [royal-flush straight-flush four-of-a-kind full-house flush
   straight three-of-a-kind two-pairs one-pair high-card])

(defn poker-scores [cs]
  (->> (map (fn [f] (f cs)) ranks) (reduce into)))

(defn read-poker-line [s]
  (partition 5 (clojure.string/split s #" ")))

(defn wins-line? [s]
  (let [[h1 h2] (read-poker-line s)]
    (wins? (poker-scores h1) (poker-scores h2))))

(defn solve []
  (->> input (filter wins-line?) count))

(deftest tests
  (is (= 14 (card-order \A)))
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
