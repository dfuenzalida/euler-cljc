(ns euler.p045.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5]]
   [euler.p045.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn triangles []
  (map (fn [n] (/ (* n (inc n)) 2)) (rest (range))))

(defn pentagonals []
  (map (fn [n] (/ (* n (dec (* 3 n))) 2)) (rest (range))))

(defn hexagonals []
  (map (fn [n] (* n (dec (* 2 n)))) (rest (range))))

(defn matches [ts ps hs]
  (let [t (first ts)
        p (first ps)
        h (first hs)]
    (cond
      (= t p h) (lazy-seq (cons t (matches (rest ts) (rest ps) (rest hs))))
      (> p h)   (recur ts ps (rest hs))
      (> t p)   (recur ts (rest ps) hs)
      :else     (recur (rest ts) ps hs))))

(defn solve [] ;; takes ~10 secs on my laptop, very general, no bounds
  (->> (matches (triangles) (pentagonals) (hexagonals))
       (take 3)
       last))

(defn solve2 [] ;; takes ~170 ms in my laptop, but sets limit (100,000th element)
  (let [pents (set (take 100000 (pentagonals)))
        hexas (set (take 100000 (hexagonals)))
        tris  (drop 286 (triangles))] ;; 286 comes from the problem statement
    (->> (filter (comp pents hexas) tris)
         first)))

(deftest tests
  (is (= [1 40755] (take 2 (matches (triangles) (pentagonals) (hexagonals)))))
  (is (= (str answer)
         (md5 (str (solve)))))
  (is (= (str answer)
         (md5 (str (solve2))))))

;;;; Scratch

(comment
  (t/run-tests)
  )
