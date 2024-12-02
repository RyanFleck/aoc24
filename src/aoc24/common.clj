(ns aoc24.common
  (:require [clojure.java.io :as io]
            [clj-http.client :as client]
            [environ.core :refer [env]]
            [clojure.string :as s]))

(defn download-puzzle-data [^Integer day]
  ;; https://github.com/dakrone/clj-http
  ;; https://yobriefca.se/blog/2014/04/29/managing-environment-variables-in-clojure/
  ;; Hint on setting session here: https://medium.com/@wujido20/advent-of-code-2022-input-loading-made-easy-b38153d6ca2d

  ;; Step 1: Make request with token.
  (let [token (env :aoc-session)
        url (str "https://adventofcode.com/2024/day/" day "/input")]
    (println (str "Using token '" (subs token 0 10) "..."))
    (let [filename (str "resources/day" day ".txt")
          res (client/get url {:headers {:cookie (str "session=" token)}})]

      ;; Step 2: Write data to correct filename.
      (spit filename (:body res))
      (println (str "Wrote body to " filename)))))

;; Example:
;; (download-puzzle-data 1)

(defn get-input-data
  "Returns the puzzle data for a given day, and downloads it if it doesn't exist."
  [^Integer day]
  (let [filename (str "resources/day" day ".txt")]

    (when (not (.exists (io/file filename)))
      (println "Data for day " day " does not exist, downloading...")
      (download-puzzle-data day))

    (slurp filename)))
