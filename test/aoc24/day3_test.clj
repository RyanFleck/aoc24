(ns aoc24.day3-test
  (:require [clojure.test :refer :all]
            [aoc24.day3 :refer :all]))

(deftest day3-all
  (testing "Prepping for unlock"
    (is (= 10 (add 1 2 3 4)))))
