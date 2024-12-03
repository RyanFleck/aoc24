(ns aoc24.day2
  (:require [clojure.string :as s]
            [nextjournal.clerk :as clerk]
            [clojure.java.io :as io]
            [aoc24.common :as common]))

(def input (common/get-input-data 2))

(def sample
  "7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9")


(defn str->intlist [^String string_line]
  (->> (s/split string_line #"\s+")
       (map #(Integer/parseInt %))))

(defn str->matrix [^String string_grid]
  (map str->intlist (s/split-lines string_grid)))

(defn levels-all-increasing? [int-list]
  (cond
    (= (count int-list) 1) true
    (> (nth int-list 1) (first int-list)) (levels-all-increasing? (rest int-list))
    :else false))

(defn levels-all-decreasing? [int-list]
  (cond
    (= (count int-list) 1) true
    (< (nth int-list 1) (first int-list)) (levels-all-decreasing? (rest int-list))
    :else false))

(defn levels-differ-by-1-to-3? [int-list]
  (cond
    (= (count int-list) 1) true
    (let [diff (abs (- (nth int-list 1) (first int-list)))]
      (and (>= diff 1) (<= diff 3)))
    (levels-differ-by-1-to-3? (rest int-list))
    :else false))

(defn is-level-safe? [int-list]
  (and
   (or (levels-all-increasing? int-list) (levels-all-decreasing? int-list)) 
   (levels-differ-by-1-to-3? int-list)))

(defn count-trues [bool-list]
  (get (frequencies bool-list) true 0))


(= 2 (count-trues (map is-level-safe? (str->matrix sample))))

;; Answer:
(count-trues (map is-level-safe? (str->matrix input)))

(defn levels-all-increasing-v2? [int-list index]
  (cond
    (= (count int-list) 1) true
    (> (nth int-list 1) (first int-list)) (levels-all-increasing-v2? (rest int-list) (+ index 1))
    :else index))

(defn levels-all-decreasing-v2? [int-list index]
  (cond
    (= (count int-list) 1) true
    (< (nth int-list 1) (first int-list)) (levels-all-decreasing-v2? (rest int-list) (+ index 1))
    :else index))

(defn levels-differ-by-1-to-3-v2? [int-list index]
  (cond
    (= (count int-list) 1) true
    (let [diff (abs (- (nth int-list 1) (first int-list)))]
      (and (>= diff 1) (<= diff 3)))
    (levels-differ-by-1-to-3-v2? (rest int-list) (+ index 1))
    :else index))

(defn remove-one-and-retry [int-list]
  (let [bad-increases (levels-all-increasing-v2? int-list 0)
        bad-decreases (levels-all-decreasing-v2? int-list 0)
        bad-gaps (levels-differ-by-1-to-3-v2? int-list 0)]

    ;; TODO - Determine which entry to remove. Holy crap this is a tough problem. Outtatime!
    ))

(defn is-level-safe-damped? [int-list]
  (or
   (and
    (or (levels-all-increasing? int-list) (levels-all-decreasing? int-list))
    (levels-differ-by-1-to-3? int-list))
   (remove-one-and-retry int-list)))

(map remove-one-and-retry (str->matrix sample))
;; ((0 true true) (true 0 1) (0 true 2) (1 0 true) (0 2 2) (true 0 true))

;; ((0) (0 1) (0 2) (1 0) (0 2) (0))

