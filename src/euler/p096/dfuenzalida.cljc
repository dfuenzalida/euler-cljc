(ns euler.p096.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 read-string]]
   [euler.p096.data :refer [input answer]]
   [clojure.test :as t :refer [is]]))

(defn read-input [lines]
  (->> lines
       (partition 10)
       (mapcat rest) ;; drop the lines with 'Grid NN'
       (reduce str)
       (map (comp read-string str)) ;; digits to numbers
       (partition 9)
       (partition 9)))

(defn at [board x y]
  (-> board (nth y) (nth x)))

(defn column [board i]
  (disj (->> (map #(at board i %) (range 9)) set) 0))

(defn row [board j]
  (disj (->> (map #(at board % j) (range 9)) set) 0))

(defn box [board i j]
  (let [boxi (-> i (quot 3) (* 3))
        boxj (-> j (quot 3) (* 3))
        xs (set (for [x (range 3) y (range 3)] (at board (+ boxi x) (+ boxj y))))]
    (disj xs 0)))

(defn valid? [board i j n] ;; is it valid to place n in the board at i, j?
  (let [existing  (concat (column board i) (row board j) (box board i j))
        available (reduce disj (set (range 1 10)) existing)]
    (some? (available n))))

(defn updatep [board x y n]
  (let [xs (mapcat seq board)
        xs (concat (take (+ x (* 9 y)) xs) [n] (drop (+ 1 x (* 9 y)) xs))]
    (mapv vec (partition 9 xs))))

(defn complete? [board] ;; not a single 0 in the board
  (nil? (some #{0} (mapcat seq board))))

(defn solve-board [board]
  ;;  bns: possible values at bx, by, starting with 9 dummy values
  (loop [x 0, y 0, bx -1, by -1, branching? false,
         bns [0 0 0 0 0 0 0 0 0], board board]
    (cond
      (complete? board) board

      ;; a whole loop without updating the board
      (and branching?
           (= [x y] [bx by])) (->> (map #(updatep board x y %) bns)
                                   (map solve-board)
                                   (remove nil?)
                                   (filter complete?)
                                   first)

      (> x 8) (recur 0 (inc y) bx by branching? bns board)
      (> y 8) (recur 0 0 bx by branching? bns board)
      
      (pos? (at board x y))
      (recur (inc x) y bx by branching? bns board) ;; place already filled
      :else
      (let [existing (set (concat (column board x) (row board y) (box board x y)))
            possible (reduce disj (set (range 1 10)) existing)]
        (cond
          (> (count possible) 1) (if (< (count possible) (count bns))
                                   (recur (inc x) y x y true possible board)
                                   (recur (inc x) y bx by true bns board))
          (= 1 (count possible)) ;; single solution
          (let [sol (first possible)]
            (recur (inc x) y bx by false bns (updatep board x y sol)))

          :else nil)))))

(defn corner [board] ;; 3 digits on the corner of a board as a 3-digit number
  (+ (* 100 (at board 0 0)) (* 10 (at board 1 0)) (at board 2 0)))

(defn solve []
  (->> (read-input input)
       (map (comp corner solve-board))
       (reduce +)))

(deftest tests
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
