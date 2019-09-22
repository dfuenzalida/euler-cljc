(ns euler.p008.dfuenzalida
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest parse-int md5]]
   [euler.p008.data :refer [input answer]]
   [clojure.test :as t :refer [is testing]]))

(defn digit-to-int [c]
  (parse-int (str c)))

(defn solve [n]
  (->> (map digit-to-int input)
       (partition n 1)
       (map #(apply * %))
       (reduce max)))

(deftest tests
  (is (= 5832 (solve 4)))
  (is (= (str answer)
         (md5 (str (solve 13))))))

;;;; Scratch

(comment
  (t/run-tests)
)
