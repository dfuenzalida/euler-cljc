(ns euler.p061.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest md5]]
   [euler.p061.data :refer [answer]]
   [clojure.test :as t :refer [is]]))

(defn generate [a b c] ;; n * (an + b) / c ; eg. triangle = (generate 1 1 2)
  (->> (rest (range))
       (map #(/ (* % (+ (* a %) b)) c))
       (drop-while #(< % 1000))
       (take-while #(< % 10000))))

(defn tail [xs] (-> xs last (rem 100))) ;; (= 67 (tail [1234 4567]))

(defn find-groups [n upgs pairs]
  (loop [pgs [], upgs upgs, ppairs [], pairs pairs]
    ;; pick an unprocessed group, check if any pair fits the tail of the group
    (if (seq upgs)
      (let [[nums types] (first upgs)]
        (if (= n (count nums))
          ;; filter groups with N elements and have same head and tail
          (->> (map first upgs)
               (filter #(= n (count %)))
               (filter #(= (quot (first %) 100) (mod (last %) 100)))
               first)
          (if (seq pairs)
            (let [[num type] (first pairs)]
              (if (and (nil? (types type)) ;; this group doesn't have that type yet
                       (= (quot num 100) (tail nums)))
                (recur (conj pgs [(conj nums num) (conj types type)]) ;; match becomes a new processed group
                       upgs
                       (conj ppairs [num type])
                       (rest pairs))
                (recur pgs upgs (conj ppairs (first pairs)) (rest pairs)))) ;; move into the next pair
            ;; all pairs processed, move into next group
            (recur (conj pgs (first upgs)) (rest upgs) [] ppairs))))
      ;; all groups processed, move to unprocessed and start over
      (recur [] pgs ppairs pairs))))

(defn solve []
  (->> (find-groups
        6
        (mapv #(vector [%] #{:p3}) (generate 1 1 2))
        (concat
         (mapv vector (generate 1 0 1) (repeat :p4))
         (mapv vector (generate 3 -1 2) (repeat :p5))
         (mapv vector (generate 2 -1 1) (repeat :p6))
         (mapv vector (generate 5 -3 2) (repeat :p7))
         (mapv vector (generate 3 -2 1) (repeat :p8))))
       (reduce +)))

(deftest tests
  (is (= [8128, 2882, 8281]
         (find-groups
          3
          (mapv #(vector [%] #{:p3}) (generate 1 1 2))
          (concat
           (mapv vector (generate 1 0 1) (repeat :p4))
           (mapv vector (generate 3 -1 2) (repeat :p5))))))

  (is (= (str answer)
         (md5 (str (solve))))))

;;;; Scratch

;; Groups are pairs of <numbers matching their last-first 2 digits> and <series they belong to>:
;;
;; [
;;   [
;;     [8128 2882] ;; elements, "tail" = 82
;;     #{:p3 :p5}  ;; "types", :p3 = triangular, :p4 = square
;;   ]
;;   ,,,
;; ]

(comment
  (t/run-tests)
)

