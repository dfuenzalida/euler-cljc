(ns euler.new
  (:require
   [clojure.java.io :as io]
   [clojure.tools.cli :refer [parse-opts]]))

(defn data-ns [problem]
  (format "(ns euler.p%s.data)

(def input)

(def answer-1)

(def answer-2)
" problem))

(defn user-ns [problem user]
  (format "(ns euler.p%s.%s
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest read-string format]]
   [euler.p%s.data :refer [input answer-1 answer-2]]
   [clojure.test :as t :refer [is testing]]))

(defn solve-1 []
  ;; TODO
)

(defn solve-2 []
  ;; TODO
)

(deftest part-1
  (is (= (str answer-1)
         (str (solve-1)))))

(deftest part-2
  (is (= (str answer-2)
         (str (solve-2)))))

;;;; Scratch

(comment
  (t/run-tests)
)
" problem user problem))

(defn create-new [{:keys [problem user]}]
  (when (contains? (into #{} [problem user]) nil)
    (throw (Exception. (str "Some required arguments are null: "
                            {:problem problem
                             :user user}))))
  (let [data-out (io/file "src" "euler"
                          (str "p" problem)
                          "data.cljc")
        out (io/file "src" "euler"
                     (str "p" problem)
                     (str user ".cljc"))]
    (io/make-parents out)
    (when-not (.exists data-out)
      (spit data-out (data-ns problem))
      (println "Created a new file at" (.getPath data-out)))
    (if-not (.exists out)
      (do (spit out (user-ns problem user))
          (println "Created a new file at" (.getPath out)))
      (println "File already exists:" (.getPath out)))))

(def cli-options
  ;; An option with a required argument
  [["-p" "--problem NUMBER" "Problem"
    :parse-fn #(Integer/parseInt %)
    :validate [#(<= 1 % 2018) "Must be a number between 1 and 1000 (inclusive)"]]
   ["-u" "--user USER" "User"
    :validate [#(re-find #"^[A-Za-z]" %) "Username must start with letter"]]
   ["-h" "--help"]])

(defn -main
  [& args]
  (let [{:keys [options arguments] :as opts}
        (parse-opts args cli-options)
        {:keys [options summary errors]}
        (if (empty? options)
          (parse-opts (interleave
                       ["-p" "-u"]
                       arguments)
                      cli-options)
          opts)]
    (cond (:help options)
          (println summary)
          errors
          (doseq [e errors]
            (println e))
          :else
          (create-new options))))

