# Advent of Code 2024

[![Clojure CI](https://github.com/RyanFleck/aoc24/actions/workflows/clojure.yml/badge.svg)](https://github.com/RyanFleck/aoc24/actions/workflows/clojure.yml)
[![codecov](https://codecov.io/github/RyanFleck/aoc24/graph/badge.svg?token=8FGtTTnD1b)](https://codecov.io/github/RyanFleck/aoc24)

This year I am taking on the challenges in **Clojure**.

To use, add a `profiles.clj` file with the following data:

```clojure
{:dev {:env {:aoc-session "53616c7... "}}}
```

You'll need to step through and execute (`C-x C-e`) most of the
solutions step by step with a REPL.

```clojure
(comment
  (= 2 (count-trues (map is-level-safe? (str->matrix sample)))) ; test case
  (count-trues (map is-level-safe? (str->matrix input)))) ; correct!
```

## Solutions

- [Day 1](https://github.com/RyanFleck/aoc24/blob/master/src/aoc24/day1.clj)
- [Day 2](https://github.com/RyanFleck/aoc24/blob/master/src/aoc24/day2.clj)
- [Day 3](https://github.com/RyanFleck/aoc24/blob/master/src/aoc24/day3.clj)

```clojure
(->>
 (s/split day-3-input #"do")
 (filter #(not (s/starts-with? % "n't")))
 (apply str)
 (re-seq #"mul\((\d+),(\d+)\)")
 (map #(* (str->int (nth % 1)) (str->int (nth % 2))))
 (apply +)) ; correct!
```

- [Day 4](https://github.com/RyanFleck/aoc24/blob/master/src/aoc24/day4.clj) (just part one)

```clojure
(defn day-4-answer [input]
  (let [rows (get-rows input)
        cols (get-columns input)
        diag-se (get-diagonal-rows-se input)
        diag-sw (get-diagonal-rows-sw input)
        combined (flatten (apply merge rows cols diag-se diag-sw))
        reversed (map #(apply str (reverse %)) combined)
        all (flatten (apply merge combined reversed))]

    (count (re-seq #"XMAS" (s/join "-" all)))))
```

- [Day 5](https://github.com/RyanFleck/aoc24/blob/master/src/aoc24/day5.clj)

## License

Copyright © 2024 Ryan Fleck

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.

## Code Coverage

Unit tests aren't critical to this project, but visualizations are still cool:

![Code Coverage Tree](https://codecov.io/github/RyanFleck/aoc24/graphs/tree.svg?token=8FGtTTnD1b)
