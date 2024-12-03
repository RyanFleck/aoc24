(ns aoc24.day2-test
  (:require [clojure.test :refer :all]
            [aoc24.day2 :refer :all]))

;; C-c C-t p  ---> Run project tests.

(deftest str->intlist_test
  (testing "Default use case:"
    (is (= (str->intlist "1 2 3 4") '(1 2 3 4)))))

(def matrix-in
  "1 2 3
4 5 6
7 8 9")

(def matrix-out '((1 2 3) (4 5 6) (7 8 9)))

(deftest str->matrix_test
  (testing "Default use case"
    (is (= (str->matrix matrix-in) matrix-out))))

(deftest check-increase-tester
  (testing "Ensure increasing levels are marked true"
    (is (= true (levels-all-increasing? '(1))))
    (is (= true (levels-all-increasing? '(1 2 3))))
    (is (= true (levels-all-increasing? '(1 2 3 4 5 6))))
    (is (= false (levels-all-increasing? '(1 2 3 4 5 5))))
    (is (= false (levels-all-increasing? '(1 2 3 4 5 4))))))

(deftest check-decrease-tester
  (testing "Ensure decreasing levels are marked true"
    (is (= true (levels-all-decreasing? '(1))))
    (is (= true (levels-all-decreasing? '(3 2 1))))
    (is (= true (levels-all-decreasing? '(5 4 3 2 1))))
    (is (= false (levels-all-decreasing? '(5 4 3 2 2))))
    (is (= false (levels-all-decreasing? '(5 4 3 2 20))))))

(deftest check-diff-tester
  (testing "Ensure decreasing levels are marked true"
    (is (= true (levels-differ-by-1-to-3? '(1))))
    (is (= true (levels-differ-by-1-to-3? '(1 2 3 4 5 7 9 11 14 17 14))))
    (is (= false (levels-differ-by-1-to-3? '(5 7 9 11 14 17 13))))
    (is (= false (levels-differ-by-1-to-3? '(1 14))))
    (is (= false (levels-differ-by-1-to-3? '(23 14))))
    (is (= false (levels-differ-by-1-to-3? '(1 2 3 3))))))
