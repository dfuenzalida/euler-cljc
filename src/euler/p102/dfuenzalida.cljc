(ns euler.p102.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5]]
   [euler.p102.data :refer [input answer]]
   [clojure.test :as t :refer [is]]))

(defn slope [xa ya xb yb]
  (/ (- yb ya) (- xb xa)))

;; origin and x3 y3 are in the same semiplane defined by the line (x1, y1) to (x2, y2)
(defn same-semiplane? [x1 y1 x2 y2 x3 y3]
  (cond
    (= x1 x2) (pos? (* y1 (- y1 y3)))
    (= y1 y2) (pos? (* x1 (- x1 x3)))
    :else
    (let [m  (slope x1 y1 x2 y2)
          k1 (- y1 (* m x1))  ;; y-intercept when crossing (x1, y1)
          k3 (- y3 (* m x3))] ;; y-intercept when crossing (x3, y3)
      (pos? (* k1 (- k1 k3)))))) ;; both w/ same sign implies same side of the line

(defn contained? [x1 y1 x2 y2 x3 y3]
  (->> (cycle [x1 y1 x2 y2 x3 y3])
       (partition 6 2)
       (take 3)
       (map (partial apply same-semiplane?))
       (every? true?)))

(defn solve []
  (->> input
       (filter (partial apply contained?))
       count))

(deftest tests
  (is (true? (contained? -340,495, -153,-910, 835,-947))) ;; Triangle ABC
  (is (false? (contained? -175,41, -421,-714, 574,-645))) ;; Triangle XYZ
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
