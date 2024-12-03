(ns aoc24.day2-test
  (:require [clojure.test :refer :all]
            [aoc24.day2 :as day2]))

;; C-c C-t p  ---> Run project tests.

(deftest str->intlist_test
  (testing "Default use case:"
    (is (= (day2/str->intlist "1 2 3 4") '(1 2 3 4)))))

(def matrix-in
  "1 2 3
4 5 6
7 8 9")

(def matrix-out '((1 2 3) (4 5 6) (7 8 9)))

(deftest str->matrix_test
  (testing "Default use case"
    (is (= (day2/str->matrix matrix-in) matrix-out))))

(deftest check-increase-tester
  (testing "Ensure increasing levels are marked true"
    (is (= true (day2/levels-all-increasing? '(1))))
    (is (= true (day2/levels-all-increasing? '(1 2 3))))
    (is (= true (day2/levels-all-increasing? '(1 2 3 4 5 6))))
    (is (= false (day2/levels-all-increasing? '(1 2 3 4 5 5))))
    (is (= false (day2/levels-all-increasing? '(1 2 3 4 5 4))))))

(deftest check-decrease-tester
  (testing "Ensure decreasing levels are marked true"
    (is (= true (day2/levels-all-decreasing? '(1))))
    (is (= true (day2/levels-all-decreasing? '(3 2 1))))
    (is (= true (day2/levels-all-decreasing? '(5 4 3 2 1))))
    (is (= false (day2/levels-all-decreasing? '(5 4 3 2 2))))
    (is (= false (day2/levels-all-decreasing? '(5 4 3 2 20))))))

(deftest check-diff-tester
  (testing "Ensure decreasing day2/levels are marked true"
    (is (= true (day2/levels-differ-by-1-to-3? '(1))))
    (is (= true (day2/levels-differ-by-1-to-3? '(1 2 3 4 5 7 9 11 14 17 14))))
    (is (= false (day2/levels-differ-by-1-to-3? '(5 7 9 11 14 17 13))))
    (is (= false (day2/levels-differ-by-1-to-3? '(1 14))))
    (is (= false (day2/levels-differ-by-1-to-3? '(23 14))))
    (is (= false (day2/levels-differ-by-1-to-3? '(1 2 3 3))))))

(def sample
  "7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9")

(deftest check-with-sample-data
  (testing "Question 1 solution"
    (is (= 2 (day2/count-trues (map day2/is-level-safe? (day2/str->matrix sample))))))

  (testing "Question 2 solution"
    (is (= 4 (day2/count-trues (map day2/is-level-safe-v3? (day2/str->matrix sample)))))))
