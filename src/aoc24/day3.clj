(ns aoc24.day3
  (:require [clojure.string :as s]
            [nextjournal.clerk :as clerk]
            [clojure.java.io :as io]
            [aoc24.common :as common]))

(def input (common/get-input-data 3))

(defn add [& rest]
  (apply + rest))
