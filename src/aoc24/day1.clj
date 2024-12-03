(ns aoc24.day1
  (:require [clojure.string :as s]
            [nextjournal.clerk :as clerk]
            [clojure.java.io :as io]
            [aoc24.common :as common]))

;; M-<RET> renders the clerk file.
;; Here's a test:

(def a (sort (list 3 4 2 1 3 3)))
(def b (sort (list 4 3 5 3 9 3)))
(map #(abs (- %1 %2)) (sort a) (sort b))
(reduce + (map #(abs (- %1 %2)) (sort a) (sort b)))

;; 11 (true!)

(def input (common/get-input-data 1))

;; This is wrong, the columns must be sorted
(defn row-diff [rowstr]
  (let [x (s/split rowstr #"[ ]+")
        y (map #(Integer/parseInt %) x)]
    (abs (- (first y) (last y)))))

(defn row-prep [rowstr]
  (let [x (s/split rowstr #"[ ]+")
        y (map #(Integer/parseInt %) x)]

    ;; Just return Y.
    y))

;; (map row-prep (s/split-lines input))

(row-diff "5 9")

;; Should be 4.

;; Day 1 Puzzle 1

(def c (map row-prep (s/split-lines input)))
(def left (sort (map first c)))
(def right (sort (map last c)))
(reduce + (map #(abs (- %1 %2)) left right))

;; ...correct!

;; Puzzle 2

(def right-freqs (frequencies right))
(reduce + (map #(* % (get right-freqs % 0)) left))

;; ...correct!
