(ns aoc24.day2
  (:require [clojure.string :as s]
            [nextjournal.clerk :as clerk]
            [clojure.java.io :as io]
            [aoc24.common :as common]))

(comment
  (def input (common/get-input-data 2)))

;; These two functions turn the input into a nested array representing
;; the input matrix.

(defn str->intlist [^String string_line]
  (->> (s/split string_line #"\s+")
       (map #(Integer/parseInt %))))

(defn str->matrix [^String string_grid]
  (map str->intlist (s/split-lines string_grid)))

;; These three functions provide the three plain ideas required to
;; check if a single level of the matrix is 'safe' per the event
;; rules.

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

;; Here they are combined in a function to check each row for safety.

(defn is-level-safe? [int-list]
  (and
   (or (levels-all-increasing? int-list) (levels-all-decreasing? int-list))
   (levels-differ-by-1-to-3? int-list)))

;; A few ways to do this, but this is easy.

(defn count-trues [bool-list]
  (get (frequencies bool-list) true 0))

(comment
  ;; Question 1 Answer:
  (count-trues (map is-level-safe? (str->matrix input)))); correct!

;;; ======================================================================================
;;; Part Two

;; https://stackoverflow.com/questions/24553524/how-to-drop-the-nth-item-in-a-collection-in-clojure
(defn drop-nth [n coll]
  (concat (take n coll) (nthrest coll (inc n))))

(defn brute-force-options [int-list]
  (map #(drop-nth % int-list) (range (count int-list))))

(defn brute-force-retry [int-list]
  (let [options (brute-force-options int-list)]
    (->
     (map #(is-level-safe? %) options)
     (.contains true))))

(defn is-level-safe-v3? [int-list]
  (or

   ;; Either meet all initial requirements
   (and
    (or (levels-all-increasing? int-list) (levels-all-decreasing? int-list))
    (levels-differ-by-1-to-3? int-list))

   ;; OR Try "Problem Dampener" from Q2
   (brute-force-retry int-list)))

(comment
  ;; Answer:
  (count-trues (map is-level-safe-v3? (str->matrix input)))); correct!

;;; ... yep, this brute-force method is correct. Now if only I were a
;;; good programmer and could figure out how to do all of this in a
;;; minute or two like the guys at the top of the leaderboard!

;;; ======================================================================================
;;; Part Two - Failures

;; I figured I could determine *which* indice made things failed to
;; eliminate lots of retries. Perhaps this is still a good solution.

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

    (println int-list)
    (println {:increase  bad-increases :decrease bad-decreases :gap bad-gaps})

    ;; TODO - Determine which entry to remove. Holy crap this is a tough problem. Outtatime!
    (let [bad-indice (->> (list bad-increases bad-decreases bad-gaps)
                          (filter int?)
                          (filter #(< 0 %)))]

      (println (str "The bad indices may be " bad-indice))
      (cond
        (and (or (true? bad-increases) (true? bad-decreases)) (int? bad-gaps))
        (do
          (print "There was a good increase or decrease, try to remove a gap.")
          (is-level-safe? (drop-nth bad-gaps int-list)))))))

(defn is-level-safe-damped? [int-list]
  (or
   (and
    (or (levels-all-increasing? int-list) (levels-all-decreasing? int-list))
    (levels-differ-by-1-to-3? int-list))
   (remove-one-and-retry int-list)))
