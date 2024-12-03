(ns aoc24.day3-test
  (:require [clojure.test :refer :all]
            [aoc24.day3 :as day3]))

(deftest day3-all
  (testing "Prepping for unlock"
    (is (= 10 (day3/add 1 2 3 4)))))
