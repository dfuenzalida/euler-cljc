(ns euler.p028.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest]]
   [euler.p028.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(def directions {:left [-1 0] :right [1 0] :up [0 1] :down [0 -1]})

(defn movements []
  (mapcat (fn [n]
            (if (odd? n)
              (lazy-cat (repeat n :right) (repeat n :down))
              (lazy-cat (repeat n :left) (repeat n :up))))
          (rest (range))))

(defn make-spiral [n] ;; square of size n x n
  (let [origin (quot n 2)
        pairs  (take (dec (* n n))
                (map vector (movements) (drop 2 (range))))]
    (->> (reduce (fn [[m [x y]] [mov num]]
                   (let [[dx dy] (directions mov)
                         m'      (assoc m [(+ x dx) (+ y dy)] num)]
                     [m' [(+ x dx) (+ y dy)]]))
                 [{[origin origin] 1} [origin origin]] ;; start with 1 @ origin
                 pairs)
         first)))

(defn solve [n]
  (let [spiral (make-spiral n)]
    (->> (map #(spiral [% %]) (range n))
         (concat (map #(spiral [% (- n % 1)]) (range n)))
         (reduce +)
         dec)))

(deftest tests
  (is (= 101 (solve 5)))
  (is (= (str answer)
         (str (solve 1001)))))

;;;; Scratch

(comment
  (t/run-tests)
)
