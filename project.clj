(defproject aoc24 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [io.github.nextjournal/clerk "0.17.1102"]
                 [org.babashka/cli "0.8.60"]
                 [org.slf4j/slf4j-nop "2.0.16"]
                 [clj-http "3.13.0"]
                 [environ "1.2.0"]]
  :plugins [[dev.weavejester/lein-cljfmt "0.13.0"] ; 'lein cljfmt fix' to format repository
            [lein-cloverage "1.2.4"] ; 'lein cloverage' to get code coverage
            [lein-environ "1.2.0"]]
  :repl-options {:init-ns aoc24.core})
