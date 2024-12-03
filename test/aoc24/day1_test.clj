(ns aoc24.day1-test
  (:require [clojure.test :refer :all]))

(deftest day1_test_solution
  (testing "With sample data."
    (let [a (sort (list 3 4 2 1 3 3))
          b (sort (list 4 3 5 3 9 3))]

      ; (map #(abs (- %1 %2)) (sort a) (sort b))
      (is (= 11 (reduce + (map #(abs (- %1 %2)) (sort a) (sort b))))))))
