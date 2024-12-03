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

(= 4 (count-trues (map is-level-safe-v3? (str->matrix sample))))

;; Answer:
(count-trues (map is-level-safe-v3? (str->matrix input)))

;;; ... yep, this brute-force method is correct.

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

(map remove-one-and-retry (str->matrix sample))



;;; ======================================================================================
