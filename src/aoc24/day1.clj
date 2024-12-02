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

(reduce + (map #(abs (- %1 %2)) (sort a) (sort b))) ;; 11 (true!)

(def input (common/get-input-data 1))
