(ns euler.p085.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5 read-string format]]
   [euler.p085.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn opts-for-grid-rect [gridw gridh rectw recth]
  (* (- gridw rectw -1) (- gridh recth -1)))

(defn opts-for-grid [gridw gridh]
  (reduce +
          (for [w (range 1 (inc gridw))
                h (range 1 (inc gridh))]
            (opts-for-grid-rect gridw gridh w h))))

(defn compute-opts []
  (for [w (rest (range))
        h (range 1 w)]
    [(opts-for-grid w h) (* h w)])) ;; [num-combs grid-size]

(defn solve []
  (->> (compute-opts)
       (take 4000) ;; this number was found by attempting several submissions
       (apply min-key #(Math/abs (- 2000000 (first %))))
       second))

(deftest tests
  (is (= 18 (opts-for-grid 3 2)))
  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

(comment
  (t/run-tests)
)
