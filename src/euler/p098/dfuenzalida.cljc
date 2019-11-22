(ns euler.p098.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest parse-int md5]]
   [euler.p098.data :refer [input answer]]
   [clojure.test :as t :refer [is]]))

(def squares ;; All squares up to 9 digits
  (delay (->> (map #(* % %) (range))
              (take-while #(> 10 (count (str %))))
              (apply sorted-set))))

(defn square? [n] (some? (@squares n)))

(defn create-map [n word] ;; map from char to digit
  (zipmap word (str n)))

(defn word-score [m word]
  (->> (map m word) (reduce str) parse-int))

(defn both-square? [m w1 w2]
  (every? true? (map (comp square? (partial word-score m)) [w1 w2])))

(def ffreqs (comp frequencies vals frequencies))

;; Look for squares than have the same 'shape' of word1 and,
;; when used as a map for word2, results in another square

(defn find-squares [[word1 word2]]
  (let [start (reduce * (repeat (dec (count word1)) 10))
        end   (dec (* start 10))
        dist  (ffreqs word1)
        sqrs  (->> @squares
                   (drop-while #(< % start))
                   (take-while #(< % end))
                   (filter #(= dist (ffreqs (str %)))))]
    (for [sqr sqrs
          :let [m (create-map sqr word1)]
          :when (and (not= \0 (m (first word2)))
                     (both-square? m word1 word2))]
      (max (word-score m word1) (word-score m word2)))))

(defn solve []
  (->> (for [w1 input, w2 input
             :when (and (not= w1 w2)
                        (= (count w1) (count w2)) ;; important!
                        (= (frequencies w1) (frequencies w2)))]
         #{w1 w2})
       set
       (map vec)
       (mapcat find-squares)
       (reduce max)))

(deftest tests
  (is (= [9216 9216] (find-squares ["CARE" "RACE"])))
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
