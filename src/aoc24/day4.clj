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
MXMXAXMASX"))

(comment
  (def data (common/get-input-data 4)))

;; First attempt: I bet if I can get all the rows, columns, and
;; diagonals from this thing, I'll be able to combine them all and
;; their reversed versions to seach for "XMAS" in one go. Lots of
;; little functions though - I bet there's a better way to think about
;; this puzzle.

(defn get-rows [input]
  (map s/trim (s/split-lines input)))

(defn get-column [str-list nth-col]
  (apply str (map #(nth % nth-col) str-list)))

(comment
  (get-column (get-rows sample) 1))

(defn get-columns [input]
  (let [rows (get-rows input)
        width (count (first rows))]

    (map #(get-column rows %) (range width))))

(comment
  (get-columns sample))

(defn get-diagonals [input]
  (let [rows (get-rows input)
        columns (get-columns input)]

    {:rows rows
     :cols columns}))

(comment
  (get-diagonals sample))

(defn count-up-coords [col row limit]
  (if (and (> limit (abs col)) (>= limit (abs row)))
    (merge
     (count-up-coords (+ col 1) (+ row 1) limit)
     (list col row))
    (list (list col row))))

(comment
  (count-up-coords 1 1 9)   ; ((1 1) (0 0) (-1 -1) (-2 -2) (-3 -3) (-4 -4) (-5 -5) (-6 -6) (-7 -7) (-8 -8) (-9 -9))
  (count-up-coords 0 -9 9)  ;((0 -9) (1 -8) (2 -7) (3 -6) (4 -5) (5 -4) (6 -3) (7 -2) (8 -1) (9 0))
  (count-up-coords 0 -8 9)  ;((0 -8) (1 -7) (2 -6) (3 -5) (4 -4) (5 -3) (6 -2) (7 -1) (8 0) (9 1))
  (count-up-coords 0 -7 9)) ;((0 -7) (1 -6) (2 -5) (3 -4) (4 -3) (5 -2) (6 -1) (7 0) (8 1) (9 2))

(defn first-start-coords [first-row]
  [0 (- 1 (count first-row))])

(defn diagonal-right-down-coords [[x y] limit]
  (cond
    (< x limit) (conj (diagonal-right-down-coords [(+ x 1) (+ y 1)] limit) (list x y))
    (= x limit) (list (list x y))
    :else (throw (Exception. "How did you get here?"))))

(defn diagonal-left-down-coords [[x y]]
  (cond
    (> x 0) (conj (diagonal-left-down-coords [(- x 1) (+ y 1)]) (list x y))
    (= x 0) (list (list x y))
    :else (throw (Exception. "How did you get here?"))))

(comment
  (diagonal-right-down-coords [0 0] 9)
  (diagonal-right-down-coords [0 -9] 9)
  (diagonal-left-down-coords [9 -9]) ; ((9 -9) (8 -8) (7 -7) (6 -6) (5 -5) (4 -4) (3 -3) (2 -2) (1 -1) (0 0))
  (diagonal-left-down-coords [9 0]) ; ((9 0) (8 1) (7 2) (6 3) (5 4) (4 5) (3 6) (2 7) (1 8) (0 9))
  )

(defn filter-for-real-coords [set height width]
  (->> set
       (filter #(and (>= (first %) 0) (>= (second %) 0)))
       (filter #(and (< (first %) width) (< (second %) height)))))

(comment
  (filter-for-real-coords
   (diagonal-right-down-coords [0 0] 9) 10 10)
  (filter-for-real-coords
   (diagonal-right-down-coords [0 -9] 9) 10 10))

(defn getchar [rows coords]
  (nth (nth rows (second coords)) (first coords)))

(comment
  (def sample-rows (get-rows sample))
  (map #(nth (nth sample-rows (second %)) (first %))
       (diagonal-right-down-coords [0 0] 9)))

(comment
;; The cider inspector is awesome.
  (getchar sample-rows '(0 1))

;; Here's how we can get the diagonal-right-down rows. (SE)
  (->>
   (for [y (range -9 10)]
     (filter-for-real-coords
      (diagonal-right-down-coords [0 y] 9) 10 10))
   (map (fn [row] (apply str (map #(getchar sample-rows %) row)))))

  ;; ...now I just need to do left-down.
  ;; ((9 0) (8 1) (7 2) (6 3) (5 4) (4 5) (3 6) (2 7) (1 8) (0 9))

  (for [y (range -9 10)] ; -9 to 9 inclusive
    (filter-for-real-coords
     (diagonal-left-down-coords [(- (count (first sample-rows)) 1) y]) 10 10))

  ;; Left-down: (SW)
  (->>
   (for [y (range -9 10)] ; -9 to 9 inclusive
     (filter-for-real-coords
      (diagonal-left-down-coords [(- (count (first sample-rows)) 1) y]) 10 10))
   (filter seq?)
   (map (fn [row] (apply str (map #(getchar sample-rows %) row)))))

  ;; End of comment
  )

(defn get-diagonal-rows-se [input]
  (let [rows (get-rows input)
        cols (get-columns input)
        row-count (count rows)
        col-count (count cols)]

    (->>
     (for [y (range (- 1 row-count) row-count)]
       (filter-for-real-coords
        (diagonal-right-down-coords [0 y] (- col-count 1)) row-count col-count))
     (map (fn [row] (apply str (map #(getchar rows %) row)))))))

(defn get-diagonal-rows-sw [input]
  (let [rows (get-rows input)
        cols (get-columns input)
        row-count (count rows) ; "height"
        col-count (count cols) ; "width"
        ]

    (->>
     (for [y (range (- 1 row-count) row-count)] ; -9 to 9 inclusive
       (filter-for-real-coords
        (diagonal-left-down-coords [(- col-count 1) y]) row-count col-count))

     (map (fn [row] (apply str (map #(getchar rows %) row)))))))

(defn day-4-answer [input]
  (let [rows (get-rows input)
        cols (get-columns input)
        diag-se (get-diagonal-rows-se input) ;; string index out of bounds?
        diag-sw (get-diagonal-rows-sw input)
        combined (flatten (apply merge rows cols diag-se diag-sw))
        reversed (map #(apply str (reverse %)) combined)
        all (flatten (apply merge combined reversed))]

    ;; Combine into one long string (with separators) and count "XMAS"
    (count (re-seq #"XMAS" (s/join "-" all)))))

(comment
  ;; Part 1
  (day-4-answer data) ; correct. First try. Way way too long. Whew!
  (time (day-4-answer data)) ; 391.9874 msecs

  )

;; Ah crap I built this all wrong if this was supposed to help in solving part two. RIP!
