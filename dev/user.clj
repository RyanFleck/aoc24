
(ns user
  (:require [nextjournal.clerk :as clerk]
            [aoc24.index :as index]))

(clerk/serve! {:port 7777 :browse true})

(comment
  (clerk/build! {:paths (index/build-paths) :browse true}))
