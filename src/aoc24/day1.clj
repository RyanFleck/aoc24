(ns aoc24.day1
  (:require [clojure.string :as s]
            [nextjournal.clerk :as clerk]
            [clojure.java.io :as io]
            [aoc24.common :as common]))

(defn row-prep [rowstr]
  (map #(Integer/parseInt %) (s/split rowstr #"[ ]+")))

(comment
  ;; Get the AoC data for the first challenge:
  (def input (common/get-input-data 1))

  ;; Day 1 Puzzle 1
  (def c (map row-prep (s/split-lines input)))
  (def left (sort (map first c)))
  (def right (sort (map last c)))
  (reduce + (map #(abs (- %1 %2)) left right)); correct!

  ;; Puzzle 2
  (def right-freqs (frequencies right))
  (reduce + (map #(* % (get right-freqs % 0)) left))); correct!
