(ns aoc24.day4  
  (:require [clojure.string :as s]
            [aoc24.common :as common]))

(comment
  (def sample
    "MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX")

  (defn get-rows [input]
    (s/split-lines input))

  (defn reverse [str-list]
    (map s/reverse str-list))

  (defn get-column [str-list nth-col]
    (map #(nth % nth-col) str-list))

; (get-column (get-rows sample) 1)

  (defn get-columns [input]
    (let [rows (get-rows input)
          width (count (first rows))]

      (->>
       (range width)
       (map #(get-column rows %))
       (map #(apply str %)))))

  (get-columns sample)

  (defn get-diagonals [input]
    (let [rows (get-rows input)
          columns (get-columns input)]

      {:rows rows
       :cols columns}))

  (get-diagonals sample)

  (defn count-up-coords [col row limit]
    (if (and (> limit (abs col)) (>= limit (abs row)))
      (merge
       (count-up-coords (+ col 1) (+ row 1) limit)
       (list col row))
      (list (list col row))))

  (count-up-coords 1 1 9); ((1 1) (0 0) (-1 -1) (-2 -2) (-3 -3) (-4 -4) (-5 -5) (-6 -6) (-7 -7) (-8 -8) (-9 -9))

  (count-up-coords 0 -9 9);((0 -9) (1 -8) (2 -7) (3 -6) (4 -5) (5 -4) (6 -3) (7 -2) (8 -1) (9 0))
  (count-up-coords 0 -8 9);((0 -8) (1 -7) (2 -6) (3 -5) (4 -4) (5 -3) (6 -2) (7 -1) (8 0) (9 1))
  (count-up-coords 0 -7 9);((0 -7) (1 -6) (2 -5) (3 -4) (4 -3) (5 -2) (6 -1) (7 0) (8 1) (9 2))

  (count-up-coords 0 9 9)

; ... I am stuck.
)
