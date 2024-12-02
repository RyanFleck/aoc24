(ns aoc24.core
  (:require [clojure.string :as s]
            [nextjournal.clerk :as clerk]))

(defn -main []
  (clerk/serve! {:browse true}))
