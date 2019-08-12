(ns euler.new
  (:require
   [clojure.java.io :as io]
   [clojure.tools.cli :refer [parse-opts]]))

(defn data-ns [problem]
  (format "(ns euler.p%s.data)

(def answer nil)

" problem))

(defn user-ns [problem user]
  (format "(ns euler.p%s.%s
  (:refer-clojure :exclude [read-string format])
  (:require
   [euler.utils :as u :refer [deftest read-string format]]
   [euler.p%s.data :refer [answer]]
   [clojure.test :as t :refer [is testing]]))

(defn solve []
  ;; TODO
)

(deftest tests
  (is (= (str answer)
         (str (solve)))))

;;;; Scratch

(comment
  (t/run-tests)
)
" problem user problem))

(defn create-new [{:keys [problem user]}]
  (let [prob (format "%03d" problem)]
    (when (contains? (into #{} [problem user]) nil)
      (throw (Exception. (str "Some required arguments are null: "
                              {:problem problem
                               :user user}))))
    (let [data-out (io/file "src" "euler"
                            (str "p" prob)
                            "data.cljc")
          out (io/file "src" "euler"
                       (str "p" prob)
                       (str user ".cljc"))]
      (io/make-parents out)
      (when-not (.exists data-out)
        (spit data-out (data-ns prob))
        (println "Created a new file at" (.getPath data-out)))
      (if-not (.exists out)
        (do (spit out (user-ns prob user))
            (println "Created a new file at" (.getPath out)))
        (println "File already exists:" (.getPath out))))))

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

