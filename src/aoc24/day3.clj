(ns aoc24.day3
  (:require [clojure.string :as s]
            [nextjournal.clerk :as clerk]
            [clojure.java.io :as io]
            [aoc24.common :as common]))

;; https://adventofcode.com/2024/day/3

(defn str->int [str] (Integer/parseInt str))

(comment
  ;; Fetch day 3's input data:
  (def input (common/get-input-data 3))

  ;; Answer - Part One
  ;; One liner baby B)
  (apply + (map #(* (str->int (nth % 1)) (str->int (nth % 2))) 
                (re-seq #"mul\(([\d][\d]?[\d]?),([\d][\d]?[\d]?)\)" input))) ; correct!

  ;; Part 2 can wait until tomorrow!
  )

(defn add [& rest]
  (apply + rest))
