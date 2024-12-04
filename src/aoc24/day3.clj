(ns aoc24.day3
  (:require [clojure.string :as s]
            [aoc24.common :as common]))

;; https://adventofcode.com/2024/day/3

(defn add [& rest]
  (apply + rest))

(defn str->int [str] (Integer/parseInt str))

(comment

  ;; Fetch day 3's input data:
  (def input (common/get-input-data 3))

  ;; Answer - Part One
  (apply + (map #(* (str->int (nth % 1)) (str->int (nth % 2)))
                (re-seq #"mul\(([\d][\d]?[\d]?),([\d][\d]?[\d]?)\)" input))); correct!

  ;; Answer - Part Two
  (apply + (map #(* (str->int (nth % 1)) (str->int (nth % 2)))
                (re-seq #"mul\(([\d][\d]?[\d]?),([\d][\d]?[\d]?)\)"
                        (apply str (filter #(not (s/starts-with? % "n't")) (s/split input #"do"))))))

  ;; Answer - Part Two 
  ;; - Refactored with threading macro for readability.
  ;; - Simplified the regex.
  (->>
   (s/split input #"do") ; split the initial input on 'do'
   (filter #(not (s/starts-with? % "n't"))) ; remove strings that start with 'don't'
   (apply str) ; combine these into one long string
   (re-seq #"mul\((\d+),(\d+)\)") ; run the regex from part one answer
   (map #(* (str->int (nth % 1)) (str->int (nth % 2)))) ; multiply the numbers found by regex
   (apply +)) ; sum this list of products

  ;; fin
  )
